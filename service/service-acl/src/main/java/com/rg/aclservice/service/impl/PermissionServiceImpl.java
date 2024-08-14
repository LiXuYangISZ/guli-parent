package com.rg.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rg.aclservice.entity.Permission;
import com.rg.aclservice.entity.RolePermission;
import com.rg.aclservice.entity.User;
import com.rg.aclservice.helper.MemuHelper;
import com.rg.aclservice.helper.PermissionHelper;
import com.rg.aclservice.mapper.PermissionMapper;
import com.rg.aclservice.service.PermissionService;
import com.rg.aclservice.service.RolePermissionService;
import com.rg.aclservice.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;
    
    @Autowired
    private UserService userService;

    //根据角色获取菜单
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }


        List<Permission> permissionList = buildPermission(allPermissionList);
        return permissionList;
    }


    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        List<JSONObject> result = MemuHelper.bulid(permissionList);
        return result;
    }

    //===================================根据用户id获取用户菜单==============================
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    //============递归查询菜单================================================================
    /**
     * 获取所有的菜单
     * @return
     */
    @Override
    public List <Permission> queryAllMenu() {
        LambdaQueryWrapper <Permission> wrapper = new LambdaQueryWrapper <>();
        wrapper.orderByDesc(Permission::getId);
        List <Permission> permissionList = this.baseMapper.selectList(wrapper);
        List<Permission> list = this.buildPermission(permissionList);
        return list;
    }

    /**
     * 构建
     * @param permissionList
     * @return
     */
    private List<Permission> buildPermission(List<Permission> permissionList) {
        ArrayList <Permission> rootPermisson = new ArrayList <>();
        for (Permission permission : permissionList) {
            if (permission.getPid().equals("0")) {
                permission.setLevel(1);
                List<Permission> childrenPermisson = this.findChildren(permission,permissionList);
                permission.setChildren(childrenPermisson);
                rootPermisson.add(permission);
            }
        }
        return rootPermisson;
    }

    /**
     * 菜单的子菜单
     * @param permission
     * @param permissionList
     * @return
     */
    private List<Permission> findChildren(Permission permission, List<Permission> permissionList) {
        ArrayList <Permission> result = new ArrayList <>();
        for (Permission newPermisson : permissionList) {
            if(permission.getId().equals(newPermisson.getPid())){
                newPermisson.setLevel(permission.getLevel()+1);
                // 递归设置子菜单
                newPermisson.setChildren(this.findChildren(newPermisson, permissionList));
                result.add(newPermisson);
            }
        }
        return result;
    }


    //===================================删除菜单==========================================
    @Override
    public void removeChildById(String id) {
        List <Permission> permissionList = this.baseMapper.selectList(new LambdaQueryWrapper <Permission>().orderByAsc(Permission::getId));
        List <String> idList = new ArrayList <>();
        // 收集子类菜单列表
        this.collectChildIds(id,permissionList,idList);
        idList.add(id);
        // 删除当前菜单
        this.baseMapper.deleteBatchIds(idList);
    }

    /**
     * 收集子类菜单列表
     *
     * @param id
     * @param permissionList
     * @param idList
     * 注意：因为Java方法对于对象是引用的传递，所以并不需要返回值
     */
    private void collectChildIds(String id, List<Permission> permissionList, List <String> idList) {
        for (Permission permission : permissionList) {
            if (permission.getPid().equals(id)){
                // 递归寻找子类
                this.collectChildIds(permission.getId(),permissionList, idList);
                // 加入列表
                idList.add(permission.getId());
            }
        }
    }


    // ====================================给角色分配权限==========================================
    @Override
    public void saveRolePermissionRealtion(String roleId, String[] permissionIdList) {
        List <RolePermission> list = new ArrayList <>();
        for (String permissonId : permissionIdList) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissonId);
            list.add(rolePermission);
        }
        this.rolePermissionService.saveBatch(list);
    }
}
