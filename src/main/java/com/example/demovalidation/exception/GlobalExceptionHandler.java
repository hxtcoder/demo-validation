package com.example.demovalidation.exception;

import com.example.demovalidation.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: GlobalExceptionHandler
 * @Description: TODO
 * @Author: wentian
 * @Date: 2020/12/29 11:35 下午
 * @Version: 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        log.error("请求参数错误异常. req: {}", request, ex);
        // 可以多种方式, 自行处理
        // 方式一
//        Result result;
//        StringBuilder sb = new StringBuilder();
//        if (ex.getBindingResult().hasErrors()) {
//            List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
//            allErrors.stream().map(error -> error.getDefaultMessage()).forEach(str -> sb.append(str).append(";"));
//            result = new Result(10400, sb.toString());
//        } else {
//            result = new Result(BusinessExceptionEnum.E_10400);
//        }
//        return result;

        // 方式二
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append("; ");
        }

        return  new Result(10400, sb.toString());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleMethodArgumentNotValidException(HttpServletRequest request, ConstraintViolationException ex) {
        log.error("请求参数错误异常. req: {}", request, ex);

        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        constraintViolations.stream().forEach(constraintViolation -> {
            // 可以多种方式, 自行处理
//            sb.append(constraintViolation.getMessage()).append(";");

            sb.append(constraintViolation.getPropertyPath().toString()).append(":")
                    .append(constraintViolation.getMessage()).append(";");
        });

        return  new Result(10400, sb.toString());
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Result handleUnknownException(HttpServletRequest request, Exception ex) {
        log.error("未知异常. req: {}", request, ex);

        return new Result(BusinessExceptionEnum.E_10500);
    }

}
