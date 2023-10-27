package com.itheima.mp.util;

import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.vo.StudentVO;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.impl.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;

public class CommomMethod {


    private static StudentCourseService studentCourseService;

    public static DataResponse getReturnData(Object obj, String msg){
        return new   DataResponse(0,obj,msg);
    }
    public static DataResponse getReturnMessage(Integer code, String msg){
        return new   DataResponse(code,null,msg);
    }

    public static  DataResponse getReturnData(Object obj){
        return getReturnData(obj,null);
    }
    public static DataResponse getReturnMessageOK(String msg){
        return getReturnMessage(0, msg);
    }
    public static DataResponse getReturnMessageOK(){
        return getReturnMessage(0, null);
    }
    public static DataResponse getReturnMessageError(String msg){
        return getReturnMessage(1, msg);
    }



}
