package com.tue.spark.naming;

import org.apache.commons.lang3.StringUtils;

public class CompanyNameParser {
    public CompanyNameComponent parse(String rawCompanyName) {
        if (StringUtils.isEmpty(rawCompanyName)) {
            return null;
        }
        String type = getType(rawCompanyName);
        String name = StringUtils.removeIgnoreCase(rawCompanyName, "Công Ty TNHH");
        name = StringUtils.removeIgnoreCase(name, "Cổ Phần Thương Mại Và Dịch Vụ");
        name = StringUtils.removeIgnoreCase(name, "Cổ Phần");
        name = StringUtils.removeIgnoreCase(name, "Công Ty");
        name = StringUtils.removeIgnoreCase(name, "cty");
        name = StringUtils.removeEndIgnoreCase(name, "Việt Nam");
        name = StringUtils.removeEndIgnoreCase(name, "VietNam").trim();

        name = StringUtils.removeStartIgnoreCase(name, "tm");
        name = StringUtils.removeStartIgnoreCase(name, "Thương mại");
        return new CompanyNameComponent(name.trim(), type);
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
}
