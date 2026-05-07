package com.wiki.dao;

import com.wiki.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
    User login(@Param("username") String username, @Param("password") String password);
    List<User> getAllUsers();
    int deleteUser(Integer id);
}