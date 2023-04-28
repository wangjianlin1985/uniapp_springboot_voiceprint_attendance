package cn.ttitcn.system.dao;

import cn.ttitcn.common.core.base.BaseDao;
import cn.ttitcn.system.entity.SysUserPost;

public interface SysUserPostDao extends BaseDao<SysUserPost>{

	int deleteUserPostByUserId(Long userId);
	int countUserPostById(Long postId);
	
}
