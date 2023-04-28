package cn.ttitcn.web.controller.system;

import java.util.List;

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

import cn.ttitcn.common.annotation.Log;
import cn.ttitcn.common.constant.UserConstants;
import cn.ttitcn.common.core.base.BaseController;
import cn.ttitcn.common.core.domain.AjaxResult;
import cn.ttitcn.common.core.page.TableDataInfo;
import cn.ttitcn.common.core.text.Convert;
import cn.ttitcn.common.enums.BusinessType;
import cn.ttitcn.common.util.ExcelUtil;
import cn.ttitcn.framework.util.ShiroUtils;
import cn.ttitcn.system.entity.SysPost;
import cn.ttitcn.system.service.SysPostService;

/**
 *  岗位管理
 */
@Controller
@RequestMapping("/system/post")
public class SysPostController extends BaseController {
	
    private static String prefix = "system/post";

	@Autowired
	private SysPostService postService;

	@RequiresPermissions("system:post:view")
	@GetMapping()
	public String operlog() {
		return prefix + "/post";
	}

	@RequiresPermissions("system:post:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysPost post) {
		startPage();
		List<SysPost> list = postService.list(post);
		return getDataTable(list);
	}

	@Log(title = "岗位管理", businessType = BusinessType.EXPORT)
	@RequiresPermissions("system:post:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysPost post) {
		List<SysPost> list = postService.list(post);
		ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
		return util.exportExcel(list, "岗位数据");
	}

	@RequiresPermissions("system:post:remove")
	@Log(title = "岗位管理", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		try {
		    Long[] idsLong = Convert.toLongArray(ids);
			return toAjax(postService.deleteBatch(idsLong, ShiroUtils.getLoginName()));
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * 新增岗位
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存岗位
	 */
	@RequiresPermissions("system:post:add")
	@Log(title = "岗位管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysPost post) {
		if (UserConstants.POST_CODE_NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
			return error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
		}
		post.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(postService.add(post));
	}

	/**
	 * 修改岗位
	 */
	@GetMapping("/edit/{postId}")
	public String edit(@PathVariable("postId") Long postId, ModelMap mmap) {
		mmap.put("post", postService.getById(postId));
		return prefix + "/edit";
	}

	/**
	 * 修改保存岗位
	 */
	@RequiresPermissions("system:post:edit")
	@Log(title = "岗位管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysPost post) {
		if (UserConstants.POST_CODE_NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
			return error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
		}
		post.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(postService.update(post));
	}

	/**
	 * 校验岗位编码
	 */
	@PostMapping("/checkPostCodeUnique")
	@ResponseBody
	public String checkPostCodeUnique(SysPost post) {
		return postService.checkPostCodeUnique(post);
	}
}
