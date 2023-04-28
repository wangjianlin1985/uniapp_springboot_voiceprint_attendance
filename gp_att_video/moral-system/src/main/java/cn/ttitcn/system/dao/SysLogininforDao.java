package cn.ttitcn.system.dao;

import cn.ttitcn.common.core.base.BaseDao;
import cn.ttitcn.system.entity.SysLogininfor;

public interface SysLogininforDao extends BaseDao<SysLogininfor>{

    // 清空
    int clean();
    
}
