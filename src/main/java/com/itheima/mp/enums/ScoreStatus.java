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
    Unmarked(1, "未进行考试"),
    Marked(2, "打出分数但学生不可见"),
    Visible(3, "学生可见分数");
    @JsonValue
    @EnumValue
    private final Integer code;

    private final String type;

    ScoreStatus(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    @JsonCreator
    public static ScoreStatus getByCode(@JsonProperty("code") int code) {
        return Arrays.stream(ScoreStatus.values()).filter(item -> item.getCode() == code).findFirst().get();
    }
}
