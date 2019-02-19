package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {

    private final static String COOKIE_DOMAIN = ".617076674.cn";

    private final static String COOKIE_NAME = "mmall_login_token";

    // X: domain=".617076674.cn"
    // a: A.617076674.cn                cookie:domain=A.617076674.cn;path="/"
    // b: B.617076674.cn                cookie:domain=B.617076674.cn;path="/"
    // c: A.617076674.cn/test/cc        cookie:domain=A.617076674.cn;path="/test/cc"
    // d: A.617076674.cn/test/dd        cookie:domain=A.617076674.cn;path="/test/dd"
    // e: A.617076674.cn/test           cookie:domain=A.617076674.cn;path="/test"
    public static void writeLoginToken(HttpServletResponse response, String token){
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");    //代表设置在根目录
        ck.setHttpOnly(true);   //防止脚本获取cookie信息，保证信息安全性
        ck.setMaxAge(60 * 60 * 24 * 365); //单位是秒。如果是-1，代表永久。如果maxAge不设置，cookie不会写入硬盘，而是写入内存，只在当前页面有效
        log.info("write cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
        response.addCookie(ck);
    }

    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if(null != cks){
            for (Cookie ck : cks){
                log.info("read cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)){
                    log.info("return cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cks = request.getCookies();
        if(null != cks){
            for (Cookie ck : cks){
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);    //设置成0，代表删除此cookie
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }

}
