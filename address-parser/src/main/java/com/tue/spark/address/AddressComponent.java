package com.tue.spark.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddressComponent {
    private String country;
    private String province;
    private String district;
    private String ward;
    private String street;

    private boolean confident;

    @ToString.Exclude
    private AddressParserExtender addressParserExtender;
}
