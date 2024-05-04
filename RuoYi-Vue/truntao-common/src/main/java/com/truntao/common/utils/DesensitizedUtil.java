package com.truntao.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 脱敏工具类
 *
 * @author truntao
 */
public class DesensitizedUtil {

    private DesensitizedUtil() {
    }

    /**
     * 星号
     */
    private static final char ASTERISK = '*';

    /**
     * 密码的全部字符都用*代替，比如：******
     *
     * @param password 密码
     * @return 脱敏后的密码
     */
    public static String password(String password) {
        if (StringUtils.isBlank(password)) {
            return StringUtils.EMPTY;
        }
        return StringUtils.repeat('*', password.length());
    }

    /**
     * 车牌中间用*代替，如果是错误的车牌，不处理
     *
     * @param carLicense 完整的车牌号
     * @return 脱敏后的车牌
     */
    public static String carLicense(String carLicense) {
        if (StringUtils.isBlank(carLicense)) {
            return StringUtils.EMPTY;
        }
        // 普通车牌
        if (carLicense.length() == 7) {
            carLicense = hide(carLicense, 3, 6);
        } else if (carLicense.length() == 8) {
            // 新能源车牌
            carLicense = hide(carLicense, 3, 7);
        }
        return carLicense;
    }

    /**
     * 替换指定字符串的指定区间内字符为"*"
     *
     * @param str          字符串
     * @param startInclude 开始位置（包含）
     * @param endExclude   结束位置（不包含）
     * @return 替换后的字符串
     */
    public static String hide(CharSequence str, int startInclude, int endExclude) {
        if (StringUtils.isEmpty(str)) {
            return StringUtils.EMPTY;
        }
        final int strLength = str.length();
        if (startInclude > strLength) {
            return StringUtils.EMPTY;
        }
        if (endExclude > strLength) {
            endExclude = strLength;
        }
        if (startInclude > endExclude) {
            // 如果起始位置大于结束位置，不替换
            return StringUtils.EMPTY;
        }
        final char[] chars = new char[strLength];
        for (int i = 0; i < strLength; i++) {
            if (i >= startInclude && i < endExclude) {
                chars[i] = ASTERISK;
            } else {
                chars[i] = str.charAt(i);
            }
        }
        return new String(chars);
    }
}
