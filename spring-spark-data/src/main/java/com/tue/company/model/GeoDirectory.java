package com.tue.company.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class GeoDirectory {
    @Field(name = "post_id", type = FieldType.Text)
    private String postId;
    @Field(name = "guid", type = FieldType.Text)
    private String guid;

    @Field(name = "post_date", type = FieldType.Date, store = true, format = DateFormat.date)
    private Instant postDate;

    @Field(name = "error", type = FieldType.Integer)
    private Integer error;

    @Field(name = "data")
    private ErrorData errorData;

    public String toStringNonNull() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE, true, true, true, null);
    }

    @Data
    @Builder @AllArgsConstructor @NoArgsConstructor
    public static class ErrorData {
        @Field(name = "vi", type = FieldType.Text)
        private String vi;

        @Field(name = "geodir_website", type = FieldType.Text)
        private String geodirWebsite;

        @Field(name = "geodir_tax_code", type = FieldType.Text)
        private String geodirTaxCode;

        @Field(name = "geodir_email1", type = FieldType.Text)
        private String geodirEmail1;
        @Field(name = "geodir_email2", type = FieldType.Text)
        private String geodirEmail2;
        @Field(name = "geodir_email3", type = FieldType.Text)
        private String geodirEmail3;

        @Field(name = "geodir_contact 1", type = FieldType.Text)
        private String geodirContact1;
        @Field(name = "geodir_contact 2", type = FieldType.Text)
        private String geodirContact2;
        @Field(name = "geodir_contact 3", type = FieldType.Text)
        private String geodirContact3;
        @Field(name = "geodir_contact 4", type = FieldType.Text)
        private String geodirContact4;

        @Field(name = "post_longitude", type = FieldType.Text)
        private String postLon;
        @Field(name = "post_latitude", type = FieldType.Text)
        private String postLat;

        public String toString() {
            return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE, true, true, true, null);
        }
    }
}


