package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.StudentAdvanced;
import com.itheima.mp.domain.vo.StudentAdvancedVO;
import com.itheima.mp.domain.vo.StudentSingleAdvancedVO;
import com.itheima.mp.enums.AdvancedType;
import com.itheima.mp.mapper.StudentAdvancedMapper;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.VOService;
import com.itheima.mp.service.iservice.IStudentAdvancedService;
import com.itheima.mp.util.CommomMethod;
import com.itheima.mp.util.FormatMethod;
import com.itheima.mp.util.UpdateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class StudentAdvancedService extends ServiceImpl<StudentAdvancedMapper, StudentAdvanced> implements IStudentAdvancedService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudentAdvancedMapper studentAdvancedMapper;
    @Autowired
    private VOService voService;

    public Integer getNewStudentAdvancedId() {
        return studentAdvancedMapper.findMaxStudentAdvancedId() + 1;
    }

    public StudentAdvanced getStudentAdvancedFromMap(Map map) {
        Integer studentId = CommomMethod.getInteger(map, "studentId");
        Integer advancedType = CommomMethod.getInteger(map, "advancedType");
        String content = CommomMethod.getString(map, "content");
        String title = CommomMethod.getString(map, "title");// 加的标题
        if (title.isBlank() || title == null) title = "没有标题呦";
        Double duration = FormatMethod.DurationFormat(CommomMethod.getDouble(map, "duration"));
        StudentAdvanced studentAdvanced = new StudentAdvanced();
        if (advancedType != null && advancedType >= 1 && advancedType <= 6)
            studentAdvanced.setAdvancedType(AdvancedType.getByCode(advancedType));
        studentAdvanced.setStudentId(studentId);
        studentAdvanced.setTitle(title);
        studentAdvanced.setContent(content);
        studentAdvanced.setDuration(duration);
        return studentAdvanced;
    }

    public StudentAdvanced getStudentAdvancedFromMap(Map map, Integer studentAdvancedId) {
        StudentAdvanced studentAdvanced = getStudentAdvancedFromMap(map);
        studentAdvanced.setStudentAdvancedId(studentAdvancedId);
        return studentAdvanced;
    }


    public DataResponse judgeStudentAdvancedData(StudentAdvanced studentAdvanced) {
        if (studentMapper.checkStudentId(studentAdvanced.getStudentId()) == 0)
            return CommomMethod.getReturnMessageError("不存在该学生");
        if (studentAdvanced.getStudentId() == null || studentAdvanced.getContent().isBlank() || studentAdvanced.getContent() == null) {
            return CommomMethod.getReturnMessageError("内容不可为空");
        }
        if (studentAdvanced.getAdvancedType() == AdvancedType.Volunteer && studentAdvanced.getDuration() == null)
            return CommomMethod.getReturnMessageError("志愿类型活动时长不可为空");
        if (studentAdvanced.getAdvancedType() == null) return CommomMethod.getReturnMessageError("类型不可为空");
        return CommomMethod.getReturnMessageOK();
    }

    public DataResponse insertStudentAdvanced(DataRequest dataRequest) {
        Map map = dataRequest.getData();
        StudentAdvanced studentAdvanced = getStudentAdvancedFromMap(map, getNewStudentAdvancedId());
        DataResponse dataResponse = judgeStudentAdvancedData(studentAdvanced);
        if (dataResponse.getCode() == 1) return dataResponse;
        studentAdvancedMapper.insert(studentAdvanced);
        return CommomMethod.getReturnMessageOK("成功添加了学生活动记录");
    }

    public DataResponse updateStudentAdvanced(DataRequest dataRequest) {
        Integer studentAdvancedId = dataRequest.getInteger("studentAdvancedId");
        if (studentAdvancedMapper.checkStudentAdvancedId(studentAdvancedId) == 0)
            return CommomMethod.getReturnMessageError("不存在该条活动记录");
        StudentAdvanced studentAdvanced = studentAdvancedMapper.selectById(studentAdvancedId);
        StudentAdvanced studentAdvancedSource = getStudentAdvancedFromMap(dataRequest.getData());
        UpdateUtil.copyNullProperties(studentAdvancedSource, studentAdvanced);
        DataResponse dataResponse = judgeStudentAdvancedData(studentAdvanced);
        if (dataResponse.getCode() == 1) return dataResponse;
        studentAdvancedMapper.updateById(studentAdvanced);
        return CommomMethod.getReturnMessageOK("成功修改了活动记录");
    }

    public DataResponse deleteStudentAdvanced(DataRequest dataRequest) {
        Integer studentAdvancedId = dataRequest.getInteger("studentAdvancedId");
        if (studentAdvancedMapper.checkStudentAdvancedId(studentAdvancedId) == 0)
            return CommomMethod.getReturnMessageError("不存在该条活动记录");
        studentAdvancedMapper.deleteById(studentAdvancedId);
        return CommomMethod.getReturnMessageOK("成功删除了一条活动记录");
    }

    // 获取所有学生所有活动的信息
    public DataResponse selectStudentAdvanceVOAll() {
        List<Student> studentList = studentMapper.getStudentListAll();
        List<StudentAdvancedVO> studentAdvancedVOList = new ArrayList<>();
        for (Student student : studentList) {
            studentAdvancedVOList.add(voService.getStudentAdvancedVO(student));
        }
        return CommomMethod.getReturnData(studentAdvancedVOList);
    }

    // 获取一个学生的一个活动信息
    public DataResponse selectStudentAdvancedVOListByStudentId(DataRequest dataRequest) {
        Integer studentId = dataRequest.getInteger("studentId");
        if (studentMapper.checkStudentId(studentId) == 0) return CommomMethod.getReturnMessageError("不存在该学生");
        return CommomMethod.getReturnData(voService.getStudentAdvancedVO(studentId));
    }

    // 获取一个学生一种活动的活动信息
    public DataResponse selectStudentSingleAdvancedVOSingleListByStudentIdAndAdvancedType(DataRequest dataRequest) {
        Integer studentId = dataRequest.getInteger("studentId");
        if (studentMapper.checkStudentId(studentId) == 0) return CommomMethod.getReturnMessageError("不存在该学生");
        Integer advancedType = dataRequest.getInteger("advancedType");
        if (advancedType < 1 || advancedType > 6) return CommomMethod.getReturnMessageError("活动类型有误");
        List<StudentAdvanced> studentAdvancedList = studentAdvancedMapper.getListByStudentIdAndAdvanceType(studentId, advancedType);
        List<StudentSingleAdvancedVO> studentSingleAdvancedVOList = new ArrayList<>();
        for (StudentAdvanced studentAdvanced : studentAdvancedList) {
            studentSingleAdvancedVOList.add(voService.getStudentSingleAdvancedVO(studentAdvanced));
        }
        return CommomMethod.getReturnData(studentSingleAdvancedVOList);
    }

    public DataResponse selectStudentSingleAdvancedVOListByAdvancedType(DataRequest dataRequest) {
        Integer advancedType = dataRequest.getInteger("advancedType");
        if (advancedType < 1 || advancedType > 6) return CommomMethod.getReturnMessageError("不存在该类型的活动");
        List<StudentAdvanced> studentAdvancedList = studentAdvancedMapper.getListByAdvancedTypeOrderByStudentId(advancedType);
        List<StudentSingleAdvancedVO> studentSingleAdvancedVOList = new ArrayList<>();
        for (StudentAdvanced studentAdvanced : studentAdvancedList) {
            studentSingleAdvancedVOList.add(voService.getStudentSingleAdvancedVO(studentAdvanced));
        }
        return CommomMethod.getReturnData(studentSingleAdvancedVOList);
    }


}
