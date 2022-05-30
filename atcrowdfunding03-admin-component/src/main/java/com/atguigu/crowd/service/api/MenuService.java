package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Menu;

import java.util.List;

public interface MenuService {
    /**
     * 查询所有
     *
     * @return
     */
    List<Menu> getAll();

    /**
     * 保存菜单
     *
     * @param menu
     */
    void saveMenu(Menu menu);

    /**
     * 更新菜单
     *
     * @param menu
     */
    void updateMenu(Menu menu);

    /**
     * 根据 id 删除菜单
     *
     * @param id
     */
    void deleteMenuById(Integer id);
}
