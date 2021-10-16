package com.shu.ming.mp.handler;

import com.shu.ming.mp.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
        log.info("happen a NullPointerException: {0}" , e);
        return Result.failure("空指针异常", HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
    }
}
