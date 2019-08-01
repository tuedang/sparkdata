package com.tue.spark.address.parsers;

import com.tue.spark.address.AddressComponent;
import com.tue.spark.address.AddressComponentParser;
import com.tue.spark.address.AddressParser;
import org.apache.commons.lang3.StringUtils;

public class StreetTokenAddressParser implements AddressParser {

    @Override
    public AddressComponent parse(String rawAddress) {
        return null;
    }

    private int findStreetIndex(String rawAddress) {
        Result result = AddressComponentParser.checkStreet(rawAddress, false);
        if(result.isConfident()) {
            return StringUtils.indexOf(rawAddress, result.getRawValue());
        }
        return -1;
    }
}
