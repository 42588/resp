package cn.hbmz.ruiji.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hbmz.ruiji.common.R;
import cn.hbmz.ruiji.entity.Employee;
import cn.hbmz.ruiji.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/employee")
@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /**
     * 员工登录
     * 前端请求是post方式
     * 
     * @param employee 获取信息，采用的是json格式
     * @param request  获取session
     * @return
     */
    @PostMapping("/login")
    public R<Employee> Login(@RequestBody Employee employee, HttpServletRequest request) {
        // 将页面提交的密码进行md5加密
        String pwd = employee.getPassword();
        pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        // 根据用户提交的username查询数据库
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        // 如果查询失败就返回失败的的结果
        if (emp == null)
            return R.error("登录失败");
        // 比对密码，如果不一致就返回失败的结果
        if (!emp.getPassword().equals(pwd))
            return R.error("密码不对");
        // 查看员工的状态，是否可用
        if (emp.getStatus() == 0)
            return R.error("账号已禁用");
        // 登录成功，将id放入到session中
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> Logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 连接数据库保存浏览器传来的数据
     * 当用户录入多个相同的id的时候会抛出异常
     * 
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        // 打印需要保存的信息，在日志中
        log.info("当前新添加的用户{}", employee);
        // 设置初始密码,记得进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // 加密之后记录时间
        Long attribute = (Long) request.getSession().getAttribute("employee");
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(attribute);
        employee.setUpdateUser(attribute);
        // 通知数据库进行保存
        employeeService.save(employee);
        return R.success("新增用户成功");
    }

    /**
     * 分页查询，数据库查询一部分，前端页面选择刷新一部分页面
     * 
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name) {
        log.info("page:{},  pageSize:{},  name:{}", page, pageSize, name);
        // 构造条件构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        // 增加查询条件，过滤掉空字符跟空对象
        if (name != null)
            if (!name.equals("")) {
                queryWrapper.like("name", name);
                // 降序进行排列
                queryWrapper.orderByDesc("update_time");
            }
        // 分页，参数是查询字符串以及分页信息
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id修改员工ID
     * 
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee, HttpServletRequest req) {
        log.info(employee.toString());
        Long empID = (Long) req.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empID);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        } else
            return R.error("没有这个员工信息");
    }
}