package cn.ttitcn.system.service;

import java.util.List;

import cn.ttitcn.common.core.base.BaseService;
import cn.ttitcn.system.entity.SysPost;

public interface SysPostService extends BaseService<SysPost>{
	
	List<SysPost> selectPostsByUserId(Long userId);
	
	String checkPostCodeUnique(SysPost post);
	
	int countUserPostById(Long postId);
	
}
