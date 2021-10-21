package com.shu.ming.mp.handler;

import cn.hutool.core.exceptions.ValidateException;
import com.shu.ming.mp.domain.Result;
import com.shu.ming.mp.enums.ResultCode;
import com.shu.ming.mp.exception.AccessLimitException;
import com.shu.ming.mp.exception.BusyException;
import com.shu.ming.mp.exception.NoLoginException;
import com.shu.ming.mp.exception.NoPermisssionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author JGod
 * @create 2021-10-11-11:51
 */
@ControllerAdvice
@Slf4j
public class CustomerExceptionHandler {

    /**
     * 对全局的空指针异常进行判断
     * @param e  空指针异常
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = NullPointerException.class)
    public Result nullPointerHandler(NullPointerException e){
        log.error("", e);
        return Result.failure("空指针异常", HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
    }

    /**
     * 忽略参数异常处理器
     *
     * @param e 忽略参数异常
     * @return ResponseResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result parameterMissingExceptionHandler(MissingServletRequestParameterException e) {
        log.error("", e);
        return Result.failure( "请求参数 " + e.getParameterName() + " 不能为空", 500, null);
    }

    /**
     * 缺少请求体异常处理器
     *
     * @param e 缺少请求体异常
     * @return ResponseResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        log.error("", e);
        return Result.failure( "参数体不能为空", 500, null);
    }


    /**
     * 未登录异常
     */
    @ResponseBody
    @ExceptionHandler(NoLoginException.class)
    public Result unLogin(NoLoginException e){
        return Result.failure(ResultCode.USER_NOT_LOGGED_IN);
    }

    /**
     * token异常
     */
    @ResponseBody
    @ExceptionHandler(ValidateException.class)
    public Result validateException(ValidateException e){
        return Result.failure(ResultCode.USER_NOT_LOGGED_IN);
    }

    /**
     * 无访问权限异常
     */
    @ResponseBody
    @ExceptionHandler(NoPermisssionException.class)
    public Result noPermisssionException(NoPermisssionException e){
        return Result.failure(ResultCode.PERMISSION_NO_ACCESS);
    }

    /**
     * 用户IP短时间多次请求异常
     */
    @ResponseBody
    @ExceptionHandler({AccessLimitException.class, UndeclaredThrowableException.class})
    public Result accessLimitException(Exception e){
        return Result.failure(ResultCode.MORE_THAN_REQUEST_COUNT);
    }

    /**
     * 服务器繁忙异常 -- 用于限流
     */
    @ResponseBody
    @ExceptionHandler({BusyException.class})
    public Result busyException(Exception e){
        return Result.failure(ResultCode.BUSY);
    }


    /**
     * 未被捕获的异常信息
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
        log.error("出现了一个未被捕获的异常: ", e);
        return Result.failure(ResultCode.FAIL);
    }
}
