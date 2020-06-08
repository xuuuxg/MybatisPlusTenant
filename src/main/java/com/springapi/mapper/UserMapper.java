package com.springapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springapi.entity.UserEntity;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserEntity> {

    public UserEntity selectById(Integer id);

    @Select("select * from user where name = #{user.name}")
    List<UserEntity> selectAll(@Param("user") UserEntity userEntity);

    @Update("update user set password = #{password} where id = #{id}")
    UserEntity editPassword(UserEntity userEntity);

}
