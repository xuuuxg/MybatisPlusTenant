package com.springapi.service;

import com.springapi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public ResponseEntity findUsersById(Integer id) {
        return ResponseEntity.ok().body(userMapper.selectById(id));
    }

}
