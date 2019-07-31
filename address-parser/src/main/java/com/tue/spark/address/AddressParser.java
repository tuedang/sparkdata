package com.tue.spark.address;

import org.apache.commons.lang3.StringUtils;

public class AddressParser {
    public static AddressComponent parse(String rawAddress) {
        String[] components = StringUtils.split(rawAddress, AddressDelimiter.detectDelimitor(rawAddress));
        return null;
    }


}
