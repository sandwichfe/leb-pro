package com.lww.server.pojo.RespBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R {

    private long code;
    private String message;
    private Object obj;


    /**
     * 成功返回结果
     * @param message
     * @return
     */
    public static  R success(String message){
        return new R(200,message,null);
    }

    public static  R success(String message,Object obj){
        return new R(200,message,obj);
    }

    /**
     * 失败返回结果
     * @param message
     * @return
     */
    public static  R error(String message){
        return new R(500,message,null);
    }

    public static  R error(String message,Object obj){
        return new R(200,message,obj);
    }

}
