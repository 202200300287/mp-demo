package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT MAX(user_id) FROM user")
    Integer findMaxUserId();

    @Select("SELECT username FROM user")
    List<String> findAllUsername();

    @Select("SELECT user_id FROM user WHERE username LIKE CONCAT('%', #{username}, '%')")
    List<Integer> getUserIdListLikeUsername(String username);
}