package com.springapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springapi.entity.UserEntity;
import com.springapi.service.UserService;
import com.tools.Send;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Api(value = "用户Controller", tags = {"用户操作接口"})
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Send send;

    @RequestMapping(value = "/user/{id}", method = {RequestMethod.GET})
    @ApiOperation(value = "按照Id查询User", notes = "获取User")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "GET UserId required", dataType = "Integer", paramType = "path")

    })
    @ApiResponses(@ApiResponse(code = 400,message = "Invalid"))
    public ResponseEntity getUser(@PathVariable("id")Integer id) throws IOException, TimeoutException {
        send.send("xixi");
        return ResponseEntity.ok().body(userService.findUsersById(id));
    }

    @GetMapping("/userList/")
    @ApiOperation(value = "按照条件查询User", notes = "获取User")
    public ResponseEntity getUserList(@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                      @RequestParam("pageNum") int pageNum,
                                      UserEntity userEntity) {
        return ResponseEntity.ok().body(userService.findAll(pageNum, pageSize, userEntity));
    }

    @PostMapping("/register")
    public ResponseEntity register() {

        return ResponseEntity.ok().body(null);
    }


}
