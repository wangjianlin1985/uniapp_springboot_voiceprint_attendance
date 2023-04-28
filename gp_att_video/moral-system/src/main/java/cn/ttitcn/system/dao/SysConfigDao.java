package cn.ttitcn.system.dao;

import cn.ttitcn.common.core.base.BaseDao;
import cn.ttitcn.system.entity.SysConfig;

public interface SysConfigDao extends BaseDao<SysConfig>{
    
    /**
     * 根据configKey查询Sysconfig
     * @param configKey
     * @return
     */
    SysConfig checkConfigKeyUnique(SysConfig config);
    
    /**
     * 通过configKey获取configValue的值
     * @param configKey
     * @return
     */
    String getConfigByKey(SysConfig config);
    
}
