package com.itheima.mp.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huaisui
 */

@Component
public class TokenUtil {
    @Value("${token.privateKey}")
    String privateKey;

    // 通过用户Id和用户名创建Token
    public String getToken(Integer userId, String username, Integer role) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 3);
        System.out.println("privateKey===================" + privateKey);
        return JWT.create()
                .withClaim("userId", userId)
                .withClaim("username", username)
                .withClaim("role", role)
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(privateKey));
    }

    public void verify(String token) {
        JWTVerifier verify = JWT.require(Algorithm.HMAC256(privateKey)).build();
        verify.verify(token);
    }


    // 解析Token工具方法
    public Map<String, Object> parseToken(String token) throws JWTVerificationException {
        HashMap<String, Object> map = new HashMap<>();
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(privateKey)).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        Claim userId = decodedJWT.getClaim("userId");
        Claim username = decodedJWT.getClaim("username");
        Claim role = decodedJWT.getClaim("role");
        map.put("userId", userId.asInt());
        map.put("username", username.asString());
        map.put("role", role.asInt());
        return map;
    }

}