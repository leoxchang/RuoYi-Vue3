package com.truntao.framework.config;

import java.util.Random;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

/**
 * 验证码文本生成器
 *
 * @author truntao
 */
public class KaptchaTextCreator extends DefaultTextCreator {
    private static final String[] CHAR_NUMBERS = "0,1,2,3,4,5,6,7,8,9,10".split(",");
    private final Random random = new Random();
    @Override
    public String getText() {
        Integer result;
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        StringBuilder suChinese = new StringBuilder();
        int randomOperands = random.nextInt(3);
        if (randomOperands == 0) {
            result = x * y;
            suChinese.append(CHAR_NUMBERS[x]);
            suChinese.append("*");
            suChinese.append(CHAR_NUMBERS[y]);
        } else if (randomOperands == 1) {
            if ((x != 0) && y % x == 0) {
                result = y / x;
                suChinese.append(CHAR_NUMBERS[y]);
                suChinese.append("/");
                suChinese.append(CHAR_NUMBERS[x]);
            } else {
                result = x + y;
                suChinese.append(CHAR_NUMBERS[x]);
                suChinese.append("+");
                suChinese.append(CHAR_NUMBERS[y]);
            }
        } else {
            if (x >= y) {
                result = x - y;
                suChinese.append(CHAR_NUMBERS[x]);
                suChinese.append("-");
                suChinese.append(CHAR_NUMBERS[y]);
            } else {
                result = y - x;
                suChinese.append(CHAR_NUMBERS[y]);
                suChinese.append("-");
                suChinese.append(CHAR_NUMBERS[x]);
            }
        }
        suChinese.append("=?@").append(result);
        return suChinese.toString();
    }
}