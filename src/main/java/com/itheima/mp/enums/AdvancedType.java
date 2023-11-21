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
public enum AdvancedType {
    Honors(1,"个人荣誉"),
    Competitions(2,"竞赛经历"),
    Disciplinary(3,"处分"),
    Clubs(4,"社团"),
    Volunteer(5,"志愿活动"),
    Internship(6,"实习经历");
    @EnumValue
    private Integer code;
    @JsonValue
    private String type;
    AdvancedType(Integer code, String type){
        this.code=code;
        this.type=type;
    }

    @JsonCreator
    public static AdvancedType getByCode(@JsonProperty("code") int code) {
        return Arrays.stream(AdvancedType.values()).filter(item -> item.code == code).findFirst().get();
    }
}
