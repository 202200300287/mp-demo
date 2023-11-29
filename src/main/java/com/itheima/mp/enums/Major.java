package com.itheima.mp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Major {
    SoftwareEngineer(1,"软件工程"),
    DigitalMedia(2,"数字媒体技术"),
    DataScience(3,"数据科学与大数据技术");
    @JsonValue
    @EnumValue
    private Integer code;

    private String type;

    Major(Integer code, String type){
        this.code=code;
        this.type=type;
    }

    /**
     * 反序列化
     * @param code 数据库对应的值
     * @return 枚举对象
     */
    @JsonCreator
    public static Major getByCode(@JsonProperty("code") int code) {
        return Arrays.stream(Major.values()).filter(item -> item.getCode() == code).findFirst().get();
    }

    }
