package cn.ttitcn.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ttitcn.common.core.base.BaseServiceImpl;
import cn.ttitcn.system.dao.SysAttDao;
import cn.ttitcn.system.entity.SysAtt;
import cn.ttitcn.system.service.SysAttService;

@Service
public class SysAttServiceImpl extends BaseServiceImpl<SysAtt> implements SysAttService{

	@Autowired
	private SysAttDao dao;
	
	@Override
	public SysAtt getByUserAndDate(SysAtt att) {
		return dao.getByUserAndDate(att);
	}

	@Override
	public int updateByUserAndDate(SysAtt att) {
		return dao.updateByUserAndDate(att);
	}

}
