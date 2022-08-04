package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * 考虑到 User 对象中仅仅包含账号和密码, 为了能够获取原始的 Admin 对象, 专门创建这个类对 User 类进行扩展
 */
public class SecurityAdmin extends User {
    private static final long serialVersionUID = 1L;

    // 原始的 Admin 对象, 包含 Admin 对象的全部属性
    private Admin originalAdmin;

    /**
     *
     * @param originalAdmin 原始的 Admin 对象
     * @param authorities 创建角色、权限信息的集合
     */
    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities) {
        // 调用父类构造器
        super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), authorities);
        // 给本类的 this.originalAdmin 赋值
        this.originalAdmin = originalAdmin;
    }

    // 对外提供的获取原始 Admin 对象的 getXxx 方法
    public Admin getOriginalAdmin() {
        return originalAdmin;
    }

}
