package com.shu.ming.mp.handler;

import cn.hutool.core.exceptions.ValidateException;
import com.shu.ming.mp.domain.Result;
import com.shu.ming.mp.domain.ResultCode;
import com.shu.ming.mp.exception.NoLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Result ValidateException(ValidateException e){
        return Result.failure(ResultCode.USER_NOT_LOGGED_IN);
    }
}
