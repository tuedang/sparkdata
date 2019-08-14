package com.tue.spark.naming;

import org.apache.commons.lang3.StringUtils;

public class CompanyNameParser {
    private static final String[] stopwordsCompany = {"&", "Và", "?", "-", "–",
            "Công Ty", "cty", "Cổ Phần", "TNHH", "Trách Nhiệm Hữu Hạn",
            "tm", "Sản Xuất", "Thương Mại", "Dịch Vụ",
            "Một Thành Viên"};
    public CompanyNameComponent parse(String rawCompanyName) {
        if (StringUtils.isEmpty(rawCompanyName)) {
            return null;
        }
        String type = getType(rawCompanyName);
        String name = StringUtils.removeIgnoreCase(rawCompanyName, "Thương Mại Và Dịch Vụ");
        name = StringUtils.removeEndIgnoreCase(name, "Việt Nam");
        name = StringUtils.removeEndIgnoreCase(name, "VietNam");

        name = stripStarts(name, stopwordsCompany);

        return new CompanyNameComponent(name, type);
    }

    private String getType(String rawCompanyName) {
        if (StringUtils.containsIgnoreCase(rawCompanyName, "tnhh")) {
            return "TNHH";
        } else if (StringUtils.containsIgnoreCase(rawCompanyName, "cp")
                || StringUtils.containsIgnoreCase(rawCompanyName, "Cổ phần")) {
            return "CP";
        }
        return null;
    }

    public static String stripStarts(String s, String... characters) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        String result = s.trim();

        while (true) {
            int lenBefore = result.length();
            for (String character : characters) {
                result = StringUtils.removeStartIgnoreCase(result, character).trim();
            }
            if (result.length() == lenBefore) {
                break;
            }
        }
        return result;
    }
}
