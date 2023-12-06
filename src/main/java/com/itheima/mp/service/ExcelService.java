package com.itheima.mp.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.mp.domain.po.Course;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.Teacher;
import com.itheima.mp.domain.vo.StudentVO;
import com.itheima.mp.domain.vo.TeacherVO;
import com.itheima.mp.mapper.CourseMapper;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.mapper.TeacherMapper;
import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.util.CommomMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class ExcelService {
    private static final String[] studentVOTitles = {"学生主键", "姓名", "专业", "年级", "绩点", "班级", "班级排名", "学院排名"
            , "学号", "性别", "生日", "民族", "籍贯", "住址", "电话", "邮箱"};
    private static final String[] teacherVOTitles = {"教师主键", "姓名", "电话", "邮箱", "性别", "职务", "学位", "所在学院", "研究方向", "工号"};

    private static final String[] courseTitles={"课程主键","课序号","课程名","学分","课程状态","课程年级","课程类型"};
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private VOService voService;
    @Autowired
    private TeacherMapper teacherMapper;

    public List<Student> getStudentListAll() {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<Student>()
                .select("*");
        return studentMapper.selectList(studentQueryWrapper);
    }

    public List<StudentVO> selectStudentVOList() {
        List<Student> studentList = getStudentListAll();
        if (studentList.isEmpty()) return null;
        List<StudentVO> studentVOList = new ArrayList<>();
        for (Student student : studentList) {
            Integer studentId = student.getStudentId();
            studentVOList.add(voService.getStudentVO(studentId));
        }
        return studentVOList;
    }

    public List<TeacherVO> selectTeacherVOList(List<Integer> teacherIdList) {
        if (teacherIdList.isEmpty()) return new ArrayList<>();
        String teacherListStr = StringUtils.join(teacherIdList, ",");
        List<Teacher> teacherList = teacherMapper.selectByTeacherIdList(teacherListStr);
        // List<Teacher> teacherList=teacherMapper.selectTeacherList();
        if (teacherList.isEmpty()) return new ArrayList<>();
        List<TeacherVO> teacherVOList = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            teacherVOList.add(voService.getTeacherVO(teacher));
        }
        return teacherVOList;
    }


    public ResponseEntity<StreamingResponseBody> getStudentVOListExcl(DataRequest dataRequest) {
        List studentIdList=dataRequest.getList("studentIdList");
        if(studentIdList.isEmpty())return null;
        String studentIdListStr=StringUtils.join(studentIdList,",");
        List<StudentVO> list=studentMapper.selectStudentVOListByStudentIdList(studentIdListStr);
        int length = studentVOTitles.length;
        int i, j;
        String outPutSheetName = "student.xlsx";
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCellStyle styleTitle = CommomMethod.createCellStyle(wb, 20);
        XSSFSheet sheet = wb.createSheet(outPutSheetName);
        sheet.setDefaultColumnWidth(30 * 256);
        XSSFCellStyle style = CommomMethod.createCellStyle(wb, 11);
        XSSFRow row;
        XSSFCell[] cell = new XSSFCell[length];
        row = sheet.createRow(0);
        for (j = 0; j < length; j++) {
            cell[j] = row.createCell(j);
            cell[j].setCellStyle(style);
            cell[j].setCellValue(studentVOTitles[j]);
            cell[j].getCellStyle();
        }
        // Map m;
        if (list != null && !list.isEmpty()) {
            for (i = 0; i < list.size(); i++) {
                row = sheet.createRow(i + 1);
                for (j = 0; j < length; j++) {
                    cell[j] = row.createCell(j);
                    cell[j].setCellStyle(style);
                }
                StudentVO studentVO = list.get(i);
                Class<?> studentVOObj = studentVO.getClass();
                Field[] fields = studentVOObj.getDeclaredFields();
                int count = 0;
                for (Field field : fields) {
                    try {
                        // 设置访问权限，确保可以访问私有属性
                        field.setAccessible(true);
                        // 输出属性名及属性值
                        String fieldName = field.getName();
                        if (fieldName.equals("userType") || fieldName.equals("photo")) {
                            continue;
                        }
                        if (field.get(studentVO) != null) {
                            cell[count].setCellValue(field.get(studentVO).toString());
                        } else {
                            cell[count].setCellValue("");
                        }
                        // System.out.println("属性名：" + field.getName() + "，属性值：" + field.get(studentVO));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        try {
            StreamingResponseBody stream = wb::write;
            return ResponseEntity.ok()
                    .contentType(CommomMethod.exelType)
                    .body(stream);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    public ResponseEntity<StreamingResponseBody> getTeacherVOListExcl(DataRequest dataRequest) {
        List teacherIIdList=dataRequest.getList("teacherIdList");
        if(teacherIIdList.isEmpty())return null;
        String teacherIdListStr=StringUtils.join(teacherIIdList,",");
        List<TeacherVO> list = teacherMapper.selectTeacherVOListByTeacherIdList(teacherIdListStr);
        int length = teacherVOTitles.length;
        int i, j;
        String outPutSheetName = "teacher.xlsx";
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCellStyle styleTitle = CommomMethod.createCellStyle(wb, 20);
        XSSFSheet sheet = wb.createSheet(outPutSheetName);
        sheet.setDefaultColumnWidth(30 * 256);
        XSSFCellStyle style = CommomMethod.createCellStyle(wb, 11);
        XSSFRow row;
        XSSFCell[] cell = new XSSFCell[length];
        row = sheet.createRow(0);
        for (j = 0; j < length; j++) {
            cell[j] = row.createCell(j);
            cell[j].setCellStyle(style);
            cell[j].setCellValue(teacherVOTitles[j]);
            cell[j].getCellStyle();
        }
        // Map m;
        if (list != null && !list.isEmpty()) {
            for (i = 0; i < list.size(); i++) {
                row = sheet.createRow(i + 1);
                for (j = 0; j < length; j++) {
                    cell[j] = row.createCell(j);
                    cell[j].setCellStyle(style);
                }
                TeacherVO teacherVO = list.get(i);
                Class<?> teacherVOObj = teacherVO.getClass();
                Field[] fields = teacherVOObj.getDeclaredFields();
                int count = 0;
                for (Field field : fields) {
                    try {
                        // 设置访问权限，确保可以访问私有属性
                        field.setAccessible(true);
                        // 输出属性名及属性值
                        String fieldName = field.getName();
                        if (fieldName.equals("userType") || fieldName.equals("photo") || fieldName.equals("paper") || fieldName.equals("resume")) {
                            continue;
                        }
                        if (field.get(teacherVO) != null) {
                            cell[count].setCellValue(field.get(teacherVO).toString());
                        } else {
                            cell[count].setCellValue("");
                        }
                        // System.out.println("属性名：" + field.getName() + "，属性值：" + field.get(studentVO));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        try {
            StreamingResponseBody stream = wb::write;
            return ResponseEntity.ok()
                    .contentType(CommomMethod.exelType)
                    .body(stream);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }


    @Autowired
    private CourseMapper courseMapper;
    public ResponseEntity<StreamingResponseBody> getCourseListExcl(DataRequest dataRequest) {
        List courseIIdList=dataRequest.getList("courseIdList");
        if(courseIIdList.isEmpty())return null;
        String courseIdListStr=StringUtils.join(courseIIdList,",");
        List<Course> list = courseMapper.selectCourseListByCourseIdList(courseIdListStr);
        int length = courseTitles.length;
        int i, j;
        String outPutSheetName = "course.xlsx";
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCellStyle styleTitle = CommomMethod.createCellStyle(wb, 20);
        XSSFSheet sheet = wb.createSheet(outPutSheetName);
        sheet.setDefaultColumnWidth(30 * 256);
        XSSFCellStyle style = CommomMethod.createCellStyle(wb, 11);
        XSSFRow row;
        XSSFCell[] cell = new XSSFCell[length];
        row = sheet.createRow(0);
        for (j = 0; j < length; j++) {
            cell[j] = row.createCell(j);
            cell[j].setCellStyle(style);
            cell[j].setCellValue(courseTitles[j]);
            cell[j].getCellStyle();
        }
        // Map m;
        if (list != null && !list.isEmpty()) {
            for (i = 0; i < list.size(); i++) {
                row = sheet.createRow(i + 1);
                for (j = 0; j < length; j++) {
                    cell[j] = row.createCell(j);
                    cell[j].setCellStyle(style);
                }
                Course course = list.get(i);
                Class<?> courseObj = course.getClass();
                Field[] fields = courseObj.getDeclaredFields();
                int count = 0;
                for (Field field : fields) {
                    try {
                        // 设置访问权限，确保可以访问私有属性
                        field.setAccessible(true);
                        // 输出属性名及属性值
                        String fieldName = field.getName();
//                        if (fieldName.equals("userType") || fieldName.equals("photo") || fieldName.equals("paper") || fieldName.equals("resume")) {
//                            continue;
//                        }
                        if (field.get(course) != null) {
                            cell[count].setCellValue(field.get(course).toString());
                        } else {
                            cell[count].setCellValue("");
                        }
                        // System.out.println("属性名：" + field.getName() + "，属性值：" + field.get(studentVO));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        try {
            StreamingResponseBody stream = wb::write;
            return ResponseEntity.ok()
                    .contentType(CommomMethod.exelType)
                    .body(stream);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }
}
