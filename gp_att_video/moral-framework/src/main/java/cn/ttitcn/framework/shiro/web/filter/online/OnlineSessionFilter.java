package cn.ttitcn.framework.shiro.web.filter.online;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import cn.ttitcn.common.constant.ShiroConstants;
import cn.ttitcn.common.enums.OnlineStatus;
import cn.ttitcn.framework.shiro.session.OnlineSession;
import cn.ttitcn.framework.shiro.session.OnlineSessionDAO;
import cn.ttitcn.framework.util.ShiroUtils;
import cn.ttitcn.system.entity.SysUser;

/**
 * 在线用户管理
 */
public class OnlineSessionFilter extends AccessControlFilter {
	/**
	 * 强制退出后重定向的地址
	 */
	@Value("${shiro.user.loginUrl}")
	private String loginUrl;

	@Autowired
	private OnlineSessionDAO onlineSessionDAO;

	/**
	 * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response);
		if (subject == null || subject.getSession() == null) {
			return true;
		}
		Session session = onlineSessionDAO.readSession(subject.getSession().getId());
		if (session != null && session instanceof OnlineSession) {
			OnlineSession onlineSession = (OnlineSession) session;
			request.setAttribute(ShiroConstants.ONLINE_SESSION, onlineSession);
			// 把user对象设置进去
			boolean isGuest = onlineSession.getUserId() == null || onlineSession.getUserId() == 0L;
			if (isGuest == true) {
				SysUser user = ShiroUtils.getSysUser();
				if (user != null) {
					onlineSession.setUserId(user.getUserId());
					onlineSession.setLoginName(user.getLoginName());
					onlineSession.setUserCode(user.getUserCode());
					onlineSession.setIdcardno(user.getIdcardno());
					onlineSession.setUserName(user.getUserName());
					onlineSession.setPhonenumber(user.getPhonenumber());
					onlineSession.setUserType(user.getUserType());
					onlineSession.setAvatar(user.getAvatar());
					onlineSession.setDeptName(user.getDept().getDeptName());
					onlineSession.markAttributeChanged();
				}
			}

			if (onlineSession.getStatus() == OnlineStatus.off_line) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (subject != null) {
			subject.logout();
		}
		saveRequestAndRedirectToLogin(request, response);
		return false;
	}

	// 跳转到登录页
	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		WebUtils.issueRedirect(request, response, loginUrl);
	}
}
