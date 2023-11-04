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
public enum ScoreStatus {
    ExamUnfinished(1,"未进行考试"),
    ExamFinished(2,"已经考试未打分"),
    ScoreMarked(3,"打出分数但学生不可见"),
    ScoreVisible(4,"学生可见分数");

    @EnumValue
    private Integer code;
    @JsonValue
    private String type;

    ScoreStatus(Integer code,String type){
        this.code=code;
        this.type=type;
    }

    @JsonCreator
    public static ScoreStatus getByCode(@JsonProperty("code") int code) {
        return Arrays.stream(ScoreStatus.values()).filter(item -> item.getCode() == code).findFirst().get();
    }
}