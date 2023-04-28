package cn.ttitcn.system.dao;

import org.apache.ibatis.annotations.Param;

import cn.ttitcn.common.core.base.BaseDao;
import cn.ttitcn.system.entity.SysUserRole;

public interface SysUserRoleDao extends BaseDao<SysUserRole>{

	int deleteUserRoleByUserId(Long userId);
	int deleteUserRole(Long[] ids);
	int countUserRoleByRoleId(Long roleId);
	int deleteUserRoleInfo(SysUserRole userRole);
	int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);
	int deleteAuthUsers(Long roleId);
}
