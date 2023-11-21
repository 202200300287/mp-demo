package com.itheima.mp.domain.vo;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.itheima.mp.enums.AdvancedType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedTypeVO {
    @EnumValue
    private AdvancedType advancedType;
    List<StudentSingleAdvancedVO> studentSingleAdvancedList;
}
