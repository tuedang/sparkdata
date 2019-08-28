package com.tue.company.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LogData {
    @JsonProperty("addr")
    private String address;
    @JsonProperty("geocoding")
    private String geocoding;
    @JsonProperty("url_ref_thongtincongty")
    private String urlRefThongTinCongTy;

    @JsonProperty("url_ref_yellowpages")
    private String urlRefYellowPages;

}
