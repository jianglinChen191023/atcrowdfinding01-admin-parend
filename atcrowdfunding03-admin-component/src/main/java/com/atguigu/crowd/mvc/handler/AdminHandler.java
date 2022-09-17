package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {

    @Autowired
    AdminService adminService;

    /**
     * 修改 Admin 数据信息
     *
     * @param admin
     * @param pageNum
     * @param keyword
     * @return
     */
    @PreAuthorize("hasAuthority('user:update')")
    @RequestMapping("/admin/update.html")
    public String updateAdmin(
            Admin admin,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword) {
        adminService.updateAdmin(admin);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    /**
     * 根据 id 获取 Admin 对象
     * 并跳转至修改页面
     *
     * @param adminId
     * @param modelMap
     * @return
     */
    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(
            @RequestParam("adminId") Integer adminId,
            ModelMap modelMap
    ) {
        // 1.根据 adminId 查询 Admin 对象
        Admin admin = adminService.getAdminById(adminId);
        // 2. 将 Admin 对象存入模型
        modelMap.addAttribute("admin", admin);
        return "admin-edit";
    }

    /**
     * 保存 Admin
     *
     * @param admin
     * @return
     */
    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/admin/save.html")
    public String save(Admin admin) {
        adminService.saveAdmin(admin);
        // 分页最后一页, 直接看到新增加的数据
        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    /**
     * 根据 id 删除对应数据
     *
     * @param adminId
     * @param pageNum
     * @param keyword
     * @return
     */
    @PreAuthorize("hasAuthority('user:delete')")
    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String removeAdminById(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword
    ) {
        // 执行删除
        adminService.removeAdminById(adminId);
        // 1. 页面跳转: 回到分页页面, admin-page.jsp 会无法显示数据
        // return "admin-page"

        // 2. 转发到 /admin/get/page.html 地址, 一旦刷新页面会重复执行删除
        // return "forward:/admin/get/page.html";

        // 3. 重定向到 /admin/get/page.html 地址
        // 同时为了保持原本所在的页面和查询关键词再附加 pageNum 和 keyword 两个请求参数
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    /**
     * 获取分页信息
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @PreAuthorize("hasAuthority('user:get')")
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(
            // 使用 @RequestParam 注解的 defaultValue 属性, 指定默认值, 在请求中没有携带对应参数时使用默认值
            // keyword 默认值使用空字符串, 和 SQL 语句配合实现两种情况适配
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            ModelMap modelMap
    ) {
        // 调用 adminService 获取 PageInfo 对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        // 将 PageInfo 对象传入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);

        return "admin-page";
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session) {
        // 强制 Session 失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping("/admin/do/login.html")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session
    ) {
        // 调用 Service 方法执行登录检查
        // 这个方法如果能够返回 admin 对象说明登录成功,
        // - 如果账号、密码不正确则会抛出异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

        // 将登录成功返回的 admin 对象存入 Session 域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/to/main/page.html";
    }

}
