package com.itheima.mp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MessageResponse  消息返回对象 系统框架返回的消息对象
 * String message 返回的消息
 */


@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageResponse {
    private String message;
}