package com.itheima.mp.payload.response;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String username;
    private String perName;
    private String role;

    public JwtResponse(String accessToken, Integer id, String username, String perName, String role) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.perName = perName;
        this.role = role;
    }
}
