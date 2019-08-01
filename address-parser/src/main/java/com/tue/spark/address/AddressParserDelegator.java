package com.tue.spark.address;

import com.google.common.collect.ImmutableList;
import com.tue.spark.address.parsers.DistrictTokenAddressParser;
import com.tue.spark.address.parsers.ExtendedAddressParser;
import com.tue.spark.address.parsers.StandardAddressParser;
import com.tue.spark.address.parsers.StreetTokenAddressParser;
import com.tue.spark.address.parsers.WardTokenAddressParser;

import java.util.List;

public class AddressParserDelegator implements AddressParser {
    private final StandardAddressParser standardAddressParser = new StandardAddressParser();
    private final WardTokenAddressParser wardTokenAddressParser = new WardTokenAddressParser();
    private final DistrictTokenAddressParser districtTokenAddressParser = new DistrictTokenAddressParser();
    private final StreetTokenAddressParser streetTokenAddressParser = new StreetTokenAddressParser();
    private final ExtendedAddressParser extendedAddressParser = new ExtendedAddressParser();

    private final List<AddressParser> ALTERNATIVE_ADDRESS_PARSERS = ImmutableList.of(
            districtTokenAddressParser,
            wardTokenAddressParser,
            streetTokenAddressParser,
            extendedAddressParser
    );

    @Override
    public AddressComponent parse(String rawAddress) {
        AddressComponent addressComponent = standardAddressParser.parse(rawAddress);
        if (addressComponent != null && addressComponent.isConfident()) {
            return addressComponent;
        }
        for (AddressParser addressParser : ALTERNATIVE_ADDRESS_PARSERS) {
            AddressComponent alternativeAddressComponent = addressParser.parse(rawAddress);
            if (alternativeAddressComponent != null && alternativeAddressComponent.isConfident()) {
                return alternativeAddressComponent;
            }
        }
        return addressComponent;
    }
}
