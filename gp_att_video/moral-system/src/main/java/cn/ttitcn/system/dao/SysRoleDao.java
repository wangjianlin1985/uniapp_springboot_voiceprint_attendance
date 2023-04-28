package cn.ttitcn.system.dao;

import java.util.List;

import cn.ttitcn.common.core.base.BaseDao;
import cn.ttitcn.system.entity.SysRole;

public interface SysRoleDao extends BaseDao<SysRole>{

	List<SysRole> selectRolesByUserId(Long userId);
	
	SysRole checkRoleKeyUnique(SysRole role);
	
	int countUserRoleByRoleId(Long roleId);
	
}
