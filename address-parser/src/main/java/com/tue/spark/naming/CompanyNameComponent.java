package com.tue.spark.naming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompanyNameComponent {
    private String name;
    private String type;

    public CompanyNameComponent(String name) {
        this.name = name;
    }
}
