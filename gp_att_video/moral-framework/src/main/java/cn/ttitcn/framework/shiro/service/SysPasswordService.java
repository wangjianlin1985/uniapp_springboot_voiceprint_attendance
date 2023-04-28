package cn.ttitcn.framework.shiro.service;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.ttitcn.common.constant.Constants;
import cn.ttitcn.common.constant.ShiroConstants;
import cn.ttitcn.common.exception.user.UserPasswordNotMatchException;
import cn.ttitcn.common.exception.user.UserPasswordRetryLimitExceedException;
import cn.ttitcn.common.util.MessageUtils;
import cn.ttitcn.framework.manager.AsyncManager;
import cn.ttitcn.framework.manager.factory.AsyncFactory;
import cn.ttitcn.system.entity.SysUser;

/**
 * shiro密码管理
 */
@Component
public class SysPasswordService {
	
	@Autowired
	private CacheManager cacheManager;

	private Cache<String, AtomicInteger> loginRecordCache;

	@Value(value = "${user.password.maxRetryCount}")
	private String maxRetryCount;

	@PostConstruct
	public void init() {
		loginRecordCache = cacheManager.getCache(ShiroConstants.LOGINRECORDCACHE);
	}

	public void validate(SysUser user, String password) {
		String loginName = user.getLoginName();

		AtomicInteger retryCount = loginRecordCache.get(loginName);

		if (retryCount == null) {
			retryCount = new AtomicInteger(0);
			loginRecordCache.put(loginName, retryCount);
		}
		if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue()) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL,
					MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
			throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
		}

		if (!matches(user, password)) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL,
					MessageUtils.message("user.password.retry.limit.count", retryCount)));
			loginRecordCache.put(loginName, retryCount);
			throw new UserPasswordNotMatchException();
		} else {
			clearLoginRecordCache(loginName);
		}
	}

	public boolean matches(SysUser user, String newPassword) {
		System.out.println("密码：" + encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
		return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
	}

	public void clearLoginRecordCache(String username) {
		loginRecordCache.remove(username);
	}

	public String encryptPassword(String username, String password, String salt) {
		return new Md5Hash(username + password + salt).toHex().toString();
	}
	
	

}
