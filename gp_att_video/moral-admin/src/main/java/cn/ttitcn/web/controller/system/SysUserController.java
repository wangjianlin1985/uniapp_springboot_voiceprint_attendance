package cn.ttitcn.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.ttitcn.common.annotation.Log;
import cn.ttitcn.common.constant.PunctuationConstants;
import cn.ttitcn.common.constant.UserConstants;
import cn.ttitcn.common.core.base.BaseController;
import cn.ttitcn.common.core.domain.AjaxResult;
import cn.ttitcn.common.core.domain.Ztree;
import cn.ttitcn.common.core.page.TableDataInfo;
import cn.ttitcn.common.core.text.Convert;
import cn.ttitcn.common.enums.BusinessType;
import cn.ttitcn.common.enums.UserType;
import cn.ttitcn.common.exception.BusinessException;
import cn.ttitcn.common.util.AESUtil;
import cn.ttitcn.common.util.ExcelUtil;
import cn.ttitcn.common.util.StringUtils;
import cn.ttitcn.framework.shiro.service.SysPasswordService;
import cn.ttitcn.framework.util.ShiroUtils;
import cn.ttitcn.system.entity.SysPost;
import cn.ttitcn.system.entity.SysRole;
import cn.ttitcn.system.entity.SysUser;
import cn.ttitcn.system.service.SysPostService;
import cn.ttitcn.system.service.SysRoleService;
import cn.ttitcn.system.service.SysUserService;
import cn.ttitcn.util.DeleteFeature;
import cn.ttitcn.util.XFYunConstant;

/**
 * 用户管理
 */

@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
	
	private static String prefix = "system/user";

	@Autowired
	private SysUserService userService;

	@Autowired
	private SysRoleService roleService;

	@Autowired
	private SysPostService postService;

	@Autowired
	private SysPasswordService passwordService;
	
	

	@RequiresPermissions("system:user:view")
	@GetMapping()
	public String user() {
		return prefix + "/user";
	}

	@RequiresPermissions("system:user:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysUser user) {
	    user.setUserType(UserType.TEACHER.getCode());
		startPage();
		List<SysUser> list = userService.list(user);
		return getDataTable(list);
	}

	@Log(title = "用户管理", businessType = BusinessType.EXPORT)
	@RequiresPermissions("system:user:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysUser user) {
	    user.setUserType(UserType.TEACHER.getCode());
	    List<SysUser> list = userService.list(user);
		ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
		return util.exportExcel(list, "用户数据");
	}

	@Log(title = "用户管理", businessType = BusinessType.IMPORT)
	@RequiresPermissions("system:user:import")
	@PostMapping("/importData")
	@ResponseBody
	public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
		ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
		List<SysUser> userList = util.importExcel(file.getInputStream());
		String operName = ShiroUtils.getSysUser().getLoginName();
		String message = userService.importUser(userList, updateSupport, operName);
		return AjaxResult.success(message);
	}

	@RequiresPermissions("system:user:view")
	@GetMapping("/importTemplate")
	@ResponseBody
	public AjaxResult importTemplate() {
		ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
		return util.importTemplateExcel("用户数据");
	}

	/**
	 * 新增用户
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap) {
		mmap.put("roles", roleService.listAll(new SysRole()));
		mmap.put("posts", postService.listAll(new SysPost()));
		return prefix + "/add";
	}

	/**
	 * 新增保存用户
	 */
	@RequiresPermissions("system:user:add")
	@Log(title = "用户管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysUser user) {
		if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(user))) {
			return error("新增用户'" + user.getLoginName() + "'失败，登录账号已存在");
		} else if (UserConstants.USER_IDCARDNO_NOT_UNIQUE.equals(userService.checkIdcardnoUnique(user))) {
            return error("新增用户'" + user.getLoginName() + "'失败，身份证号已存在");
        } else if(UserConstants.USER_CODE_NOT_UNIQUE.equals(userService.checkUsercodeUnique(user))) {
            return error("新增用户'" + user.getLoginName() + "'失败，学工号已存在");
        }
		user.setUserType(UserType.TEACHER.getCode());
		user.setSalt(ShiroUtils.randomSalt());
		user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
		user.setOldpwd(AESUtil.encrypt(user.getPassword()));
		user.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(userService.add(user));
	}

	/**
	 * 修改用户
	 */
	@GetMapping("/edit/{userId}")
	public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
		mmap.put("user", userService.getById(userId));
		mmap.put("roles", roleService.selectRolesByUserId(userId));
		mmap.put("posts", postService.selectPostsByUserId(userId));
		return prefix + "/edit";
	}

	/**
	 * 修改保存用户
	 */
	@RequiresPermissions("system:user:edit")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysUser user) {
		if (StringUtils.isNotNull(user.getUserId()) && SysUser.isAdmin(user.getUserId())) {
			return error("不允许修改超级管理员用户");
		} else if (UserConstants.USER_IDCARDNO_NOT_UNIQUE.equals(userService.checkIdcardnoUnique(user))) {
            return error("修改用户'" + user.getLoginName() + "'失败，身份证号已存在");
        } else if(UserConstants.USER_CODE_NOT_UNIQUE.equals(userService.checkUsercodeUnique(user))) {
            return error("修改用户'" + user.getLoginName() + "'失败，学工号已存在");
        }
		user.setUserType(UserType.TEACHER.getCode());
		user.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(userService.update(user));
	}

	@RequiresPermissions("system:user:resetPwd")
	@Log(title = "重置密码", businessType = BusinessType.UPDATE)
	@GetMapping("/resetPwd/{userId}")
	public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
		mmap.put("user", userService.getById(userId));
		return prefix + "/resetPwd";
	}

	@RequiresPermissions("system:user:resetPwd")
	@Log(title = "重置密码", businessType = BusinessType.UPDATE)
	@PostMapping("/resetPwd")
	@ResponseBody
	public AjaxResult resetPwdSave(SysUser user) {
		user.setSalt(ShiroUtils.randomSalt());
		user.setOldpwd(AESUtil.encrypt(user.getPassword()));
		user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
		if (userService.resetUserPwd(user) > 0) {
			if (ShiroUtils.getUserId().equals(user.getUserId())) {
				ShiroUtils.setSysUser(userService.getById(user.getUserId()));
			}
			return success();
		}
		return error();
	}
	
	// 单个删除声纹（同时删除讯飞库）
	@RequestMapping("deleteVoice")
	@ResponseBody
	public AjaxResult deleteVoice(HttpServletRequest request) {
		Long userId = Convert.toLong(request.getParameter("userId"),1L);
		SysUser user = userService.getById(userId);
		String featureId = user.getFeatureId();
		if (StringUtils.isEmpty(featureId)) {
			return success();
		} else {
			userService.cleanFeatureId(userId);
			DeleteFeature.doDeleteFeature(XFYunConstant.REQUESTURL,XFYunConstant.APPID,XFYunConstant.APISECRET,XFYunConstant.APIKEY, featureId);
		}
		return success();
	}
	
	

	@RequiresPermissions("system:user:remove")
	@Log(title = "用户管理", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		try {
			for(String id : ids.split(PunctuationConstants.COMMA)) {
				if(SysUser.isAdmin(Long.valueOf(id))) {
					 throw new BusinessException("不允许删除超级管理员用户");
				}
			}
			return toAjax(userService.deleteBatch(Convert.toLongArray(ids), ShiroUtils.getLoginName()));
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * 校验用户名
	 */
	@PostMapping("/checkLoginNameUnique")
	@ResponseBody
	public String checkLoginNameUnique(SysUser user) {
		return userService.checkLoginNameUnique(user);
	}

	/**
	 * 校验学工号
	 */
	@PostMapping("/checkUsercodeUnique")
	@ResponseBody
	public String checkUsercodeUnique(SysUser user) {
		return userService.checkUsercodeUnique(user);
	}

	/**
	 * 校验身份证
	 */
	@PostMapping("/checkIdcardnoUnique")
	@ResponseBody
	public String checkIdcardnoUnique(SysUser user) {
		return userService.checkIdcardnoUnique(user);
	}

	/**
	 * 用户状态修改
	 */
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@RequiresPermissions("system:user:edit")
	@PostMapping("/changeStatus")
	@ResponseBody
	public AjaxResult changeStatus(SysUser user) {
		return toAjax(userService.changeStatus(user));
	}
	
	/**
	 * 进入用户树结构，部门是根节点，用户是叶子节点
	 * @param request
	 * @param mmap
	 * @return
	 */
	@GetMapping("/selectUserTreeMultiple")
	public String selectUserTreeMultiple(HttpServletRequest request, ModelMap mmap) {
		String userId = request.getParameter("userId");
		if(StringUtils.isEmpty(userId)) {
			userId = "1"; 
		}
		String userType = request.getParameter("userType");
		mmap.put("user", userService.getById(Long.valueOf(userId)));
		mmap.put("userType", userType);
		return prefix + "/treeMultiple";
	}
	
	
	/**
     * 进入用户树结构，部门是根节点，用户是叶子节点
     * @param request
     * @param mmap
     * @return
     */
    @GetMapping("/selectUserTree")
    public String selectDeptTree(HttpServletRequest request, ModelMap mmap) {
        String userId = request.getParameter("userId");
        if(StringUtils.isEmpty(userId)) {
            userId = "1"; 
        }
        String userType = request.getParameter("userType");
        mmap.put("user", userService.getById(Long.valueOf(userId)));
        mmap.put("userType", userType);
        return prefix + "/tree";
    }
	
	
	@GetMapping("/treeData")
	@ResponseBody
	public List<Ztree> treeData(HttpServletRequest request) {
		String userType = request.getParameter("userType");
		List<Ztree> ztrees = userService.treeData(Integer.parseInt(userType));
		return ztrees;
	}

	
}
