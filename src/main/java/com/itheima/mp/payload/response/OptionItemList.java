package com.itheima.mp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * OptionItemList 发挥前端的OptionItemList集合对象
 * Integer code 返回代码 0 正确 1 错误
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OptionItemList {
    private Integer code = 0;
    private List<OptionItem> itemList;
}
