package com.shop.intercepetor;


import com.shop.common.pojo.JwtProperties;
import com.shop.common.utils.CookieUtils;
import com.shop.common.utils.EasyJwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    //线程私有
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private EasyJwtTokenUtil easyJwtTokenUtil;

    /**
     * 前置拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String cookieValue = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
        String username = easyJwtTokenUtil.getUserNameFromToken(cookieValue.substring(jwtProperties.getTokenStartHead().length()));
        if(username == null){
            return false;
        }
        THREAD_LOCAL.set(username);
        return true;
    }

    public static String getUsername(){
        return THREAD_LOCAL.get();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空线程的局部变量，必须清空，因为使用的是Tomcat线程池，线程不会结束，不会释放线程局部变量
        THREAD_LOCAL.remove();
    }
}
