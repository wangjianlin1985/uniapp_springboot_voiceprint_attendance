package cn.ttitcn.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ttitcn.common.annotation.Log;
import cn.ttitcn.common.constant.PunctuationConstants;
import cn.ttitcn.common.constant.UserConstants;
import cn.ttitcn.common.core.base.BaseController;
import cn.ttitcn.common.core.domain.AjaxResult;
import cn.ttitcn.common.core.page.TableDataInfo;
import cn.ttitcn.common.core.text.Convert;
import cn.ttitcn.common.enums.BusinessType;
import cn.ttitcn.common.exception.BusinessException;
import cn.ttitcn.common.util.ExcelUtil;
import cn.ttitcn.framework.util.ShiroUtils;
import cn.ttitcn.system.constant.SystemRedisKey;
import cn.ttitcn.system.entity.SysDictData;
import cn.ttitcn.system.entity.SysDictType;
import cn.ttitcn.system.service.SysDictDataService;
import cn.ttitcn.system.service.SysDictTypeService;

/**
 * 数据字典类型管理
 */
@Controller
@RequestMapping("/system/dict")
public class SysDictTypeController extends BaseController {
	
    private static String prefix = "system/dict/type";

	@Autowired
	private SysDictTypeService dictTypeService;
	@Autowired
	private SysDictDataService dictDataService;

	@RequiresPermissions("system:dict:view")
	@GetMapping()
	public String dictType() {
		return prefix + "/type";
	}

	@PostMapping("/list")
	@RequiresPermissions("system:dict:list")
	@ResponseBody
	public TableDataInfo list(SysDictType dictType) {
		startPage();
		List<SysDictType> list = dictTypeService.list(dictType);
		return getDataTable(list);
	}

	@Log(title = "字典类型", businessType = BusinessType.EXPORT)
	@RequiresPermissions("system:dict:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysDictType dictType) {
		List<SysDictType> list = dictTypeService.list(dictType);
		ExcelUtil<SysDictType> util = new ExcelUtil<SysDictType>(SysDictType.class);
		return util.exportExcel(list, "字典类型");
	}

	/**
	 * 新增字典类型
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存字典类型
	 */
	@Log(title = "字典类型", businessType = BusinessType.INSERT)
	@RequiresPermissions("system:dict:add")
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysDictType dict) {
		if (UserConstants.DICT_TYPE_NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
			return error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
		}
		dict.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(dictTypeService.add(dict));
	}

	/**
	 * 修改字典类型
	 */
	@GetMapping("/edit/{dictId}")
	public String edit(@PathVariable("dictId") Long dictId, ModelMap mmap) {
		mmap.put("dict", dictTypeService.getById(dictId));
		return prefix + "/edit";
	}

	/**
	 * 修改保存字典类型
	 */
	@Log(title = "字典类型", businessType = BusinessType.UPDATE)
	@RequiresPermissions("system:dict:edit")
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysDictType dict) {
		if (UserConstants.DICT_TYPE_NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
			return error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
		}
		// 删除缓存
		dictTypeService.deleteRedis(SystemRedisKey.SYSTEM_CONFIG_PREFIX + dict.getDictType());
		
		dict.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(dictTypeService.update(dict));
	}

	@Log(title = "字典类型", businessType = BusinessType.DELETE)
	@RequiresPermissions("system:dict:remove")
	@PostMapping("/remove")
	@ResponseBody
	@Transactional
	public AjaxResult remove(String ids) {
		
		// 先检查每一个是否有子项
		List<SysDictType> list = new ArrayList<>();
		Long[] dictIds = Convert.toLongArray(ids);
		for (Long dictId : dictIds) {
			SysDictType dictType = dictTypeService.getById(dictId);
			list.add(dictType);
			
			SysDictData data = new SysDictData();
			data.setDictType(dictType.getDictType());
			if (dictDataService.countByType(data) > 0) {
				throw new BusinessException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
			}
		}

		// 删除redis
		for(SysDictType dt : list) {
			dictTypeService.deleteRedis(SystemRedisKey.SYSTEM_CONFIG_PREFIX + dt.getDictType());
		}
		
		String deleteBy = ShiroUtils.getLoginName();
		try {
			for (String id : ids.split(PunctuationConstants.COMMA)) {
				SysDictType dictType = new SysDictType();
				dictType.setDictId(Long.valueOf(id));
				dictType.preDelete();
				dictType.setDeleteBy(deleteBy);
				dictTypeService.delete(dictType);
			}
			return success();
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * 查询字典详细
	 */
	@RequiresPermissions("system:dict:list")
	@GetMapping("/detail/{dictId}")
	public String detail(@PathVariable("dictId") Long dictId, ModelMap mmap) {
		mmap.put("dict", dictTypeService.getById(dictId));
		mmap.put("dictList", dictTypeService.list(new SysDictType()));
		return "system/dict/data/data";
	}

	/**
	 * 校验字典类型
	 */
	@PostMapping("/checkDictTypeUnique")
	@ResponseBody
	public String checkDictTypeUnique(SysDictType dictType) {
		return dictTypeService.checkDictTypeUnique(dictType);
	}
}
