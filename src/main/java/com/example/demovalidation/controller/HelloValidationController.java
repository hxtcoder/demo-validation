package com.example.demovalidation.controller;

import com.example.demovalidation.annotation.EncryptId;
import com.example.demovalidation.exception.BusinessExceptionEnum;
import com.example.demovalidation.model.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @ClassName: HelloValidationController
 * @Description: hello validation
 * @Author: wentian
 * @Date: 2020/12/29 11:14 下午
 * @Version: 1.0
 */
@Validated
@RestController
@RequestMapping("/hello/validation")
public class HelloValidationController {

    // 简单使用 ↓

    /**
     * request body
     */
    @PostMapping("/save")
    public Result saveUser(@RequestBody @Validated UserDTO userDTO) {
        // 校验通过，才会执行业务逻辑处理
        return Result.ok();
    }

    /**
     * 路径变量
     */
    @GetMapping("{userId}")
    public Result detail(@PathVariable("userId") @Min(10000000000000000L) Long userId) {
        // 校验通过，才会执行业务逻辑处理
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setAccount("11111111111111111");
        userDTO.setUserName("xixi");
        userDTO.setAccount("11111111111111111");
        return Result.ok(userDTO);
    }

    /**
     * 查询参数
     */
    @GetMapping("getByAccount")
    public Result getByAccount(@Length(min = 6, max = 20, message = "密码长度为 6-20") @NotNull @RequestParam("account") String account) {
        // 校验通过，才会执行业务逻辑处理
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(10000000000000003L);
        userDTO.setAccount(account);
        userDTO.setUserName("xixi");
        userDTO.setAccount("11111111111111111");
        return Result.ok(userDTO);
    }

    // 进阶使用 ↓ 分组/嵌套/集合校验/编程式校验


    /**
     * 分组 saveGroup @Min not work
     */
    @PostMapping("/save2")
    public Result saveUser(@RequestBody @Validated(UserDTO2.Save.class) UserDTO2 userDTO) {
        // 校验通过，才会执行业务逻辑处理
        return Result.ok();
    }

    /**
     * 分组. updateGroup @Min is working
     */
    @PostMapping("/update2")
    public Result updateUser(@RequestBody @Validated(UserDTO2.Update.class) UserDTO2 userDTO) {
        // 校验通过，才会执行业务逻辑处理
        return Result.ok();
    }

    /**
     * 嵌套
     */
    @PostMapping("/save3")
    public Result saveUser(@RequestBody @Validated(UserDTO3.Save.class) UserDTO3 userDTO) {
        // 校验通过，才会执行业务逻辑处理
        return Result.ok();
    }

    /**
     * 集合校验
     */
    @PostMapping("/saveList")
    public Result saveList(@RequestBody @Validated(UserDTO2.Save.class) ValidationList<UserDTO2> userList) {
        // 校验通过，才会执行业务逻辑处理
        return Result.ok();
    }

    /**
     * 自定义校验
     */
    @GetMapping("/custom/valid")
    public Result<Void> customValid(@EncryptId @RequestParam("id") String id) {
        // 自定义注解
        return Result.ok();
    }

    /**
     * 编程式校验
     *
     * 上面的示例都是基于注解来实现自动校验的，在某些情况下，我们可能希望以编程方式调用验证。
     * 这个时候可以注入 javax.validation.Validator 对象，然后再调用其 API。
     */

    @Autowired
    private javax.validation.Validator globalValidator;

    // 编程式校验
    @PostMapping("/saveWithCodingValidate")
    public Result saveWithCodingValidate(@RequestBody UserDTO2 userDTO) {
        Set<ConstraintViolation<UserDTO2>> validate = globalValidator.validate(userDTO, UserDTO2.Save.class);
        // 如果校验通过，validate为空；否则，validate包含未校验通过项
        if (validate.isEmpty()) {
            // 校验通过，才会执行业务逻辑处理

            return Result.ok();
        } else {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<UserDTO2> userDTOConstraintViolation : validate) {
                // 校验失败，做其它逻辑

                sb.append(userDTOConstraintViolation.getPropertyPath().toString()).append(":")
                        .append(userDTOConstraintViolation.getMessage()).append(";");
            }

            return new Result<>(BusinessExceptionEnum.E_10400.getBizCode(), sb.toString());
        }
    }


    /**
     * 快速失败（Fail Fast）
     *
     * Spring Validation 默认会校验完所有字段，然后才抛出异常。
     * 可以通过一些简单的配置，开启 Fail Fast 模式，一旦校验失败就立即返回。
     */
    @Autowired
//    @Qualifier(value = "myValidator")
    private Validator myValidator;

    @PostMapping("/fastFail")
    public Result fastFail(@RequestBody UserDTO2 userDTO) {
        Set<ConstraintViolation<UserDTO2>> validate = myValidator.validate(userDTO, UserDTO2.Save.class);
        // 如果校验通过，validate为空；否则，validate包含未校验通过项
        if (validate.isEmpty()) {
            // 校验通过，才会执行业务逻辑处理

            return Result.ok();
        } else {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<UserDTO2> userDTOConstraintViolation : validate) {
                // 校验失败，做其它逻辑

                sb.append(userDTOConstraintViolation.getPropertyPath().toString()).append(":")
                        .append(userDTOConstraintViolation.getMessage()).append(";");
            }

            return new Result<>(BusinessExceptionEnum.E_10400.getBizCode(), sb.toString());
        }
    }




}
