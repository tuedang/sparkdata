package com.tue.spark.address;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.stream.Collectors;

public class AddressDelimiter {
    public static final String COMMA = ",";
    public static final String DASH = "-";

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

    public static List<String> splitByDelimitor(String inputAddress, char delimitor) {
        return Splitter.on(delimitor)
                .trimResults()
                .omitEmptyStrings()
                .splitToList(inputAddress)
                .stream()
                .map(s -> StringUtils.removeEnd(s, ".").trim())
                .collect(Collectors.toList());
    }

    public static String detectPartialDelimitor(String searchString) {
        int commaCount = StringUtils.countMatches(searchString, COMMA);
        if (commaCount > 0) {
            return COMMA;
        }
        int dashCount = StringUtils.countMatches(searchString, DASH);
        if (dashCount > 0) {
            return DASH;
        }
        return null;
    }

    public static String detectLastDelimitor(String searchString) {
        int commaIndex = StringUtils.lastIndexOf(searchString, COMMA);
        int dashIndex = StringUtils.lastIndexOf(searchString, DASH);

        if (commaIndex > 0 && dashIndex > 0) {
            if (commaIndex > dashIndex) {
                return COMMA;
            } else {
                return DASH;
            }
        }

        if (commaIndex > -1) {
            return COMMA;
        }
        if (dashIndex > -1) {
            return DASH;
        }
        return null;
    }

    public static String detectFirstDelimitor(String searchString) {
        int commaIndex = StringUtils.indexOf(searchString, COMMA);
        int dashIndex = StringUtils.indexOf(searchString, DASH);

        if (commaIndex > 0 && dashIndex > 0) {
            if (commaIndex < dashIndex) {
                return COMMA;
            } else {
                return DASH;
            }
        }
        if (commaIndex > -1) {
            return COMMA;
        }
        if (dashIndex > -1) {
            return DASH;
        }
        return null;
    }
}
