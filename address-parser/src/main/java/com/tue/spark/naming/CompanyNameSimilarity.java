package com.tue.spark.naming;

import org.apache.commons.lang3.StringUtils;

public class CompanyNameSimilarity {
    private static CompanyNameParser companyNameParser = new CompanyNameParser();

    public static boolean isSimilar(String rawName1, String rawName2) {
        CompanyNameComponent c1 = companyNameParser.parse(rawName1);
        CompanyNameComponent c2 = companyNameParser.parse(rawName2);
        if (c1 == null || c2 == null || c1.getName() == null || c2.getName() == null) {
            return false;
        }
        return StringUtils.equalsIgnoreCase(c1.getName(), c2.getName());
    }
}
