package com.springapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springapi.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserEntity> {

    public UserEntity selectById(Integer id);

}
