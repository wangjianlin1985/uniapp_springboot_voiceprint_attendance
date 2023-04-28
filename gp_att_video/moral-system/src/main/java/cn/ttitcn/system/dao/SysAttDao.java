package cn.ttitcn.system.dao;

import cn.ttitcn.common.core.base.BaseDao;
import cn.ttitcn.system.entity.SysAtt;

public interface SysAttDao extends BaseDao<SysAtt>{
	
	SysAtt getByUserAndDate(SysAtt att);
	
	int updateByUserAndDate(SysAtt att);

}
