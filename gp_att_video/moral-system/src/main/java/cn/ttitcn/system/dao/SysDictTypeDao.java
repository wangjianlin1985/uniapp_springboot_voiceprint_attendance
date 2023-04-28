package cn.ttitcn.system.dao;

import cn.ttitcn.common.core.base.BaseDao;
import cn.ttitcn.system.entity.SysDictType;

public interface SysDictTypeDao extends BaseDao<SysDictType>{
    
    /**
     * 根据dictType判断是否唯一
     * @param dictType
     * @return
     */
    SysDictType checkDictTypeUnique(SysDictType dictType);

}
