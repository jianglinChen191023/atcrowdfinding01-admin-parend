package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    /**
     * 根据 keyword 查看分页数据
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 根据 id 删除对应数据
     *
     * @param adminId
     */
    void removeAdminById(Integer adminId);

    /**
     * 根据 id 获取 Admin 对象
     *
     * @param adminId
     * @return
     */
    Admin getAdminById(Integer adminId);

    /**
     * 修改 Admin 数据信息
     *
     * @param admin
     */
    void updateAdmin(Admin admin);
}
