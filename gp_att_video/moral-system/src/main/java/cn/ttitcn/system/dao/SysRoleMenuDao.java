package cn.ttitcn.system.dao;

import cn.ttitcn.common.core.base.BaseDao;
import cn.ttitcn.system.entity.SysRoleMenu;

public interface SysRoleMenuDao extends BaseDao<SysRoleMenu>{
	int deleteRoleMenuByRoleId(Long roleId);
	int deleteRoleMenu(Long[] ids);
	int selectCountRoleMenuByMenuId(Long menuId);
}
