package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.AdminExample;
import com.atguigu.crowd.exception.LoginFailedException;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);

        throw new RuntimeException();
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        // 1. 根据登录账号查询 Admin 对象
        // 1.1. 创建 AdminExample 对象
        AdminExample adminExample = new AdminExample();
        // 1.2. 创建 Criteria 对象
        AdminExample.Criteria criteria = adminExample.createCriteria();
        // 1.3. 在 Criteria 对象中封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);
        // 1.4. 调用 AdminMapper 的方法执行查询
        List<Admin> list = adminMapper.selectByExample(adminExample);

        if (list == null || list.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        if (list.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        Admin admin = list.get(0);
        // 2. 判断 Admin 对象是否为空
        if (admin == null) {
            // 2.1 如果 Admin 对象为空, 抛出异常
            throw new LoginFailedException();
        }

        // 2.2 如果 Admin 对象存在
        // 2.2.1 将数据库密码从 Admin 对象中取出
        String userPswdDB = admin.getUserPswd();

        // 2.2.2 将表单提交的明文密码进行加密
        String userPswdForm = CrowdUtil.md5(userPswd);
        // 2.2.3 比较密码
        if (!Objects.equals(userPswdDB, userPswdForm)) {
            // 2.2.3.1 密码不一致 抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 2.2.3.2 密码一致 返回 Admin 对象
        return admin;
    }

}
