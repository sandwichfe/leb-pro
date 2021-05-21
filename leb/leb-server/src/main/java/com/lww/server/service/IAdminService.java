package com.lww.server.service;

import com.lww.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lww.server.pojo.RespBean.R;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lww
 * @since 2021-01-27
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    R  login(String username, String password, String code, HttpServletRequest request);


    /**
     * 根据用户名获取用户对象
     * @param userName
     * @return
     */
    Admin getAdminByUserName(String userName);
}
