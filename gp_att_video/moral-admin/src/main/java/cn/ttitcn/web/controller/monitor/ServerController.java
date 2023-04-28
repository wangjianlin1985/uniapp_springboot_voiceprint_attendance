package cn.ttitcn.web.controller.monitor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ttitcn.common.core.base.BaseController;
import cn.ttitcn.framework.web.domain.Server;

/**
 *  系统资源监控
 */
@Controller
@RequestMapping("/monitor/server")
public class ServerController extends BaseController {
    
	private static String prefix = "monitor/server";

	@RequiresPermissions("monitor:server:view")
	@GetMapping()
	public String server(ModelMap mmap) throws Exception {
		Server server = new Server();
		server.copyTo();
		mmap.put("server", server);
		return prefix + "/server";
	}
}