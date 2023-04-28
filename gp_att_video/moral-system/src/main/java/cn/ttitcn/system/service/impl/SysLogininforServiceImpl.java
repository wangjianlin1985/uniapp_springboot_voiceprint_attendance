package cn.ttitcn.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ttitcn.common.core.base.BaseServiceImpl;
import cn.ttitcn.system.dao.SysLogininforDao;
import cn.ttitcn.system.entity.SysLogininfor;
import cn.ttitcn.system.service.SysLogininforService;

@Service
public class SysLogininforServiceImpl extends BaseServiceImpl<SysLogininfor> implements SysLogininforService{

    @Autowired
    private SysLogininforDao dao;
    
    @Override
    public int clean() {
        return dao.clean();
    }

}
