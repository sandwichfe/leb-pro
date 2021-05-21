package com.lww.server.config.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWtToken工具类
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";            //用户名  key     用于下面的claims负载中map中做key
    private static final String CLAIM_KEY_CREATED = "createdDate";        //创建时间 key
    @Value("${jwt.secret}")
    private String secret;    //密钥
    @Value("${jwt.expiration}")
    private Long expiration;   //token 有效时长


    //根据用户信息生成token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();     //token负载
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());            //new Date  当前时间
        return generateToken(claims);
    }

    /**
     * 根据token获取用户名
     *
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }

        return username;
    }


    /**
     * 验证token是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token,UserDetails userDetails){
        String username=getUserNameFromToken(token);
        return username.equals(userDetails.getUsername())&&!isTokenExpired(token);
    }

    /**
     * token是否需要更新
     * @param token
     * @return
     */
    public  boolean canRefresh(String token){
        return !isTokenExpired(token);       //token失效了则就是需要刷新了
    }


    //刷新token
    public String refreshToken(String token){
        Claims claims=getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());   //更新一下时间 然后重新生成token
        return generateToken(claims);
    }

    /**
     * tokens是否过期
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
    Claims claims=getClaimsFromToken(token);
    Date expireDate =claims.getExpiration();
    return expireDate.before(new Date());      //预定的过期时间是否在当前时间前面
    }

    /**
     * 从token中获取荷载
     *
     * @param token
     * @return
     */

    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)   //密钥
                    .parseClaimsJws(token)   //解析token
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }


    /**
     * 根据map  荷载 claims 生成token
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)        //加入荷载
                .setExpiration(generateExpirationDate())          //设置过期/到期时间
                .signWith(SignatureAlgorithm.HS512, secret)        //设置签名 以及生成密钥
                .compact();
    }

    /**
     * 根据当前时间 生成最后的  token失效时间  到期时间
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);    //获取当前时间加上设置的有效时长 得到过期时间

    }

}
