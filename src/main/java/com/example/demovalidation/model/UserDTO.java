package com.example.demovalidation.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class UserDTO {
    private Long userId;

    @NotNull(message = "用户名不能为空")
    @Length(min = 2, max = 10, message = "用户名长度为 2-10")
    private String userName;

    @NotNull(message = "账号不能为空")
    @Length(min = 6, max = 20, message = "账号长度为 2-10")
    private String account;

    @NotNull(message = "密码不能为空")
    @Length(min = 6, max = 8, message = "密码长度为 6-8")
    private String password;
}