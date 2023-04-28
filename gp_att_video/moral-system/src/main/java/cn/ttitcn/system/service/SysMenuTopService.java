package cn.ttitcn.system.service;

import java.util.List;

import cn.ttitcn.common.core.base.BaseService;
import cn.ttitcn.system.entity.SysMenuTop;

public interface SysMenuTopService extends BaseService<SysMenuTop> {
    
    List<SysMenuTop> listAllRedis();

}
