package com.rg.aclservice.service;

import com.alibaba.fastjson.JSONObject;
import com.rg.aclservice.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 获取所有菜单
     * @return
     */
    List<Permission> queryAllMenu();

    /**
     * 递归删除菜单
     * @param id
     */
    void removeChildById(String id);

    /**
     * 给角色分配权限
     * @param roleId
     * @param permissionId
     */
    void saveRolePermissionRealtion(String roleId, String[] permissionId);

    //根据角色获取菜单
    List<Permission> selectAllMenu(String roleId);


    List<JSONObject> selectPermissionByUserId(String id);


    List<String> selectPermissionValueByUserId(String id);
}
