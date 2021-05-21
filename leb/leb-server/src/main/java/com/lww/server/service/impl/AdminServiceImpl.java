package com.lww.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lww.server.config.security.JwtTokenUtil;
import com.lww.server.pojo.Admin;
import com.lww.server.mapper.AdminMapper;
import com.lww.server.pojo.RespBean.R;
import com.lww.server.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lww
 * @since 2021-01-27
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserDetailsService userDetailsService;      //springSecurity携带的对象

    @Autowired
    private PasswordEncoder passwordEncoder;        //springSecurity提供的加密工具

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 登录之后返回token
     *
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public R login(String username, String password, String code, HttpServletRequest request) {
        //从session中拿到验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        //如果验证码为空或者 验证码对比错误 （忽略大小写）
        if (StringUtils.isEmpty(code)||!captcha.equalsIgnoreCase(code)){
            return R.error("验证码错误！");
        }


        //登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);          //加载用户名
        //如果用户不存在 或者密码错误
        if (userDetails == null || passwordEncoder.matches(password, userDetails.getPassword())) {
            return R.error("用户名或密码不正确");
        }
        if (!userDetails.isEnabled()) {
            return R.error("账号已被禁用，请联系管理员！");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,           //userDetails
                null,      //登录凭证 一般是指密码 但这里不要放进去
                userDetails.getAuthorities());    //authorities
        //springSecurity全局对象 放进去此authToken
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //执行到此就说明登录没有问题  就生成一个token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();    //存放token以及定义的tokenHead
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return R.success("登录成功", tokenMap);    //登录成功 并且返回token
    }

    /**
     * 根据用户名获取用户对象
     *
     * @param userName
     * @return
     */
    @Override
    public Admin getAdminByUserName(String userName) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username",userName)   //where username= userName
        .eq("enabled",true);        // and enabled =true
        Admin admin=adminMapper.selectOne(wrapper);
        return admin;
    }
}
