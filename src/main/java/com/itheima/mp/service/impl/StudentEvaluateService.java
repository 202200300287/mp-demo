package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.StudentEvaluate;
import com.itheima.mp.mapper.StudentEvaluateMapper;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.iservice.IStudentEvaluateService;
import com.itheima.mp.util.CommomMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class StudentEvaluateService  extends ServiceImpl<StudentEvaluateMapper, StudentEvaluate> implements IStudentEvaluateService {
    @Autowired
    StudentEvaluateMapper studentEvaluateMapper;

    @Autowired
    StudentMapper studentMapper;

    public DataResponse createEvaluate (Integer userId,Integer studentId,String text,boolean like) {
        if (studentId == null || text == null) return CommomMethod.getReturnMessageError("参数错误");
        Student evaluateStudent = studentMapper.selectStudentByUserId(userId);
        if (evaluateStudent == null) return CommomMethod.getReturnMessageError("仅学生能进行学生互评");
        StudentEvaluate studentEvaluate = new StudentEvaluate();
        studentEvaluate.setEvaluateStudent(evaluateStudent.getStudentId());
        studentEvaluate.setEvaluatedStudent(studentId);
        studentEvaluate.setText(text);
        studentEvaluate.setCreateTime(LocalDateTime.now());
        if (like) {
            studentEvaluate.setLike(1);
            Student evaluatedStudent = studentMapper.selectById(studentId);
            if (evaluatedStudent == null) return CommomMethod.getReturnMessageError("被评价学生不存在");
            evaluatedStudent.setLikes(evaluatedStudent.getLikes() + 1);
        }else{
            studentEvaluate.setLike(0);
        }
        studentEvaluateMapper.insert(studentEvaluate);
        return CommomMethod.getReturnMessageOK("评论成功");
    }

    public DataResponse deleteEvaluateById (Integer evaluateId) {
        try {
            studentEvaluateMapper.deleteById(evaluateId);
            return CommomMethod.getReturnMessageOK("删除成功");
        }catch (Exception e) {
            return CommomMethod.getReturnMessageError("ID不存在");
        }
    }

    public DataResponse selectEvaluateByEvaluatedStudent (Integer userId) {
        Student evaluatedStudent = studentMapper.selectStudentByUserId(userId);
        return CommomMethod.getReturnData(studentEvaluateMapper.selectEvaluateByEvaluatedStudent(evaluatedStudent.getStudentId()));
    }

    public DataResponse selectEvaluateByEvaluateStudent (Integer userId) {
        Student evaluatedStudent = studentMapper.selectStudentByUserId(userId);
        return CommomMethod.getReturnData(studentEvaluateMapper.selectEvaluateByEvaluateStudent(evaluatedStudent.getStudentId()));
    }
}
