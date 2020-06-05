package com.springapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springapi.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserEntity> {

    public UserEntity selectById(Integer id);

    @Select("select * from user where #{userEntity.name}")
    IPage<UserEntity> selectAll(Page<UserEntity> page, UserEntity userEntity);

}
