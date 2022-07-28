package com.lei.secondkill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * md5加密
 */
@Component
public class MD5Utils {

    private static final String SALT = "1a2b3c4a";

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    /**
     * 前端发送密码到后端时，进行第一次md5加密
     * @param inputPass
     * @return
     */
    public static String inputPassToFromPass(String inputPass){
        String str = SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(3);
        return md5(str);
    }

    /**
     * 后端存入数据库时进行第二次md5加密
     * @param fromPass
     * @param salt
     * @return
     */
    public static String fromPassToDBPass(String fromPass,String salt){
        String str = SALT.charAt(0) + SALT.charAt(2) + fromPass + SALT.charAt(3);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass,String salt){
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = fromPassToDBPass(fromPass,salt);
        return dbPass;
    }
}
