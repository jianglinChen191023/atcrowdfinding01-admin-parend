package com.atguigu.crowd.test;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/* 在类上标记必要的注解, Spring 整合 Junit */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Test
    public void testTx() {
        Admin admin = new Admin(null, "jerry", "123123", "杰瑞", "jerry@qq.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testLog() {
        // 1. 获取 Logger(日志记录) 对象, 这里传入的 Class 对象就是当前打印的类
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);

        // 2. 根据不同日志级别打印日志
        logger.debug("Hello I an Debug Level!!!");
        logger.debug("Hello I an Debug Level!!!");
        logger.debug("Hello I an Debug Level!!!");

        logger.info("Hello I an Debug Level!!!");
        logger.info("Hello I an Debug Level!!!");
        logger.info("Hello I an Debug Level!!!");

        logger.warn("Hello I an Debug Level!!!");
        logger.warn("Hello I an Debug Level!!!");
        logger.warn("Hello I an Debug Level!!!");

        logger.error("Hello I an Debug Level!!!");
        logger.error("Hello I an Debug Level!!!");
        logger.error("Hello I an Debug Level!!!");
    }

    @Test
    public void testInsertAdmin() {
        Admin admin = new Admin(null, "tom", "123123", "汤姆", "tom@qq.com", null);
        int insert = adminMapper.insert(admin);

        // 在实际开发中, 所有想查看数值的地方都所有 sysout 方式打印, 会给项目上线运行带来问题
        // sysout 本质上是一个 IO 操作, 通过 IO 的操作是比较消耗性能的, 如果项目中 sysout 多, 那么对性能的影响就比较大了
        // 即使上线前专门花时间删除代码中的 sysout, 也就可能有遗漏, 而且非常麻烦
        // 而如果使用日志系统, 那么通过日志级别就可以批量的控制信息的打印
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
