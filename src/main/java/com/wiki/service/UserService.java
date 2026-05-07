package com.wiki.service;

import com.wiki.dao.UserMapper;
import com.wiki.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User login(String username, String password) { return userMapper.login(username, password); }
    public List<User> getAllUsers() { return userMapper.getAllUsers(); }
    public boolean deleteUser(Integer id) { return userMapper.deleteUser(id) > 0; }
}