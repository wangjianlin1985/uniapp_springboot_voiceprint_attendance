package cn.ttitcn.system.service;

import cn.ttitcn.common.core.base.BaseService;
import cn.ttitcn.system.entity.SysAtt;

public interface SysAttService extends BaseService<SysAtt>{
	
	SysAtt getByUserAndDate(SysAtt att);
	
	int updateByUserAndDate(SysAtt att);

}
