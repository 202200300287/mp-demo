package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Student;
import com.itheima.mp.domain.po.Teacher;
import com.itheima.mp.mapper.StudentMapper;
import com.itheima.mp.mapper.TeacherMapper;
import com.itheima.mp.service.iservice.IStudentService;
import com.itheima.mp.service.iservice.ITeacherService;

public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {
}
