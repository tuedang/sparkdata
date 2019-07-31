package com.tue.spark.address;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddressParserExtender {
    private AddressComponentParser.Result countryResult;
    private AddressComponentParser.Result provinceResult;
    private AddressComponentParser.Result districtResult;
    private AddressComponentParser.Result wardResult;
    private AddressComponentParser.Result streetResult;

    private AddressComponent addressComponentReference;
    private String rawAddress;
    private String delimitor;

    public AddressComponent correct() {
        return addressComponentReference;
    }
}
