package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 保存角色
     *
     * @param role
     */
    void saveRole(Role role);

    /**
     * 修改角色信息
     *
     * @param role
     */
    void updateRole(Role role);

    /**
     * 单条删除和批量删除
     *
     * @param roleList
     */
    void removeRole(List<Integer> roleList);
}
