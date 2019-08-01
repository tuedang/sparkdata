package com.tue.spark.address;

import lombok.Builder;
import lombok.Getter;

import static com.tue.spark.address.AddressParser.Result;

@Builder
@Getter
public class AddressParserExtender {
    private AddressComponent addressComponentReference;
    private String rawAddress;
    private String delimitor;

    private Result countryResult;
    private Result provinceResult;
    private Result districtResult;
    private Result wardResult;
    private Result streetResult;
}
