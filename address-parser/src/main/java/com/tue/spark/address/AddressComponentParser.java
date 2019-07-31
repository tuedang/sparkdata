package com.tue.spark.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class AddressComponentParser {
    @Getter
    @AllArgsConstructor
    @ToString
    static class Result {
        private String value;
        private boolean confident;

        public static Result of(String value, boolean confident) {
            return new Result(value, confident);
        }
    }

    private static final AddressConfiguration ADDRESS_CONFIGURATION = AddressConfiguration.getInstance();

    public static Result checkCountry(String componentAddress) {
        for (String country : ADDRESS_CONFIGURATION.getCountries()) {
            if (StringUtils.equalsIgnoreCase(country, componentAddress)) {
                return new Result(componentAddress, true);
            }
        }
        return new Result(null, true);
    }

    public static Result checkProvince(String component) {
        for (String provinceKeyword : ADDRESS_CONFIGURATION.getProvinceKeywords()) {
            if (StringUtils.containsIgnoreCase(component, provinceKeyword)) {
                return Result.of(StringUtils.removeIgnoreCase(component, provinceKeyword).trim(), true);
            }
        }

        Map<String, List<String>> provinceMap = ADDRESS_CONFIGURATION.getProvinces();
        for (String masterProvince : provinceMap.keySet()) {
            if (StringUtils.equalsIgnoreCase(component, masterProvince)) {
                return Result.of(masterProvince, true);
            }
            for (String provinceKeyword : provinceMap.get(masterProvince)) {
                if (StringUtils.equalsIgnoreCase(component, provinceKeyword)) {
                    return Result.of(masterProvince, true);
                }
            }
        }
        return Result.of(null, false);
    }

    public static Result checkDistrict(String component, boolean provinceConfident) {
        for (String provinceKeyword : ADDRESS_CONFIGURATION.getDistrictKeywords()) {
            if (StringUtils.containsIgnoreCase(component, provinceKeyword)) {
                return Result.of(StringUtils.removeIgnoreCase(component, provinceKeyword).trim(), true);
            }
        }
        return Result.of(component, provinceConfident);
    }

    public static Result checkWard(String component) {
        for (String wardKeyword : ADDRESS_CONFIGURATION.getWardKeywords()) {
            if (StringUtils.containsIgnoreCase(component, wardKeyword)) {
                return Result.of(StringUtils.removeIgnoreCase(component, wardKeyword).trim(), true);
            }
        }
        return Result.of(component, false);
    }

    public static Result checkStreet(String component, boolean wardConfident) {
        for (String keyword : ADDRESS_CONFIGURATION.getStreetKeywords()) {
            if (StringUtils.containsIgnoreCase(component, keyword)) {
                return Result.of(component, true);
            }
        }
        return Result.of(component, wardConfident);
    }
}
