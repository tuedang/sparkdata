package com.tue.company.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

@Data
public class GeoDirectory {
    @JsonProperty("post_id")
    private String postId;
    @JsonProperty("guid")
    private String guid;

    @JsonProperty("post_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime postDate;

    @JsonProperty("error")
    private Integer error;

    @JsonProperty("data")
    private ErrorData errorData;

    public String toStringNonNull() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE, true, true, true, null);
    }

}

@Data
class ErrorData {
    @JsonProperty("vi")
    private String vi;

    @JsonProperty("geodir_website")
    private String geodirWebsite;

    @JsonProperty("geodir_tax_code")
    private String geodirTaxCode;

    @JsonProperty("geodir_email1")
    private String geodirEmail1;
    @JsonProperty("geodir_email2")
    private String geodirEmail2;
    @JsonProperty("geodir_email3")
    private String geodirEmail3;

    @JsonProperty("geodir_contact 1")
    private String geodirContact1;
    @JsonProperty("geodir_contact 2")
    private String geodirContact2;
    @JsonProperty("geodir_contact 3")
    private String geodirContact3;
    @JsonProperty("geodir_contact 4")
    private String geodirContact4;

    @JsonProperty("post_longitude")
    private String postLon;
    @JsonProperty("post_latitude")
    private String postLat;

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE, true, true, true, null);
    }
}
