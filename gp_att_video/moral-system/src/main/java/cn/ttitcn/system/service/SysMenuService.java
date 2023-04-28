package cn.ttitcn.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.ttitcn.common.core.base.BaseService;
import cn.ttitcn.common.core.domain.Ztree;
import cn.ttitcn.system.entity.SysMenu;
import cn.ttitcn.system.entity.SysRole;
import cn.ttitcn.system.entity.SysUser;

public interface SysMenuService extends BaseService<SysMenu> {
    
    Set<String> selectPermsByUserId(SysUser user);
    
    List<SysMenu> selectMenuAll(Long userId);
    
    List<SysMenu> selectMenusByUser(SysUser user);
    
    List<SysMenu> selectMenuList(SysMenu menu, Long userId);
    
    List<Ztree> roleMenuTreeData(SysRole role, Long userId);
    
    List<Ztree> menuTreeData(Long userId);
    
    Map<String, String> selectPermsAll(Long userId);
    
    int selectCountMenuByParentId(SysMenu menu);
    
    int selectCountRoleMenuByMenuId(Long menuId);
    
    
    
}
