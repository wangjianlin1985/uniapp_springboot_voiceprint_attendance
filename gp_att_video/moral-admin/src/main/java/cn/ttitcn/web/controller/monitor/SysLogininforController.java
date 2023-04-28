package cn.ttitcn.web.controller.monitor;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ttitcn.common.annotation.Log;
import cn.ttitcn.common.core.base.BaseController;
import cn.ttitcn.common.core.domain.AjaxResult;
import cn.ttitcn.common.core.page.TableDataInfo;
import cn.ttitcn.common.enums.BusinessType;
import cn.ttitcn.common.util.ExcelUtil;
import cn.ttitcn.system.entity.SysLogininfor;
import cn.ttitcn.system.service.SysLogininforService;

/**
 * 登录日志监控
 */
@Controller
@RequestMapping("/monitor/logininfor")
public class SysLogininforController extends BaseController {
	
    private static String prefix = "monitor/logininfor";

	@Autowired
	private SysLogininforService logininforService;

	@RequiresPermissions("monitor:logininfor:view")
	@GetMapping()
	public String logininfor() {
		return prefix + "/logininfor";
	}

	/**
	 * 登录日志，取消权限认证，方便在个人中心查看
	 * RequiresPermissions("monitor:logininfor:list")
	 * @param logininfor
	 * @return
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysLogininfor logininfor) {
		startPage();
		List<SysLogininfor> list = logininforService.list(logininfor);
		return getDataTable(list);
	}

	@Log(title = "登陆日志", businessType = BusinessType.EXPORT)
	@RequiresPermissions("monitor:logininfor:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysLogininfor logininfor) {
		List<SysLogininfor> list = logininforService.list(logininfor);
		ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
		return util.exportExcel(list, "登陆日志");
	}

	@RequiresPermissions("monitor:logininfor:remove")
	@Log(title = "登陆日志", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(logininforService.deleteByIds(ids));
	}

	@RequiresPermissions("monitor:logininfor:remove")
	@Log(title = "登陆日志", businessType = BusinessType.CLEAN)
	@PostMapping("/clean")
	@ResponseBody
	public AjaxResult clean() {
		logininforService.clean();
		return success();
	}
}
