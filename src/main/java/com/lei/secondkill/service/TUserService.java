package com.lei.secondkill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.secondkill.entity.TUser;
import com.lei.secondkill.vo.LoginVo;
import com.lei.secondkill.vo.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * (TUser)表服务接口
 *
 * @author makejava
 * @since 2022-07-28 14:43:13
 */
public interface TUserService extends IService<TUser> {

    ResponseResult doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);


    //根据cookie获取用户
    TUser getUserByCookies(String userTicket, HttpServletRequest request, HttpServletResponse response);
}
