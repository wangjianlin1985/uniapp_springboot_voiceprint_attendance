package cn.ttitcn.common.exception.user;

import cn.ttitcn.common.exception.BaseException;

/**
 * 用户信息异常
 */
public class UserException extends BaseException {
	private static final long serialVersionUID = 1L;

	public UserException(String code, Object[] args) {
		super("user", code, args, null);
	}
}
