package cn.ttitcn.system.service;

import cn.ttitcn.common.core.base.BaseService;
import cn.ttitcn.system.entity.SysLogininfor;

public interface SysLogininforService extends BaseService<SysLogininfor>{

    /**
     * 清空
     * @return
     */
    int clean();
    
}
