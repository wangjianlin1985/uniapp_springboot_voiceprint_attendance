package cn.ttitcn.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ttitcn.common.core.base.BaseServiceImpl;
import cn.ttitcn.system.dao.SysOperLogDao;
import cn.ttitcn.system.entity.SysOperCount;
import cn.ttitcn.system.entity.SysOperLog;
import cn.ttitcn.system.service.SysOperLogService;

@Service
public class SysOperLogServiceImpl extends BaseServiceImpl<SysOperLog> implements SysOperLogService{

	@Autowired
	private SysOperLogDao dao;
	
	@Override
	public int clean() {
		return dao.clean();
	}

	@Override
	public List<SysOperCount> listOperCount(Map<String, Object> map) {
		return dao.listOperCount(map);
	}

	@Override
	public List<SysOperCount> listOperCountDate(Map<String, Object> map) {
		return dao.listOperCountDate(map);
	}

}
