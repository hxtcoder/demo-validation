package com.example.demovalidation.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncryptIdValidator implements ConstraintValidator<EncryptId, String> {
    /**
     * a-f 或者 数字组成, 8 到 16 位
     */
    private static final Pattern PATTERN = Pattern.compile("^[a-f\\d]{8,16}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 不为null才进行校验
        if (value != null) {
            Matcher matcher = PATTERN.matcher(value);
            return matcher.find();
        }
        return true;
    }
}