package org.example.interceptor;

import io.jsonwebtoken.Claims;
import org.example.context.BaseContext;
import org.example.properties.JwtProperties;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader("token");

        //2、校验令牌
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            // String -> Long
            Long id = Long.valueOf(claims.get("id").toString());
            BaseContext.setCurrentUserId(id);
            // 可以输出看看是否拿到了当前用户id
            System.out.println("当前用户id="+BaseContext.getCurrentUserId());
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            //4、不通过，响应 401 状态码
            response.setStatus(401);
            // 打印异常信息，不建议打异常，前端处理
            // ex.printStackTrace();
            return false;
        }
    }
}
