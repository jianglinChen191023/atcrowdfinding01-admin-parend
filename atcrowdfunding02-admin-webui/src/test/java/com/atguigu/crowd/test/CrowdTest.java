package com.atguigu.crowd.test;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.mapper.AdminMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/* 在类上标记必要的注解, Spring 整合 Junit */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void testInsertAdmin() {
        Admin admin = new Admin(null, "tom", "123123", "汤姆", "tom@qq.com", null);
        int insert = adminMapper.insert(admin);
        System.out.println("受影响的行数" + insert);
    }

    @Test
    public void testConnection() throws SQLException {
        // 1. 通过数据源对象获取数据源连接
        Connection connection = dataSource.getConnection();
        // 2. 打印数据库连接
        System.out.println(connection);
    }

}
