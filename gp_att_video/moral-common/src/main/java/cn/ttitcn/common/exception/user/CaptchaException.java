package cn.ttitcn.common.exception.user;

/**
 * 验证码错误
 */
public class CaptchaException extends UserException {
	private static final long serialVersionUID = 1L;

	public CaptchaException() {
		super("user.jcaptcha.error", null);
	}
}
