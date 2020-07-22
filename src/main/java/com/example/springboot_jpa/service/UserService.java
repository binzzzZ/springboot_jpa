package com.example.springboot_jpa.service;

import com.example.springboot_jpa.dao.UserDao;
import com.example.springboot_jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(String id) {
        return userDao.findById(id).get();
    }

}
