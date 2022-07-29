package com.lei.secondkill.exception;


import com.lei.secondkill.vo.AppHttpCodeEnum;
import com.lei.secondkill.vo.ResponseResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        if(e instanceof GlobalException){
            GlobalException ex = (GlobalException) e;
            return ResponseResult.errorResult(ex.getAppHttpCodeEnum());
        }else if(e instanceof BindException){
            BindException ex = (BindException) e;
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
            responseResult.setMsg("参数校验异常" + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return  responseResult;
        }

        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}
