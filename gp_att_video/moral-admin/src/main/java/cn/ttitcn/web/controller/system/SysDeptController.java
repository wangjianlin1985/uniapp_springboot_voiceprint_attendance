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
import cn.ttitcn.common.core.base.BaseController;
import cn.ttitcn.common.core.domain.AjaxResult;
import cn.ttitcn.common.core.domain.Ztree;
import cn.ttitcn.common.enums.BusinessType;
import cn.ttitcn.common.util.StringUtils;
import cn.ttitcn.framework.util.ShiroUtils;
import cn.ttitcn.system.entity.SysDept;
import cn.ttitcn.system.entity.SysRole;
import cn.ttitcn.system.service.SysDeptService;

/**
 * 部门管理
 */
@Controller
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
	
    private static String prefix = "system/dept";

	@Autowired
	private SysDeptService deptService;

	@RequiresPermissions("system:dept:view")
	@GetMapping()
	public String dept() {
		return prefix + "/dept";
	}

	@RequiresPermissions("system:dept:list")
	@PostMapping("/list")
	@ResponseBody
	public List<SysDept> list(SysDept dept) {
		List<SysDept> deptList = deptService.list(dept);
		return deptList;
	}

	/**
	 * 新增部门
	 */
	@GetMapping("/add/{parentId}")
	public String add(@PathVariable("parentId") Long parentId, ModelMap mmap) {
		mmap.put("dept", deptService.getById(parentId));
		return prefix + "/add";
	}

	/**
	 * 新增保存部门
	 */
	@Log(title = "部门管理", businessType = BusinessType.INSERT)
	@RequiresPermissions("system:dept:add")
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysDept dept) {
		dept.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(deptService.add(dept));
	}

	/**
	 * 修改
	 */
	@GetMapping("/edit/{deptId}")
	public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap) {
		SysDept dept = deptService.getById(deptId);
		if (StringUtils.isNotNull(dept) && 100L == deptId) {
			dept.setParentName("无");
		}
		mmap.put("dept", dept);
		return prefix + "/edit";
	}

	/**
	 * 保存
	 */
	@Log(title = "部门管理", businessType = BusinessType.UPDATE)
	@RequiresPermissions("system:dept:edit")
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysDept dept) {
		dept.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(deptService.update(dept));
	}

	/**
	 * 删除
	 */
	@Log(title = "部门管理", businessType = BusinessType.DELETE)
	@RequiresPermissions("system:dept:remove")
	@GetMapping("/remove/{deptId}")
	@ResponseBody
	public AjaxResult remove(@PathVariable("deptId") Long deptId) {
		SysDept dept = new SysDept();
		dept.setDeptId(deptId);
		if (deptService.selectDeptCount(dept) > 0) {
			return AjaxResult.warn("存在下级部门,不允许删除");
		}
		if (deptService.checkDeptExistUser(deptId)) {
			return AjaxResult.warn("部门存在用户,不允许删除");
		}
		
		dept.preDelete();
		dept.setDeleteBy(ShiroUtils.getLoginName());
		return toAjax(deptService.delete(dept));
	}


	/**
	 * 选择部门树
	 */
	@GetMapping("/selectDeptTree/{deptId}")
	public String selectDeptTree(@PathVariable("deptId") Long deptId, ModelMap mmap) {
		mmap.put("dept", deptService.getById(deptId));
		return prefix + "/tree";
	}

	/**
	 * 加载部门列表树
	 */
	@GetMapping("/treeData")
	@ResponseBody
	public List<Ztree> treeData() {
		List<Ztree> ztrees = deptService.selectDeptTree(new SysDept());
		return ztrees;
	}

	/**
	 * 加载角色部门（数据权限）列表树
	 */
	@GetMapping("/roleDeptTreeData")
	@ResponseBody
	public List<Ztree> deptTreeData(SysRole role) {
		List<Ztree> ztrees = deptService.roleDeptTreeData(role);
		return ztrees;
	}
}