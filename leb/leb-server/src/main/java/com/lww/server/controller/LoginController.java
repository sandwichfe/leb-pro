package com.lww.server.controller;

import com.lww.server.pojo.Admin;
import com.lww.server.pojo.Login.AdminLoginParam;
import com.lww.server.pojo.RespBean.R;
import com.lww.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登录
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")                             //添加@RequestBody  那么接受格式就是json提交的 不是普通的form表单
    public R login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
    return adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);
    }


    @ApiOperation("获取当前登录用户的信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){       //  Principal  又是springSecurity的对象
        if(principal==null){
            return null;
        }
        String userName=principal.getName();
        //根据用户名获取用户对象
        Admin admin=adminService.getAdminByUserName(userName);
        admin.setPassword(null);    //用户信息中的密码不需要在此处返回
        return admin;
    }



    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public R logout(){
        return R.success("退出成功");        //退出功能已在前端完成 前端发送的请求头没有携带token就说明没有登录
    }

}
