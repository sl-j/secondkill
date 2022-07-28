package com.lei.secondkill.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (TUser)表实体类
 *
 * @author makejava
 * @since 2022-07-28 14:43:11
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class TUser {
    //用ID，手机号码@TableId
    private Long id;

    
    private String nickname;
    //MD5(MD5(PASS明文 + 固定salt) + salt)
    private String password;
    //盐值
    private String salt;
    //头像
    private String head;
    //注册时间
    private Date registerDate;
    //最后一次登录时间
    private Date lastLoginDate;
    //登录次数
    private Integer loginCount;



}
