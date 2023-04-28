package cn.ttitcn.system.dao;

import cn.ttitcn.common.core.base.BaseDao;
import cn.ttitcn.system.entity.SysRoleDept;

public interface SysRoleDeptDao extends BaseDao<SysRoleDept>{

	/**
	 * 根据roleId删除
	 * @param roleId
	 * @return
	 */
	int deleteRoleDeptByRoleId(Long roleId);
	
	/**
	 * 查询部门使用数量
	 * @param deptId
	 * @return
	 */
	int selectCountRoleDeptByDeptId(Long deptId);
	
}
