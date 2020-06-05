package com.springapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springapi.entity.UserEntity;
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

    public ResponseEntity findAll(Page<UserEntity> page,UserEntity userEntity) {
        return ResponseEntity.ok().body(userMapper.selectAll(page,userEntity));
    }

}
