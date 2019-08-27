package com.tue.company.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Description {
    @JsonProperty("business_registration")
    private String businessRegistration;

    @JsonProperty("business_registration_date")
    private String businessRegistrationDate;

    @JsonProperty("business_registration_place")
    private String businessRegistrationPlace;

    @JsonProperty("number_of_employee")
    private Integer numberOfEmployee;

}
