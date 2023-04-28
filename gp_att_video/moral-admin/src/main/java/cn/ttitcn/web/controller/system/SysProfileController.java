package cn.ttitcn.web.controller.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.ttitcn.common.annotation.Log;
import cn.ttitcn.common.config.Global;
import cn.ttitcn.common.core.base.BaseController;
import cn.ttitcn.common.core.domain.AjaxResult;
import cn.ttitcn.common.enums.BusinessType;
import cn.ttitcn.common.util.AESUtil;
import cn.ttitcn.common.util.StringUtils;
import cn.ttitcn.common.util.file.FileUploadUtils;
import cn.ttitcn.framework.shiro.service.SysPasswordService;
import cn.ttitcn.framework.util.ShiroUtils;
import cn.ttitcn.system.entity.SysUser;
import cn.ttitcn.system.service.SysUserService;

/**
 *  PC端个人中心
 */
@Controller
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {
	
    private static final Logger log = LoggerFactory.getLogger(SysProfileController.class);

	private static String prefix = "system/user/profile";

	@Autowired
	private SysUserService userService;

	@Autowired
	private SysPasswordService passwordService;

	/**
	 * 个人信息
	 */
	@GetMapping()
	public String profile(ModelMap mmap) {
		SysUser user = ShiroUtils.getSysUser();
		mmap.put("user", user);
		mmap.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
		mmap.put("postGroup", userService.selectUserPostGroup(user.getUserId()));
		return prefix + "/profile";
	}

	@GetMapping("/checkPassword")
	@ResponseBody
	public boolean checkPassword(String password) {
		SysUser user = ShiroUtils.getSysUser();
		if (passwordService.matches(user, password)) {
			return true;
		}
		return false;
	}

	@GetMapping("/resetPwd")
	public String resetPwd(ModelMap mmap) {
		SysUser user = ShiroUtils.getSysUser();
		mmap.put("user", userService.getById(user.getUserId()));
		return prefix + "/resetPwd";
	}

	@Log(title = "重置密码", businessType = BusinessType.UPDATE)
	@PostMapping("/resetPwd")
	@ResponseBody
	public AjaxResult resetPwd(String oldPassword, String newPassword) {
		SysUser user = ShiroUtils.getSysUser();
		if (StringUtils.isNotEmpty(newPassword) && passwordService.matches(user, oldPassword)) {
			user.setSalt(ShiroUtils.randomSalt());
			user.setOldpwd(AESUtil.encrypt(newPassword));
			user.setPassword(passwordService.encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
			if (userService.resetUserPwd(user) > 0) {
				ShiroUtils.setSysUser(userService.getById(user.getUserId()));
				return success();
			}
			return error();
		} else {
			return error("修改密码失败，旧密码错误");
		}
	}

	/**
	 * 修改用户
	 */
	@GetMapping("/edit")
	public String edit(ModelMap mmap) {
		SysUser user = ShiroUtils.getSysUser();
		mmap.put("user", userService.getById(user.getUserId()));
		return prefix + "/edit";
	}

	/**
	 * 修改头像
	 */
	@GetMapping("/avatar")
	public String avatar(ModelMap mmap) {
		SysUser user = ShiroUtils.getSysUser();
		mmap.put("user", userService.getById(user.getUserId()));
		return prefix + "/avatar";
	}

	/**
	 * 修改用户
	 */
	@Log(title = "个人信息", businessType = BusinessType.UPDATE)
	@PostMapping("/update")
	@ResponseBody
	public AjaxResult update(SysUser user) {
		SysUser currentUser = ShiroUtils.getSysUser();
		currentUser.setUserName(user.getUserName());
		currentUser.setEmail(user.getEmail());
		currentUser.setPhonenumber(user.getPhonenumber());
		currentUser.setSex(user.getSex());
		if (userService.updateUserInfo(currentUser) > 0) {
			ShiroUtils.setSysUser(userService.getById(currentUser.getUserId()));
			return success();
		}
		return error();
	}

	/**
	 * 保存头像
	 */
	@Log(title = "个人信息", businessType = BusinessType.UPDATE)
	@PostMapping("/updateAvatar")
	@ResponseBody
	public AjaxResult updateAvatar(@RequestParam("avatarfile") MultipartFile file) {
		SysUser currentUser = ShiroUtils.getSysUser();
		try {
			if (!file.isEmpty()) {
				String avatar = FileUploadUtils.upload(Global.getAvatarPath(), file);
				currentUser.setAvatar(avatar);
				if (userService.updateUserInfo(currentUser) > 0) {
					ShiroUtils.setSysUser(userService.getById(currentUser.getUserId()));
					return success();
				}
			}
			return error();
		} catch (Exception e) {
			log.error("修改头像失败！", e);
			return error(e.getMessage());
		}
	}
}
