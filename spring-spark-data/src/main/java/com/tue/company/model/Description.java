package com.tue.company.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Description {
    @Field(name = "business_registration", type = FieldType.Text)
    private String businessRegistration;

    @Field(name = "business_registration_date", type = FieldType.Date, store = true, format = DateFormat.date)
    private Instant businessRegistrationDate;

    @Field(name = "business_registration_place", type = FieldType.Text)
    private String businessRegistrationPlace;

    @Field(name = "number_of_employee", type = FieldType.Integer)
    private Integer numberOfEmployee;

    @Field(name = "rating_text", type = FieldType.Text, store = true)
    private String ratingText;

    @Field(name = "rating", type = FieldType.Integer)
    private Integer rating;

    @Field(name = "general_description", type = FieldType.Text, store = true)
    private String generalDescription;

    @Field( name = "logo", type = FieldType.Text, store = true)
    private String logo;

//    @Field( name = "images", type = FieldType.Object, store = true)
//    private List<String> images;
}
