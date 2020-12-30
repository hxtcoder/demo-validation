package com.example.demovalidation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: BusinessExceptionEnum
 * @Description: 业务异常模型
 * @Author: wentian
 * @Date: 2020/12/29 11:19 下午
 * @Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum  BusinessExceptionEnum {
    E_10200(10200, "请求成功"),
    E_10400(10400, "参数错误"),
    E_10500(10500, "请求失败"),
    ;

    private int bizCode;

    private String bizMsg;
}
