package cn.ttitcn.system.service;

import java.util.List;

import cn.ttitcn.common.core.base.BaseService;
import cn.ttitcn.common.core.domain.Ztree;
import cn.ttitcn.system.entity.SysDept;
import cn.ttitcn.system.entity.SysRole;

public interface SysDeptService extends BaseService<SysDept>{

    /**
     * 查询部门树
     * @param dept
     * @return
     */
    List<Ztree> selectDeptTree(SysDept dept);
    
    /**
     * 根据角色查询所关联的部门树
     * @param role
     * @return
     */
    List<Ztree> roleDeptTreeData(SysRole role);
    
    /**
     * 判断该部分下是不是有人
     * @param deptId
     * @return
     */
    boolean checkDeptExistUser(Long deptId);
    
    int selectDeptCount(SysDept dept);
    
}
