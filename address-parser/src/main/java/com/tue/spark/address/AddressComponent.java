package com.tue.spark.address;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressComponent {
    private String country;
    private String province;
    private String district;
    private String ward;

    private String street;
    private String home;
}
