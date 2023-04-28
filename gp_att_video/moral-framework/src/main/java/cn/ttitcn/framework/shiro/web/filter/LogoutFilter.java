package cn.ttitcn.framework.shiro.web.filter;

import java.io.Serializable;
import java.util.Deque;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ttitcn.common.constant.Constants;
import cn.ttitcn.common.constant.ShiroConstants;
import cn.ttitcn.common.util.AgentUtils;
import cn.ttitcn.common.util.MessageUtils;
import cn.ttitcn.common.util.StringUtils;
import cn.ttitcn.framework.manager.AsyncManager;
import cn.ttitcn.framework.manager.factory.AsyncFactory;
import cn.ttitcn.framework.util.ShiroUtils;
import cn.ttitcn.system.entity.SysUser;

/**
 * 退出拦截器
 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
    private static final Logger log = LoggerFactory.getLogger(LogoutFilter.class);

    /**
     * 退出后重定向的地址
     */
    private String loginUrl;

    private Cache<String, Deque<Serializable>> cache;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        try {
            Subject subject = getSubject(request, response);
            String redirectUrl = getRedirectUrl(request, response, subject);
            try {
                SysUser user = ShiroUtils.getSysUser();
                if (StringUtils.isNotNull(user)) {
                    String loginName = user.getLoginName();
                    // 记录用户退出日志
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGOUT,
                            MessageUtils.message("user.logout.success")));
                    // 清理缓存
                    cache.remove(loginName);
                    
                    // 手机端退出需要清空openid
                    if(AgentUtils.isMobile((HttpServletRequest)request)) {
                        AsyncManager.me().execute(AsyncFactory.cleanOpenid(loginName));
                    } 
                    
                }
                // 退出登录
                subject.logout();
            } catch (SessionException ise) {
                log.error("logout fail.", ise);
            }
            issueRedirect(request, response, redirectUrl);
        } catch (Exception e) {
            log.error("Encountered session exception during logout.  This can generally safely be ignored.", e);
        }
        return false;
    }

    /**
     * 退出跳转URL
     */
    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
    	String url = getLoginUrl();
    	/**
    	 *  手机端跳转地址
    	 *  如果是wx/home/login是直接登录地址。
    	 *  如果是wx/home/index的话，
    	 *     是先获取openid再进入登录,
    	 *     这样做避免在退出后再登录使得openid为空
    	 */
    	
    	if(AgentUtils.isMobile((HttpServletRequest)request)) {
    		url = "/wx/home/index";
    	} 
        if (StringUtils.isNotEmpty(url)) {
            return url;
        }
        return super.getRedirectUrl(request, response, subject);
    }

    // 设置Cache的key的前缀
    public void setCacheManager(CacheManager cacheManager) {
        // 必须和ehcache缓存配置中的缓存name一致
        this.cache = cacheManager.getCache(ShiroConstants.SYS_USERCACHE);
    }
}
