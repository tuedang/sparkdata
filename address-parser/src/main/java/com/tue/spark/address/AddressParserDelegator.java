package com.tue.spark.address;

import com.tue.spark.address.parsers.DistrictTokenAddressParser;
import com.tue.spark.address.parsers.StandardAddressParser;
import com.tue.spark.address.parsers.WardTokenAddressParser;

public class AddressParserDelegator implements AddressParser {
    private StandardAddressParser standardAddressParser = new StandardAddressParser();
    private WardTokenAddressParser wardTokenAddressParser = new WardTokenAddressParser();
    private DistrictTokenAddressParser districtTokenAddressParser = new DistrictTokenAddressParser();

    @Override
    public AddressComponent parse(String rawAddress) {
        AddressComponent addressComponent = standardAddressParser.parse(rawAddress);
        if (addressComponent != null && addressComponent.isConfident()) {
            return addressComponent;
        }
        return addressComponent;
    }
}
