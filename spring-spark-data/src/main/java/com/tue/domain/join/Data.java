package com.tue.domain.join;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Data {
    private long dataId;
    private String code;
    private String value;
    private long dataFamilyId;
}

@AllArgsConstructor
@NoArgsConstructor
@Getter
class SubData {
    private String subId;
}

