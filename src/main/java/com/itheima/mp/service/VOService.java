package com.itheima.mp.service;


import com.itheima.mp.domain.po.*;
import com.itheima.mp.domain.vo.*;
import com.itheima.mp.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class VOService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudentBasicMapper studentBasicMapper;
    @Autowired
    private StudentAdvancedMapper studentAdvancedMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StudentCourseMapper studentCourseMapper;

    public StudentVO getStudentVO(Student student) {
        Integer studentId = student.getStudentId();
        User user = userMapper.selectById(student.getUserId());
        StudentBasic studentBasic = studentBasicMapper.selectById(studentId);
        if (studentBasic == null) studentBasic = new StudentBasic();
        StudentAdvanced studentAdvanced = studentAdvancedMapper.selectById(studentId);
        if (studentAdvanced == null) studentAdvanced = new StudentAdvanced();
        return new StudentVO(studentId, student.getName(), student.getMajor(), student.getGrade(), student.getGpa(), student.getStudentClass(), student.getRankClass(), student.getRankCollege(),
                user.getUsername(), user.getPhoto(), user.getUserType(), studentBasic.getGender(), studentBasic.getBirthday(), studentBasic.getEthnicity(), studentBasic.getBirthplace(), studentBasic.getAddress(), studentBasic.getPhone(), studentBasic.getEmail());
    }

    public StudentVO getStudentVO(Integer studentId) {
        Student student = studentMapper.selectById(studentId);
        User user = userMapper.selectById(student.getUserId());
        StudentBasic studentBasic = studentBasicMapper.selectById(studentId);
        if (studentBasic == null) studentBasic = new StudentBasic();
        // StudentAdvanced studentAdvanced=studentAdvancedMapper.selectById(studentId);
        // if(studentAdvanced==null)studentAdvanced=new StudentAdvanced();
        return new StudentVO(studentId, student.getName(), student.getMajor(), student.getGrade(), student.getGpa(), student.getStudentClass(), student.getRankClass(), student.getRankCollege(),
                user.getUsername(), user.getPhoto(), user.getUserType(), studentBasic.getGender(), studentBasic.getBirthday(), studentBasic.getEthnicity(), studentBasic.getBirthplace(), studentBasic.getAddress(), studentBasic.getPhone(), studentBasic.getEmail());
    }

    public TeacherVO getTeacherVO(Teacher teacher) {
        Integer userId = teacher.getUserId();
        User user = userMapper.selectById(userId);
        if (user == null) user = new User();
        return new TeacherVO(teacher.getTeacherId(), teacher.getName(), teacher.getPhone(), teacher.getEmail(), teacher.getGender(), teacher.getPosition(), teacher.getDegree(), teacher.getCollege(), teacher.getResearch(), teacher.getPaper(), teacher.getResume(),
                user.getUsername(), user.getPhoto(), user.getUserType());
    }

    public TeacherVO getTeacherVO(Integer teacherId) {
        TeacherVO teacherVO = getTeacherVO(teacherMapper.selectById(teacherId));
        if (teacherVO == null) return new TeacherVO();
        return teacherVO;
    }

    public CourseVO getCourseVO(Course course, Student student) {
        Integer courseId = course.getCourseId();
        Integer studentId = student.getStudentId();
        User user = userMapper.selectById(student.getUserId());
        StudentCourse studentCourse = studentCourseMapper.findByStudentIdAndCourseId(studentId, courseId);
        return new CourseVO(studentId, student.getName(), student.getMajor(), student.getGrade(), student.getGpa(), student.getStudentClass(), student.getRankClass(), student.getRankCollege(),
                user.getUsername(), user.getPhoto(), user.getUserType(),
                courseId, course.getNum(), course.getName(), course.getCredit(), course.getCourseType(),
                studentCourse.getScore(), studentCourse.getRankClass(), studentCourse.getRankCollege(), studentCourse.getScoreStatus());
    }

    public CourseVO getCourseVO(Integer courseId, Integer studentId) {
        return getCourseVO(courseMapper.selectById(courseId), studentMapper.selectById(studentId));
    }

    public StudentAdvancedVO getStudentAdvancedVO(Student student) {
        Integer studentId = student.getStudentId();
        if (studentMapper.checkStudentId(studentId) == 0) return new StudentAdvancedVO();
        User user = userMapper.selectById(student.getUserId());
        return new StudentAdvancedVO(studentId, student.getName(), student.getMajor(), student.getGrade(),
                user.getUsername(), user.getUserType(),
                studentAdvancedMapper.getListByStudentIdAndAdvanceType(studentId, 1),
                studentAdvancedMapper.getListByStudentIdAndAdvanceType(studentId, 2),
                studentAdvancedMapper.getListByStudentIdAndAdvanceType(studentId, 3),
                studentAdvancedMapper.getListByStudentIdAndAdvanceType(studentId, 4),
                studentAdvancedMapper.getListByStudentIdAndAdvanceType(studentId, 5),
                studentAdvancedMapper.getListByStudentIdAndAdvanceType(studentId, 6));
    }

    public StudentAdvancedVO getStudentAdvancedVO(Integer studentId) {
        return getStudentAdvancedVO(studentMapper.selectById(studentId));
    }

    public StudentSingleAdvancedVO getStudentSingleAdvancedVO(StudentAdvanced studentAdvanced) {
        Integer studentId = studentAdvanced.getStudentId();
        if (studentMapper.checkStudentId(studentId) == 0) return new StudentSingleAdvancedVO();
        Student student = studentMapper.selectById(studentId);
        User user = userMapper.selectById(student.getUserId());
        return new StudentSingleAdvancedVO(studentId, student.getName(), student.getMajor(), student.getGrade(),
                user.getUsername(), user.getUserType(),
                studentAdvanced.getStudentAdvancedId(), studentAdvanced.getAdvancedType(), studentAdvanced.getContent(), studentAdvanced.getDuration(), studentAdvanced.getUpdateTime());

    }

    public StudentSingleAdvancedVO getStudentSingleAdvancedVO(Integer studentAdvancedId) {
        return getStudentSingleAdvancedVO(studentAdvancedMapper.selectById(studentAdvancedId));
    }
}
