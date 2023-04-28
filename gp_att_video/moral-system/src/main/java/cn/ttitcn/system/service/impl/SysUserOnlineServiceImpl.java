package cn.ttitcn.system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ttitcn.common.core.base.BaseServiceImpl;
import cn.ttitcn.common.util.DateUtils;
import cn.ttitcn.system.dao.SysUserOnlineDao;
import cn.ttitcn.system.entity.SysUserOnline;
import cn.ttitcn.system.service.SysUserOnlineService;

@Service
public class SysUserOnlineServiceImpl extends BaseServiceImpl<SysUserOnline> implements SysUserOnlineService{

	@Autowired
	private SysUserOnlineDao dao;
	
	@Override
	public SysUserOnline selectOnlineById(String sessionId) {
		return dao.selectOnlineById(sessionId);
	}

	@Override
	public void deleteOnlineById(String sessionId) {
		dao.deleteOnlineById(sessionId);
	}

	@Override
	public void saveOnline(SysUserOnline online) {
		dao.saveOnline(online);
	}

	@Override
	public List<SysUserOnline> selectOnlineByExpired(Date expiredDate) {
		String lastAccessTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, expiredDate);
        return dao.selectOnlineByExpired(lastAccessTime);
	}

	@Override
	public void forceLogout(String sessionId) {
		dao.deleteOnlineById(sessionId);
	}

	@Override
	public void deleteOnlineByIds(String[] sessionIds) {
		dao.deleteOnlineByIds(sessionIds);
	}

}
