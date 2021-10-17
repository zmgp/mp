package com.shu.ming.mp.domain;

        import com.shu.ming.mp.enums.ResultCode;
        import lombok.Data;

        import java.io.Serializable;

/**
 * @author JGod
 * @create 2021-10-20-20:50
 */
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = -3948389268046368059L;

    private Integer code;

    private String msg;

    private Object data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static Result success(String msg, Object data) {
        Result result = new Result();
        result.setResultCode(msg);
        result.setData(data);
        return result;
    }


    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    public static Result failure(String msg, int code, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg = code.message();
    }

    public void setResultCode(String msg) {
        this.msg = msg;
        this.code = ResultCode.SUCCESS.code();
    }
}
