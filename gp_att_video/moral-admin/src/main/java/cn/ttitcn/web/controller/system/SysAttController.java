package cn.ttitcn.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ttitcn.common.core.base.BaseController;
import cn.ttitcn.common.core.domain.AjaxResult;
import cn.ttitcn.common.core.page.TableDataInfo;
import cn.ttitcn.common.util.ExcelUtil;
import cn.ttitcn.system.entity.SysAtt;
import cn.ttitcn.system.service.SysAttService;

@Controller
@RequestMapping("/system/att")
public class SysAttController extends BaseController{
	
	private static String prefix = "system/att";

	@Autowired
	private SysAttService attService;
	
	@GetMapping()
	public String att() {
		return prefix + "/att";
	}
	
	/**
	 * 查询列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysAtt att) {
		startPage();
		List<SysAtt> list = attService.list(att);
		//List<SysAtt> list = attService.list(null);
		return getDataTable(list);
	}

	
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysAtt att) {
		List<SysAtt> list = attService.list(att);
		ExcelUtil<SysAtt> util = new ExcelUtil<SysAtt>(SysAtt.class);
		return util.exportExcel(list, "考勤数据");
	}
	

}
