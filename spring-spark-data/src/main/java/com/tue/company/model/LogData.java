package com.tue.company.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class LogData {
    @Field(name = "addr", type = FieldType.Text)
    private String address;
    @Field(name = "geocoding", type = FieldType.Text)
    private String geocoding;
    @Field(name = "url_ref_thongtincongty", type = FieldType.Text)
    private String urlRefThongTinCongTy;

    @Field(name = "url_ref_yellowpages", type = FieldType.Text)
    private String urlRefYellowPages;

}
