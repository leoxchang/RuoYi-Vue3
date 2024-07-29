package com.truntao.test;

import com.truntao.system.domain.ro.SysUserUpdateParam;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangxinlei
 * @date 2024-07-29
 */
public class TestPattern {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^$|^(?![a-zA-z]+$)(?!\\d+$)(?![!@#$%^&*]+$)(?![a-zA-z\\d]+$)(?![a-zA-z!@#$%^&*]+$)(?![\\d!@#$%^&*]+$)[a-zA-Z\\d!@#$%^&*./].{8,}$");
        Matcher matcher = pattern.matcher("");
        System.out.println(matcher.matches());
        System.out.println(pattern.matcher("Test12345").matches());
        System.out.println(pattern.matcher("$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2").matches());
        System.out.println(pattern.matcher("test123#@").matches());

        SysUserUpdateParam user = new SysUserUpdateParam();
        user.setUserName("test1234");
        user.setPassword("$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2"); // 测试密码

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<SysUserUpdateParam>> violations = validator.validate(user);
        if (violations.isEmpty()) {
            System.out.println("Password is valid.");
        } else {
            for (ConstraintViolation<SysUserUpdateParam> violation : violations) {
                System.out.println(violation.getMessage());
            }
        }
    }
}
