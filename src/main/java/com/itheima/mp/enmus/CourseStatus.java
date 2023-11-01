package com.itheima.mp.enmus;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CourseStatus {
    Unavailable(1,"无效课程"),
    Available(2,"有效课程"),
    Unselected(3,"不可选课程"),
    Selected(4,"可选课程");
    @EnumValue
    private Integer code;
    @JsonValue
    private String status;
    CourseStatus(Integer code,String status){
        this.code=code;
        this.status=status;
    }

}
