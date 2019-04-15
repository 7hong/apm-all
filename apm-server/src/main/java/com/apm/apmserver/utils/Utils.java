package com.apm.apmserver.utils;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/17 14:37
 * @Description:
 */
public class Utils {

    public static String simpMethodName(String fullName) {

        if (fullName == null || "".equals(fullName)) {
            return "";
        }

        int i = fullName.lastIndexOf(".");
        return fullName.substring(i + 1);

    }
}
