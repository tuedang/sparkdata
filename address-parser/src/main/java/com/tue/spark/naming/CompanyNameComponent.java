package com.tue.spark.naming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class CompanyNameComponent implements Serializable {
    private String name;
    private String type;

    public CompanyNameComponent(String name) {
        this.name = name;
    }
}
