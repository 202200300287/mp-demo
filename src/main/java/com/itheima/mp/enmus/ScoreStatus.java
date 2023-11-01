package com.itheima.mp.enmus;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ScoreStatus {
    ExamUnfinished(1,"未进行考试"),
    ExamFinished(2,"已经考试"),
    ScoreUnmarked(3,"已考试未打分"),
    ScoreMarked(4,"打出分数但学生不可见"),
    ScoreVisible(5,"学生可见分数");

    @EnumValue
    private Integer code;
    @JsonValue
    private String type;

    ScoreStatus(Integer code,String type){
        this.code=code;
        this.type=type;
    }
}
