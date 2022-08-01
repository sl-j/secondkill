package com.lei.secondkill.controller;


import com.lei.secondkill.entity.TUser;
import com.lei.secondkill.vo.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testController {

    @GetMapping("/hello")
    public ResponseResult hello(TUser user){
        return ResponseResult.okResult(user);
    }


}
