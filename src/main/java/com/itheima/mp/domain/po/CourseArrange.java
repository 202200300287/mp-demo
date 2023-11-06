package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.itheima.mp.enums.ArrangeDaily;
import com.itheima.mp.enums.ArrangeWeekly;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.EnumTypeHandler;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseArrange {
    @TableId(type = IdType.AUTO)
    @NotBlank
    private Integer courseArrangeId;

    private Integer courseId;

    @EnumValue
    @TableField(typeHandler = EnumTypeHandler.class)
    private ArrangeDaily arrangeDaily;

    @EnumValue
    @TableField(typeHandler = EnumTypeHandler.class)
    private ArrangeWeekly arrangeWeekly;

    private Integer studentClass;




}
