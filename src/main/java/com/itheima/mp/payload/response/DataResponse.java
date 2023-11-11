package com.itheima.mp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DataResponse 前端HTTP请求返回数据对象
 * Integer code 返回代码 0 正确  返回 1 错误返回信息
 * Object data 返回数据对象
 * String msg 返回正确错误信息
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataResponse {
    private Integer code;
    private Object data;
    private String msg;

}
