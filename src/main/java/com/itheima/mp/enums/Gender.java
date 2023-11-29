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
public enum Gender {
    Male(1,"男"),
    Female(2,"女");
    @JsonValue
    @EnumValue
    private Integer code;

    private String gender;

    Gender(Integer code, String gender){
        this.code=code;
        this.gender=gender;
    }

    @JsonCreator
    public static Gender getByCode(@JsonProperty("code") int code) {
        return Arrays.stream(Gender.values()).filter(item -> item.getCode() == code).findFirst().get();
    }

}
