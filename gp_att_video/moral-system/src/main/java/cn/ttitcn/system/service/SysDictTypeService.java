package cn.ttitcn.system.service;

import cn.ttitcn.common.core.base.BaseService;
import cn.ttitcn.system.entity.SysDictType;

public interface SysDictTypeService extends BaseService<SysDictType> {
    
    /**
     * 检查名称是否唯一
     * @param dictType
     * @return
     */
    String checkDictTypeUnique(SysDictType dictType);
}
