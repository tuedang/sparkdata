package com.tue.company.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Address {
    private String address;
    private String country;
    private String district;
    private String province;
    private String ward;
    private GeoPoint location;

    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class GeoPoint {
        private Double lon;
        private Double lat;
    }
}

