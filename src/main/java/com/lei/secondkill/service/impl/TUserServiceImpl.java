package com.lei.secondkill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.secondkill.entity.TUser;
import com.lei.secondkill.exception.GlobalException;
import com.lei.secondkill.mapper.TUserMapper;
import com.lei.secondkill.service.TUserService;
import com.lei.secondkill.utils.CookieUtil;
import com.lei.secondkill.utils.MD5Utils;
import com.lei.secondkill.utils.UUIDUtil;
import com.lei.secondkill.vo.AppHttpCodeEnum;
import com.lei.secondkill.vo.LoginVo;
import com.lei.secondkill.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * (TUser)表服务实现类
 *
 * @author makejava
 * @since 2022-07-28 14:43:15
 */
@Service("tUserService")
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

    @Autowired
    private TUserMapper tUserMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 登录功能
     * @return
     * @param loginVo
     * @param request
     * @param response
     */
    @Override
    public ResponseResult doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        TUser user = tUserMapper.selectById(mobile);
        if(user == null){
            throw new GlobalException(AppHttpCodeEnum.LOGIN_ERROR);
        }

        if(!MD5Utils.fromPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            throw new GlobalException(AppHttpCodeEnum.LOGIN_ERROR);
        }

        //生成cookie
        String uuid = UUIDUtil.uuid();
//        redisCache.setCacheObject("1",1);
        redisTemplate.opsForValue().set("user:" + uuid,user);
//        request.getSession().setAttribute(uuid,user);
        CookieUtil.setCookie(request,response,"userTicket",uuid);
        return ResponseResult.okResult(user);
    }

    @Override
    public TUser getUserByCookies(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isEmpty(userTicket)) return null;
        TUser user = (TUser) redisTemplate.opsForValue().get("user:" + userTicket);

        if(user != null){
            CookieUtil.setCookie(request,response,"userTicket",userTicket);
        }
        return user;
    }
}
