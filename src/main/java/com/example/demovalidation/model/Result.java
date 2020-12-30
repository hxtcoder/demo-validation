package com.example.demovalidation.model;

import com.example.demovalidation.exception.BusinessExceptionEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: Result
 * @Description: 返回的结果对象
 * @Author: wentian
 * @Date: 2020/12/29 11:16 下午
 * @Version: 1.0
 */
@Getter
@Setter
public class Result<T> {

    private int retCode;

    private String retMsg;

    private T data;

    private Result() {}

    public Result(BusinessExceptionEnum bizEx) {
        this.retCode = bizEx.getBizCode();
        this.retMsg = bizEx.getBizMsg();
    }

    public Result(int bizCode, String bizMsg) {
        this.retCode = bizCode;
        this.retMsg = bizMsg;
    }

    public static Result ok() {
        return new Result(BusinessExceptionEnum.E_10200);
    }

    public static <T> Result ok(T t) {
        Result<T> result = new Result<>(BusinessExceptionEnum.E_10200);
        result.setData(t);
        return result;
    }
}
