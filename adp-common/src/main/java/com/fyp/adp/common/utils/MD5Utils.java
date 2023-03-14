package com.fyp.adp.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

/**
 * @Description: MD5 工具
 * @Author: ruitao.wei
 * @Date: 2023/3/12 0:57
 */
public class MD5Utils {

    /**
     * MD5生成器
     * @param element 计算元素
     * @return MD5
     */
    public static String generate(String... element) {
        return generate(-1, element);
    }

    /**
     * MD5生成器
     * @param end ...终止值
     * @param element 计算元素
     * @return MD5
     */
    public static String generate(int end, String... element) {
        return generate(0, end, element);
    }

    /**
     * MD5生成器
     * @param start 截取偏移量起始值
     * @param end ...终止值
     * @param element 计算元素
     * @return MD5
     */
    public static String generate(int start, int end, String... element) {
        StringBuilder sb = new StringBuilder();
        for (String e : element) {
            sb.append(e);
        }
        return StringUtils.substring(DigestUtils.md5DigestAsHex(sb.toString().getBytes()).replaceAll("-", ""), start, end);
    }
}
