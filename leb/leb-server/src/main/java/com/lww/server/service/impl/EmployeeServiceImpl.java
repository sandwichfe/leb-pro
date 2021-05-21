package com.lww.server.service.impl;

import com.lww.server.pojo.Employee;
import com.lww.server.mapper.EmployeeMapper;
import com.lww.server.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lww
 * @since 2021-01-27
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
