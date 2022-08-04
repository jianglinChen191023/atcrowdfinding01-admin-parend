package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.Auth;
import com.atguigu.crowd.entity.AuthExample;
import com.atguigu.crowd.mapper.AuthMapper;
import com.atguigu.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.getAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
        // 1. 获取 roleId
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);

        // 删除旧关联关系根据
        authMapper.deleteOldRelationship(roleId);

        // 获取 authIdList
        List<Integer> authIdList = map.get("authIdArray");
        if (authIdList != null && authIdList.size() > 0) {
            authMapper.insertNewRelationship(roleId, authIdList);
        }
    }

    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
        return authMapper.getAssignedAuthNameByAdminId(adminId);
    }
}
