package com.lei.secondkill.controller;

import com.lei.secondkill.entity.TUser;
import com.lei.secondkill.mapper.TUserMapper;
import com.lei.secondkill.service.TUserService;
import com.lei.secondkill.utils.MD5Utils;
import com.lei.secondkill.utils.validtorUtil;
import com.lei.secondkill.vo.AppHttpCodeEnum;
import com.lei.secondkill.vo.LoginVo;
import com.lei.secondkill.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private TUserService tUserService;


    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public ResponseResult doLogin(LoginVo loginVo){

        return tUserService.doLogin(loginVo);
    }
}
