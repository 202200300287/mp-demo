package com.itheima.mp.util;

import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.StudentAdvanced;
import com.itheima.mp.domain.po.StudentBasic;
import com.itheima.mp.domain.vo.CourseVO;
import com.itheima.mp.mapper.StudentBasicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VOMethod {


    @Autowired
    private StudentBasicMapper studentBasicMapper;



}
