package cn.ttitcn.system.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.ttitcn.common.util.DateUtils;
import cn.ttitcn.system.entity.SysAtt;
import cn.ttitcn.system.entity.SysUser;
import cn.ttitcn.system.service.SysAttService;
import cn.ttitcn.system.service.SysUserService;

@Component
public class InitAttdata {

	@Autowired
	private SysAttService attService;
	@Autowired
	private SysUserService userService;
	
	
	// 定时任务，生成考勤数据
	// 生成的时候都是未考勤状态，
	// cron在线表达式  https://cron.qqe2.com/
	// 0 0 0/1 * * ?  每小时执行一次
	// 0/10 * * * * ? 每10秒执行一次 调试用
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void execute() {
		String date = DateUtils.getDate();
		
		System.out.println("开始执行定时任务~~~~~~~~~~~~");
		// 查询出所有的用户信息
		List<SysUser> userList = userService.list(new SysUser());
		
		// 查询出今天所有的已经生成的考勤数据，以用户id为key放入map中
		SysAtt searchAtt = new SysAtt();
		searchAtt.setDate(date);
		List<SysAtt> existAttList = attService.list(searchAtt);
		Map<Long, Object> existAttMap = new HashMap<>();
		for(SysAtt att : existAttList) {
			if (att.getUser() != null) {
				existAttMap.put(att.getUser().getUserId(), 1);
			}
		}
		
		for(SysUser user : userList) {
			if ("admin".equals(user.getLoginName())) {
				continue; // 超级管理员不考勤
			}
			
			// 该用户不存在考勤列表中，则加入。（适用于今天注册但是希望今天考勤的人）
			Object object = existAttMap.get(user.getUserId());
			if (object == null) {
				SysAtt att = new SysAtt();
				att.setDate(date);
				att.setUser(user);
				att.setStatus("0");
				att.setTime("");
				attService.add(att);
			}
		}
	}

}
