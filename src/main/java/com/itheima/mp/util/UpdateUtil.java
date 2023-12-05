package com.itheima.mp.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 更新工具类(忽略为null的字段)
 */
public class UpdateUtil {

    /**
     * 所有为空值的属性都不copy
     *
     * @param source
     * @param target
     */
    public static void copyNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullField(source));
    }

    /**
     * 获取属性中为空的字段
     * 666
     *
     * @param target
     * @return
     */
    private static String[] getNullField(Object target) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(target);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        Set<String> notNullFieldSet = new HashSet<>();
        for (PropertyDescriptor p : propertyDescriptors) {
            String name = p.getName();
            Object value = beanWrapper.getPropertyValue(name);
            if (judgeValue(value)) {
                notNullFieldSet.add(name);
            }
        }
        String[] notNullField = new String[notNullFieldSet.size()];
        return notNullFieldSet.toArray(notNullField);
    }


    public static boolean judgeValue(Object value) {
        if (Objects.isNull(value)) return true;
        if (value instanceof String) {
            String re = (String) value;
            return re.isBlank() || re.isEmpty();
        } else if (value instanceof Integer) {
            Integer re = (Integer) value;
            return re == 0;
        } else if (value instanceof Double) {
            Double re = (Double) value;
            return re == 0;
        } else {
            return false;
        }
    }
}