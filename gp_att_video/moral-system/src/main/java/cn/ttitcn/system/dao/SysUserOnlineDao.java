package cn.ttitcn.system.dao;

import java.util.List;

import cn.ttitcn.common.core.base.BaseDao;
import cn.ttitcn.system.entity.SysUserOnline;

public interface SysUserOnlineDao extends BaseDao<SysUserOnline>{
	SysUserOnline selectOnlineById(String sessionId);
	int deleteOnlineById(String sessionId);
	int saveOnline(SysUserOnline online);
	List<SysUserOnline> selectOnlineByExpired(String lastAccessTime);
	void deleteOnlineByIds(String[] sessionIds);
}
