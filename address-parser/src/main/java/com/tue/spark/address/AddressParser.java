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
        private String rawValue;

        public static Result of(String value, boolean confident) {
            return new Result(value, confident, null);
        }

        public static Result of(String value, boolean confident, String rawValue) {
            return new Result(value, confident, rawValue);
        }
    }

    AddressComponent parse(String rawAddress);
}
