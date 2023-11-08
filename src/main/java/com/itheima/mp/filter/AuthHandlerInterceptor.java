package com.itheima.mp.filter;

import com.itheima.mp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthHandlerInterceptor {
    @Autowired
    TokenUtil tokenUtil;

    @Value("${token.youngToken}")
    private Long youngToken;
}
