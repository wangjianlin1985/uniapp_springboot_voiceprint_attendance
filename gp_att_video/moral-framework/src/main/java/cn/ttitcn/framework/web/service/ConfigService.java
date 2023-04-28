package cn.ttitcn.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ttitcn.system.service.SysConfigService;

/**
 * 页面调用的configService
 */
@Service("config")
public class ConfigService {

    @Autowired
    private SysConfigService configService;

    /**
     * 前端根据configKey获取configValue的值
     * 用法：th:text="${@config.getKey('configKey')}"
     * @param configKey
     * @return
     */
    public String getKey(String configKey) {
        return configService.getConfigByKey(configKey);
    }

}
