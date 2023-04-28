package cn.ttitcn.system.entity;

import cn.ttitcn.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class SysAtt extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String date;
	
	private SysUser user;
	
	private SysDept dept;
	
	private String time;
	
	private String status; 

}
