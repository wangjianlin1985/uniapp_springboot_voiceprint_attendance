package cn.ttitcn.framework.manager.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import cn.ttitcn.common.constant.Constants;
import cn.ttitcn.common.util.AddressUtils;
import cn.ttitcn.common.util.ServletUtils;
import cn.ttitcn.common.util.SpringUtils;
import cn.ttitcn.framework.shiro.session.OnlineSession;
import cn.ttitcn.framework.util.LogUtils;
import cn.ttitcn.framework.util.ShiroUtils;
import cn.ttitcn.system.constant.SystemRedisKey;
import cn.ttitcn.system.entity.SysLogininfor;
import cn.ttitcn.system.entity.SysOperLog;
import cn.ttitcn.system.entity.SysUser;
import cn.ttitcn.system.entity.SysUserOnline;
import cn.ttitcn.system.service.SysLogininforService;
import cn.ttitcn.system.service.SysOperLogService;
import cn.ttitcn.system.service.SysUserOnlineService;
import cn.ttitcn.system.service.SysUserService;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 异步任务
 */
@SuppressWarnings("all")
@Component
public class AsyncFactory {
    
    private static RedisTemplate redisTemplate ;
    
    @Resource(name="redisTemplate")
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        AsyncFactory.redisTemplate = redisTemplate;
    }
    
	private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");
	
	
	/**
	 * 同步session到数据库 
	 * 
	 * @param session 在线用户会话
	 * @return 任务task
	 */ 
	public static TimerTask syncSessionToDb(final OnlineSession session) {
		return new TimerTask() {
			@Override
			public void run() {
				SysUserOnline online = new SysUserOnline();
				online.setSessionId(String.valueOf(session.getId()));
				online.setDeptName(session.getDeptName());
				online.setLoginName(session.getLoginName());
				online.setUserCode(session.getUserCode());
				online.setIdcardno(session.getIdcardno());
				online.setPhonenumber(session.getPhonenumber());
				online.setUserType(session.getUserType());
				online.setStartTimestamp(session.getStartTimestamp());
				online.setLastAccessTime(session.getLastAccessTime());
				online.setUserName(session.getUserName());
				online.setExpireTime(session.getTimeout());
				online.setIpaddr(session.getHost());
				online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
				online.setBrowser(session.getBrowser());
				online.setOs(session.getOs());
				online.setStatus(session.getStatus());
				SpringUtils.getBean(SysUserOnlineService.class).saveOnline(online);

			}
		};
	}

	/**
	 * 操作日志记录到redis缓存
	 * 
	 * @param operLog 操作日志信息
	 * @return 任务task
	 */
	public static TimerTask recordOperRedis(final SysOperLog operLog) {
		return new TimerTask() {
			@Override
			public void run() {
			    // 插入到redis
				ListOperations operations = redisTemplate.opsForList();
				List<SysOperLog> list = new ArrayList<SysOperLog>();
				list.add(operLog);
				operations.rightPush(SystemRedisKey.OPER_LOG_REDISKEY, list);
			}
		};
	}
	
	
	/**
     * 操作日志记录到数据库
     * 
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOperDb(final SysOperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                /** 远程查询操作地点 并插入数据库  */
                //operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(SysOperLogService.class).add(operLog);
            }
        };
    }
    
    
    /**
     * 微信端退出的话清空openid
     */
    public static TimerTask cleanOpenid(final String loginName) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtils.getBean(SysUserService.class).cleanOpenid(loginName);
            }
        };
    }

	/**
	 * 记录登陆信息
	 * 
	 * @param username 用户名
	 * @param status   状态
	 * @param message  消息
	 * @param args     列表
	 * @return 任务task
	 */
	public static TimerTask recordLogininfor(final String username, final String status, final String message,
			final Object... args) {
		final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
		final String ip = ShiroUtils.getIp();
		return new TimerTask() {
			@Override
			public void run() {
				String address = AddressUtils.getRealAddressByIP(ip);
				StringBuilder s = new StringBuilder();
				s.append(LogUtils.getBlock(ip));
				s.append(address);
				s.append(LogUtils.getBlock(username));
				s.append(LogUtils.getBlock(status));
				s.append(LogUtils.getBlock(message));
				// 打印信息到日志
				sys_user_logger.info(s.toString(), args);
				// 获取客户端操作系统
				String os = userAgent.getOperatingSystem().getName();
				// 获取客户端浏览器
				String browser = userAgent.getBrowser().getName();
				// 封装对象
				SysLogininfor logininfor = new SysLogininfor();
				logininfor.setLoginName(username);
				logininfor.setIpaddr(ip);
				logininfor.setLoginLocation(address);
				logininfor.setBrowser(browser);
				logininfor.setOs(os);
				logininfor.setMsg(message);
				// 日志状态
				if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
					logininfor.setStatus(Constants.SUCCESS);
				} else if (Constants.LOGIN_FAIL.equals(status)) {
					logininfor.setStatus(Constants.FAIL);
				}
				// 插入数据
				SpringUtils.getBean(SysLogininforService.class).add(logininfor);
			}
		};
	}
}
