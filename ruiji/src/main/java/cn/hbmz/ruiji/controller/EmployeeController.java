package cn.hbmz.ruiji.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hbmz.ruiji.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import cn.hbmz.ruiji.common.R;
import cn.hbmz.ruiji.entity.Employee;

@Slf4j
@RequestMapping("/employee")
@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /**
     * 员工登录
     * 
     * @param employee
     * @param request
     * @return
     */
    @PostMapping("/login")
    public R<Employee> Login(@RequestBody Employee employee, HttpServletRequest request) {
        // 将页面提交的密码进行md5加密
        // 根据用户提交的username查询数据库
        // 如果查询失败就返回失败的的结果
        // 比对密码，如果不一致就返回失败的结果
        // 查看员工的状态，是否可用
        // 登录成功，将id放入到session中
        return null;
    }
}
