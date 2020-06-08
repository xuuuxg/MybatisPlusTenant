package com.springapi.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.springapi.entity.UserEntity;
import com.springapi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public ResponseEntity findUsersById(Integer id) {
        return ResponseEntity.ok().body(userMapper.selectById(id));
    }

    public ResponseEntity findAll(int pageSize, int pageNum,UserEntity userEntity) {
        List<UserEntity> list = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> userMapper.selectAll(userEntity));
//        List<UserEntity> list =  userMapper.selectAll(userEntity);
        return ResponseEntity.ok().body(list);
    }

//    public ResponseEntity editPassword(UserEntity userEntity) {
////        userMapper.editPassword()
//    }

}
