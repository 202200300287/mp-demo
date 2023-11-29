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
public enum CourseStatus {
    Unavailable(1,"无效课程"),
    AvailableUnselectable(2,"有效但不可选课程"),
    Selectable(3,"可选课程");
    @JsonValue
    @EnumValue
    private Integer code;

    private String status;
    CourseStatus(Integer code,String status){
        this.code=code;
        this.status=status;
    }
    @JsonCreator
    public static CourseStatus getByCode(@JsonProperty("code") int code) {
        return Arrays.stream(CourseStatus.values()).filter(item -> item.getCode() == code).findFirst().get();
    }

}
