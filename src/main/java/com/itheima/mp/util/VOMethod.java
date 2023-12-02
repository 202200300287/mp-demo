package com.itheima.mp.util;

import com.itheima.mp.mapper.StudentBasicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VOMethod {


    @Autowired
    private StudentBasicMapper studentBasicMapper;


}
