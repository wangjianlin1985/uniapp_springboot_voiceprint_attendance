package cn.ttitcn.framework.shiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.ttitcn.common.constant.Constants;
import cn.ttitcn.common.constant.ShiroConstants;
import cn.ttitcn.common.constant.UserConstants;
import cn.ttitcn.common.enums.CommonStatus;
import cn.ttitcn.common.exception.user.CaptchaException;
import cn.ttitcn.common.exception.user.UserBlockedException;
import cn.ttitcn.common.exception.user.UserDeleteException;
import cn.ttitcn.common.exception.user.UserNotExistsException;
import cn.ttitcn.common.exception.user.UserPasswordNotMatchException;
import cn.ttitcn.common.util.DateUtils;
import cn.ttitcn.common.util.MessageUtils;
import cn.ttitcn.common.util.ServletUtils;
import cn.ttitcn.framework.manager.AsyncManager;
import cn.ttitcn.framework.manager.factory.AsyncFactory;
import cn.ttitcn.framework.util.ShiroUtils;
import cn.ttitcn.system.entity.SysUser;
import cn.ttitcn.system.service.SysUserService;

/**
 * shiro登录
 */
@Component
public class SysLoginService {
	@Autowired
	private SysPasswordService passwordService;

	@Autowired
	private SysUserService userService;

	/**
	 * 登录
	 */
	public SysUser login(String username, String password) {
		// 验证码校验
		if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.jcaptcha.error")));
			throw new CaptchaException();
		}
		// 用户名或密码为空 错误
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			AsyncManager.me().execute(
					AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
			throw new UserNotExistsException();
		}
		// 密码如果不在指定范围内 错误
		if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
				|| password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.password.not.match")));
			throw new UserPasswordNotMatchException();
		}

		// 用户名不在指定范围内 错误
		if (username.length() < UserConstants.USERNAME_MIN_LENGTH
				|| username.length() > UserConstants.USERNAME_MAX_LENGTH) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.password.not.match")));
			throw new UserPasswordNotMatchException();
		}

		// 查询用户信息
		SysUser searchUser = new SysUser();
		searchUser.setLoginName(username);
		SysUser user = userService.selectUserByLoginName(searchUser);
		if (user == null) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.not.exists")));
			throw new UserNotExistsException();
		}
		
		if (CommonStatus.DELETED.getCode().equals(user.getDeleteFlag())) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.password.delete")));
			throw new UserDeleteException();
		}

		if (CommonStatus.DISABLE.getCode().toString().equals(user.getStatus())) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.blocked", user.getRemark())));
			throw new UserBlockedException();
		}

		passwordService.validate(user, password);

		AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS,
				MessageUtils.message("user.login.success")));
		recordLoginInfo(user);
		return user;
	}

	/**
	 * 记录登录信息
	 */
	public void recordLoginInfo(SysUser user) {
		user.setLoginIp(ShiroUtils.getIp());
		user.setLoginDate(DateUtils.getNowDate());
		userService.updateUserInfo(user);
	}
}
