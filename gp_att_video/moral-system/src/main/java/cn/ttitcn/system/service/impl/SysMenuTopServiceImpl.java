package cn.ttitcn.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.ttitcn.common.core.base.BaseServiceImpl;
import cn.ttitcn.system.constant.SystemRedisKey;
import cn.ttitcn.system.entity.SysMenuTop;
import cn.ttitcn.system.service.SysMenuTopService;

@Service
public class SysMenuTopServiceImpl extends BaseServiceImpl<SysMenuTop> implements SysMenuTopService{

    @Override
    public List<SysMenuTop> listAllRedis() {
        return super.listRedis(new SysMenuTop(), SystemRedisKey.SYSTEM_MENU_TOP_ALL);
    }

}
