package com.redsea.utils;

import java.util.Arrays;

/**
 * 对字符串的简单处理
 * @author Rocky
 * @date 2013-6-6 下午5:08:06
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 截取文字safe 中文
     * @param @param string
     * @param @param length
     * @param @param more like `...`,`>>>`
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public static String subCn(String string, int length, String more) {
        if(StringUtils.isNotEmpty(string)) {
            char[] chars = string.toCharArray();
            if (chars.length > length) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    sb.append(chars[i]);
                }
                sb.append(more);
                return sb.toString();
            }
        }
        return string;
    }

    /**
     * 字符串全角转半角
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public static String togglecase(String string) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            sb.append(CharKit.regularize(string.charAt(i)));
        }
        return sb.toString();
    }

    /**
     * 功能描述: 生成sql占位符 ?,?,?
     * @param @param size
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public static String sqlHolder(int size) {
        String[] paras = new String[size];
        Arrays.fill(paras, "?");
        return StringUtils.join(paras, ',');
    }
    
}
