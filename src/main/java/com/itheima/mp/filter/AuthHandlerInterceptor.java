package com.itheima.mp.filter;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.mp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    TokenUtil tokenUtil;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse response,Object handler) throws IOException {
        log.info("============进入拦截器=============");
        // System.out.println(httpServletRequest.getHeader("User-Agent"));
        if("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
            System.out.println("Method:OPTIONS 直接放行");
            return true;
        }
        if(!(handler instanceof HandlerMethod)) {
            response.setStatus(401);
            return false;
        }
        String token = httpServletRequest.getHeader("Authorization");
        log.info("=============token:" + token);
        Map<String,String> map = new HashMap<>();
        try{
            tokenUtil.verify(token);
            return true;
        }catch (SignatureVerificationException e) {
            e.printStackTrace();
            response.setStatus(401);
            map.put("message","无效签名");
        }catch (TokenExpiredException e) {
            e.printStackTrace();
            response.setStatus(401);
            map.put("message","token过期");
        }catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            response.setStatus(401);
            map.put("message","加密算法不匹配");
        }catch (Exception e) {
            e.printStackTrace();
            response.setStatus(401);
            map.put("message","token无效");
        }
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }


}
