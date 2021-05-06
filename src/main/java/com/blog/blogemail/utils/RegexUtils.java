package com.blog.blogemail.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class RegexUtils {

    public static boolean checkEmailRegex(String email) {
        String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        return checkByRegex(email, regex);
    }

    public static boolean checkPhoneRegex(String phone) {
        String regex = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
        return checkByRegex(phone, regex);
    }

    public static boolean checkByRegex(String email, String regex) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(regex)) {
            return false;
        }
        return Pattern.matches(regex, email);
    }

}
