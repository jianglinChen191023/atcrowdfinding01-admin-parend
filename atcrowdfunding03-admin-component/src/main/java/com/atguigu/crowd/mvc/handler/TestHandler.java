package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.ParamData;
import com.atguigu.crowd.entity.Student;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestHandler {

    @Autowired
    private AdminService adminService;

    @ResponseBody
    @RequestMapping("/send/compose/object2.json")
    public ResultEntity<Student> testReceiveComposeObject2(@RequestBody Student student) {
        Logger logger = LoggerFactory.getLogger(TestHandler.class);
        logger.info(student.toString());

        // 将"查询"到的 Student 对象封装到 ResultEntity 中返回
        return ResultEntity.successWithData(student);
    }

    @ResponseBody
    @RequestMapping("/send/compose/object.html")
    public String testReceiveComposeObject(@RequestBody Student student) {
        Logger logger = LoggerFactory.getLogger(TestHandler.class);
        logger.info(student.toString());
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/array/three.html")
    public String testReceiveArrayThree(@RequestBody List<String> list) {
        Logger logger = LoggerFactory.getLogger(TestHandler.class);
        list.forEach(logger::info);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/array/two.html")
    public String testReceiveArrayTwo(ParamData paramData) {
        List<Integer> list = paramData.getArray();

        list.forEach(System.out::println);

        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/array.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> list) {
        list.forEach(System.out::println);

        return "success";
    }

    @RequestMapping("/test/ssm.html")
    public String testSsm(ModelMap modelMap) {
        List<Admin> adminList = adminService.getAll();
        System.out.println(adminList);
        modelMap.addAttribute("adminList", adminList);
        modelMap.addAttribute("adminList2", adminList);
        modelMap.addAttribute("adminList4", adminList);

        return "/target";
    }

}
