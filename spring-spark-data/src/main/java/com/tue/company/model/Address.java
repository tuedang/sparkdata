package com.tue.company.model;

import lombok.Data;

@Data
public class Address {
    private String address;
    private String country;
    private String district;
    private String province;
    private String ward;
    private GeoPoint location;
}

@Data
class GeoPoint {
    private Double lon;
    private Double lat;
}
