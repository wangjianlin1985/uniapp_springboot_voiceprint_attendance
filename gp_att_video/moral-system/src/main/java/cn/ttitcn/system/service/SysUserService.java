package cn.ttitcn.system.service;

import java.util.List;

import cn.ttitcn.common.core.base.BaseService;
import cn.ttitcn.common.core.domain.Ztree;
import cn.ttitcn.system.entity.SysUser;

public interface SysUserService extends BaseService<SysUser>{

	List<SysUser> selectAllocatedList(SysUser user);
	
	List<SysUser> selectUnallocatedList(SysUser user);
	
	SysUser selectUserByLoginName(SysUser user);
	
	int updateUserInfo(SysUser user);
	
	int resetUserPwd(SysUser user);
	
	SysUser getByFeatureId(String featureId);
	
	String checkLoginNameUnique(SysUser user);
	
	String checkUsercodeUnique(SysUser user);
	
	String checkIdcardnoUnique(SysUser user);
	
	String selectUserRoleGroup(Long userId);
	
	String selectUserPostGroup(Long userId);
	
	String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);
	
	int changeStatus(SysUser user);
	
	List<Ztree> treeData(Integer userType);
	
	List<SysUser> selectUserByRoleId(Long roleId);
	
	List<SysUser> selectUserByRoleKey(String key);
	
	int updateOpenid(SysUser user);
	
	SysUser selectUserByOpenid(String openid);
	
	int cleanOpenid(String loginName);
	
	/**
	 * 学生总数 走缓存，缓存定时删除
	 * @return
	 */
	int countStudent();
	
	/**
     * 教师总数 走缓存，缓存定时删除
     * @return
     */
	int countTeacher();
	
	int updateShenhe(SysUser user);
	
	List<SysUser> listAllTeacher();
	

	int updateUserName(SysUser user);
	int updatePhonenumber(SysUser user);
	int updateEmail(SysUser user);
	
	int cleanFeatureId(Long userId);
	
}
