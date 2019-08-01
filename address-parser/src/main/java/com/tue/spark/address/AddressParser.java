package com.tue.spark.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

public interface AddressParser {
    @Getter
    @AllArgsConstructor
    @ToString
    class Result {
        private String value;
        private boolean confident;

        public static Result of(String value, boolean confident) {
            return new Result(value, confident);
        }
    }

    AddressComponent parse(String rawAddress);
}
