package com.tue.spark;

import org.apache.commons.lang3.StringUtils;

public class TextHelper {
    public static String stripAccents(String txt) {
        if (txt == null) {
            return null;
        }
        return StringUtils.stripAccents(txt)
                .replace("Đ", "D")
                .replace("đ", "d");
    }
}
