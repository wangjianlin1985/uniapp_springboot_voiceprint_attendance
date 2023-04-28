package cn.ttitcn.system.service;

import java.util.List;
import java.util.Map;

import cn.ttitcn.common.core.base.BaseService;
import cn.ttitcn.system.entity.SysOperCount;
import cn.ttitcn.system.entity.SysOperLog;

public interface SysOperLogService extends BaseService<SysOperLog>{

	// 清空
	int clean();
	
	/**
	 * 查询时间段内某用户类型中所有人的操作次数
	 * @param map
	 * @return
	 */
	List<SysOperCount> listOperCount(Map<String, Object> map);
	
	/**
	 * 查询时间段内某用户类型中所有人的操作次数.按天查询
	 * @param map
	 * @return
	 */
	List<SysOperCount> listOperCountDate(Map<String, Object> map);
	
}
