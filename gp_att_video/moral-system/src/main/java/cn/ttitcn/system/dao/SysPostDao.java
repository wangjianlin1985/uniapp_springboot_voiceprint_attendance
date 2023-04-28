package cn.ttitcn.system.dao;

import java.util.List;

import cn.ttitcn.common.core.base.BaseDao;
import cn.ttitcn.system.entity.SysPost;

public interface SysPostDao extends BaseDao<SysPost>{

	List<SysPost> selectPostsByUserId(Long userId);
	
	int countUserPostById(Long postId);
	
	SysPost checkPostCodeUnique(SysPost post);
	
}
