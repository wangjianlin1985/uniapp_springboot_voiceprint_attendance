package cn.ttitcn.system.dao;

import java.util.List;

import cn.ttitcn.common.core.base.BaseDao;
import cn.ttitcn.system.entity.SysUser;

public interface SysUserDao extends BaseDao<SysUser>{
	List<SysUser> selectAllocatedList(SysUser user);
	List<SysUser> selectUnallocatedList(SysUser user);
	SysUser selectUserByLoginName(SysUser user);
	int checkLoginNameUnique(SysUser user);
	SysUser checkUsercodeUnique(SysUser user);
	SysUser checkIdcardnoUnique(SysUser user);
	List<SysUser> selectUserByRoleId(Long roleId);
	SysUser selectUserByOpenid(String openid);
	SysUser getByFeatureId(String featureId);
	int updateOpenid(SysUser user);
	int cleanOpenid(String loginName);
	List<SysUser> listAllTeacher(SysUser user);

	int updateUserName(SysUser user);
	int updatePhonenumber(SysUser user);
	int updateShenhe(SysUser user);
	int updateEmail(SysUser user);
	List<SysUser> selectUserByRoleKey(String key);
	
	int cleanFeatureId(Long userId);
}
