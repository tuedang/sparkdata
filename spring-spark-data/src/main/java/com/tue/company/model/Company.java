package com.tue.company.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Document(indexName = "dev_vnf", type = "companies", createIndex = false)
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@ToString
public class Company {
    @Id
    private String id;
    @Field(name = "name", type = FieldType.Text, store = true)
    private String name;
    @Field(name = "name_en", type = FieldType.Text, store = true)
    private String nameEn;

    @Field(name = "dob", type = FieldType.Date, store = true, format = DateFormat.date)
    private Instant dob;

    @Field(name = "tax_id", type = FieldType.Text, store = true)
    private String taxId;

    @Field(name = "category", type = FieldType.Text, store = true)
    private String category;
    @Field(name = "ceo", type = FieldType.Text)
    private String ceo;
    @Field(name = "ceo_address", type = FieldType.Text, store = true)
    private String ceoAddress;

    @Field( name = "website", type = FieldType.Text)
    private String website;
    @Field(name = "phone", type = FieldType.Text)
    private String phone;
    @Field(name = "fax", type = FieldType.Text, store = true)
    private String fax;
    @Field(name = "email", type = FieldType.Text)
    private String email;

    @Field(name = "mobile", type = FieldType.Text)
    private String mobile;

    @Field(name = "website_ping", type = FieldType.Date, store = true, format = DateFormat.date)
    private Instant websitePing;

    @Field("description")
    private Description description;

    @Field("address")
    private Address address;

    @Field(name = "_geodirectory")
    private GeoDirectory geoDirectory;

    @Field(name = "_log")
    private LogData logData;

    public String toMultipleLineString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
