package cn.ttitcn.system.entity;

import lombok.Data;

/**
 * 操作数据统计，大图查询mybatis使用接收参数
 */
@Data
public class SysOperCount {
	
	private Long operUserId;
	
	private String operUserName;
	
	private Integer count;
	
	private Integer operUserType;
	
	private String operDate;

}
