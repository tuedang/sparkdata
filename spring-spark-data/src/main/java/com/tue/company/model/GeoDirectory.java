package com.tue.company.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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

}
