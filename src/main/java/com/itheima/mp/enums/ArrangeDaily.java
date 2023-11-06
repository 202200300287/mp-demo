package com.itheima.mp.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ArrangeDaily {
    Morning1(1,"8:00~9:50"),
    Morning2(2,"10:10~12:00"),
    Afternoon1(3,"14:00~15:50"),
    Afternoon2(4,"16:10~18:00"),
    Night(5,"19:00~20:50");
    @EnumValue
    private Integer code;
    @JsonValue
    private String type;

    ArrangeDaily(Integer code, String type){
        this.code=code;
        this.type=type;
    }

    @JsonCreator
    public static ArrangeDaily getByCode(@JsonProperty("code") int code) {
        return Arrays.stream(ArrangeDaily.values()).filter(item -> item.getCode() == code).findFirst().get();
    }
}
