package cn.ttitcn.system.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import cn.ttitcn.common.constant.UserConstants;
import cn.ttitcn.common.core.base.BaseServiceImpl;
import cn.ttitcn.common.util.StringUtils;
import cn.ttitcn.system.constant.SystemRedisKey;
import cn.ttitcn.system.dao.SysConfigDao;
import cn.ttitcn.system.entity.SysConfig;
import cn.ttitcn.system.service.SysConfigService;

@Service
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfig> implements SysConfigService {

    @Autowired
    private SysConfigDao dao;

    @Override
    public String checkConfigKeyUnique(SysConfig config) {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        SysConfig info = dao.checkConfigKeyUnique(config);
        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue()) {
            return UserConstants.CONFIG_KEY_NOT_UNIQUE;
        }
        return UserConstants.CONFIG_KEY_UNIQUE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getConfigByKey(String configKey) {
        String key = SystemRedisKey.SYSTEM_CONFIG_PREFIX + configKey;
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        Object o = operations.get(key);
        if(null == o){
            synchronized (this) {
                o = operations.get(key);
                if(null == o){
                    SysConfig config = new SysConfig();
                    config.setConfigKey(configKey);
                    o = dao.getConfigByKey(config);
                    operations.set(key,o, 10, TimeUnit.DAYS); 
                }
            }
        }
        return (String)o;
    }

}
