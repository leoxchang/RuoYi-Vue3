package com.truntao.test;

import com.truntao.system.domain.ro.SysUserUpdateParam;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangxinlei
 * @date 2024-07-29
 */
public class TestPattern {

    public static void main(String[] args) {
        System.out.println(hasSpecialSubstring("aaabaaa", 3));
        System.out.println(hasSpecialSubstring("abc", 2));
        System.out.println(hasSpecialSubstring("ii", 1));
        System.out.println(hasSpecialSubstring("dii", 1));
        System.out.println(hasSpecialSubstring("i", 1));
    }

    public static boolean hasSpecialSubstring(String s, int k) {
        int n = s.length();
        for (int i = 0; i <= n - k; i++) {
            char current = s.charAt(i);
            boolean valid = true;

            // 检查子字符串中的所有字符是否相同
            for (int j = 1; j < k; j++) {
                if (s.charAt(i + j) != current) {
                    valid = false;
                    break;
                }
            }

            if (!valid) continue;

            // 检查前面的字符是否不同
            if (i > 0 && s.charAt(i - 1) == current) {
                continue;
            }

            // 检查后面的字符是否不同
            if (i + k < n && s.charAt(i + k) == current) {
                continue;
            }

            // 如果所有条件都满足
            return true;
        }
        return false;
//        char[] chars = s.toCharArray();
//        if (chars.length < 2 && k == 1) {
//            return true;
//        }
//        int len = 0;
//        for (int i = 0; i < chars.length - 1; i++) {
//            if (chars[i] == chars[i + 1]) {
//                len += 1;
//            } else {
//                len = 0;
//            }
//            if ((len == k - 1 && len > 1) || (k == 1 && len == 0)) {
//                boolean a = i - k < 0 || chars[i - k + 1] != chars[i - k];
//                System.out.println("---" + a);
//                boolean b = i + 2 >= chars.length || chars[i + 1] != chars[i + 2];
//                System.out.println(i + "===" + b);
//                if (a && b) {
//                    return true;
//                }
//            }
//        }
//        return false;
    }

    public static boolean isThree(int n) {
        if (n == 1 || n == 2) {
            return false;
        }
        double sqrt = Math.sqrt(n);
        System.out.println(sqrt);
        if (Math.abs(sqrt - Math.round(sqrt)) < 1e-10) {
            int b = (int) sqrt;
            if (b > 2 && b % 2 == 0) {
                return false;
            }
            double sqrt2 = Math.sqrt(sqrt);
            System.out.println(sqrt2);
            return !(Math.abs(sqrt2 - Math.round(sqrt2)) < 1e-10);
        }
        return false;
    }


    public static void t(String[] args) {
        Pattern pattern = Pattern.compile("^$|^(?![a-zA-z]+$)(?!\\d+$)(?![!@#$%^&*]+$)(?![a-zA-z\\d]+$)" +
                "(?![a-zA-z!@#$%^&*]+$)(?![\\d!@#$%^&*]+$)[a-zA-Z\\d!@#$%^&*./].{8,}$");
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
