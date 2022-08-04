package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.service.api.AuthService;
import com.atguigu.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenjianglin
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 根据账号名称查询 Admin 对象
        Admin admin = adminService.getAdminByLoginAcct(username);
        if (admin == null) {
            return null;
        }

        // 2. 获取 adminId
        Integer adminId = admin.getId();

        // 3. 根据 adminId 查询角色信息
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);

        // 4. 根据 amdinId 查询权限信息
        List<String> authNameList = authService.getAssignedAuthNameByAdminId(adminId);

        // 5. 创建集合对象用来存储 GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 6. 遍历 assignedRolelist 存入角色信息
        assignedRoleList.forEach(role -> {
            // 注意不要忘了加前缀
            String roleName = "ROLE_" + role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        });

        // 7. 遍历 authNameList 存入权限信息
        authNameList.forEach(auth -> {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(auth);
            authorities.add(simpleGrantedAuthority);
        });

        // 8. 封装 SecurityAdmin 对象
        SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);

        return securityAdmin;
    }
}
