package cn.hbmz.ruiji.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hbmz.ruiji.entity.Employee;
import cn.hbmz.ruiji.mapper.EmployeeMapper;
import cn.hbmz.ruiji.service.EmployeeService;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
