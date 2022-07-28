package com.lei.secondkill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.secondkill.entity.TUser;
import com.lei.secondkill.mapper.TUserMapper;
import com.lei.secondkill.service.TUserService;
import com.lei.secondkill.utils.MD5Utils;
import com.lei.secondkill.utils.validtorUtil;
import com.lei.secondkill.vo.AppHttpCodeEnum;
import com.lei.secondkill.vo.LoginVo;
import com.lei.secondkill.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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


    /**
     * 登录功能
     * @return
     * @param loginVo
     */
    @Override
    public ResponseResult doLogin(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) ){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }

        if(!validtorUtil.isMobile(mobile)){
            return ResponseResult.errorResult(AppHttpCodeEnum.NICAKNAME_EXIST);
        }

        TUser user = tUserMapper.selectById(mobile);
        if(user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }

        if(!MD5Utils.fromPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        return ResponseResult.okResult(user);
    }
}
