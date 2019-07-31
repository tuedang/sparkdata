package com.tue.spark.address;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class AddressDelimiter {
    private static final String COMMA = ",";
    private static final String DASH = "-";

    public static String detectDelimitor(String rawAddress) {
        Validate.isTrue(rawAddress != null, "Address is null");

        int commaCount = StringUtils.countMatches(rawAddress, COMMA);
        if (commaCount >= 4) {
            return COMMA;
        }
        int dashCount = StringUtils.countMatches(rawAddress, DASH);
        if (dashCount >= 4) {
            return DASH;
        }
        if (commaCount > dashCount && commaCount > 2) {
            return COMMA;
        }
        if (dashCount > commaCount && dashCount > 2) {
            return DASH;
        }
        return null;
    }
}
