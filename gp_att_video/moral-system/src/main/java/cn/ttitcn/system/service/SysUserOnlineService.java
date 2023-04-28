package cn.ttitcn.system.service;

import java.util.Date;
import java.util.List;

import cn.ttitcn.common.core.base.BaseService;
import cn.ttitcn.system.entity.SysUserOnline;

public interface SysUserOnlineService extends BaseService<SysUserOnline>{
	
	SysUserOnline selectOnlineById(String sessionId);
	
	void deleteOnlineById(String sessionId);
	
	void deleteOnlineByIds(String[] sessionIds);
	
	void saveOnline(SysUserOnline online);
	
	List<SysUserOnline> selectOnlineByExpired(Date expiredDate);
	
	void forceLogout(String sessionId);
	
}
