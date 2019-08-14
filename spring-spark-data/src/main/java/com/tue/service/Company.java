package com.tue.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Company implements Serializable {
    private String name;
    @JsonProperty("tax_id")
    private String taxId;
    private String website;
    private Address address;

    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address implements Serializable {
        private String address;
        private String province;
        private String district;
        private String ward;
    }
}
