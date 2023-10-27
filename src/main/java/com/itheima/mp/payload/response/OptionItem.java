package com.itheima.mp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OptionItem 选项数据类
 * Integer id  数据项id
 * String value 数据项值
 * String label 数据值标题
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OptionItem {
    private Integer id;
    private String value;
    private String title;
}
