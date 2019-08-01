package com.tue.spark.address;

import com.tue.spark.TextHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import static com.tue.spark.address.AddressParser.Result;

public class AddressComponentParser {

    private static final AddressConfiguration ADDRESS_CONFIGURATION = AddressConfiguration.getInstance();
    private static final String PROVINCE_NAME_KEY = "name";
    private static final String PROVINCE_DISTRICT_KEY = "district";

    public static AddressParser.Result checkCountry(String componentAddress) {
        for (String country : ADDRESS_CONFIGURATION.getCountries()) {
            if (StringUtils.equalsIgnoreCase(country, componentAddress)) {
                return Result.of(componentAddress, true);
            }
        }
        return Result.of(null, true);
    }

    public static Result checkProvince(String component) {
        for (String provinceKeyword : ADDRESS_CONFIGURATION.getProvinceKeywords()) {
            if (StringUtils.containsIgnoreCase(component, provinceKeyword)) {
                String rawProvince = StringUtils.removeIgnoreCase(component, provinceKeyword).trim();
                if (StringUtils.isEmpty(rawProvince)) {
                    return Result.of(rawProvince, false, component);
                }
                String noAccentrawProvince = TextHelper.stripAccents(rawProvince);
                for (String masterProvince : ADDRESS_CONFIGURATION.getProvinces().keySet()) {
                    String noAccentMasterProvince = TextHelper.stripAccents(masterProvince);
                    if (StringUtils.containsIgnoreCase(noAccentrawProvince, noAccentMasterProvince)) {
                        return Result.of(masterProvince, true, component);
                    }
                }
                Result provinceResult = checkProvinceInDictionary(rawProvince);
                if (provinceResult.isConfident()) {
                    return Result.of(provinceResult.getValue(), true, component);
                }
            }
        }

        Result provinceResult = checkProvinceInDictionary(component);
        if (provinceResult.isConfident()) {
            return Result.of(provinceResult.getValue(), true, component);
        }
        return Result.of(null, false, component);
    }

    private static Result checkProvinceInDictionary(String rawProvince) {
        Map<String, Map<String, List<String>>> provinceMap = ADDRESS_CONFIGURATION.getProvinces();
        String noAccentComponent = TextHelper.stripAccents(rawProvince);
        for (String masterProvince : provinceMap.keySet()) {
            String noAccentMasterProvince = TextHelper.stripAccents(masterProvince);
            if (StringUtils.equalsIgnoreCase(noAccentComponent, noAccentMasterProvince)) {
                return Result.of(masterProvince, true, rawProvince);
            }
            for (String provinceKeyword : provinceMap.get(masterProvince).get(PROVINCE_NAME_KEY)) {
                if (StringUtils.equalsIgnoreCase(noAccentComponent, TextHelper.stripAccents(provinceKeyword))) {
                    return Result.of(masterProvince, true, rawProvince);
                }
            }
        }
        return Result.of(null, false, rawProvince);
    }

    public static Result checkDistrict(String component, Result provinceResult) {
        for (String districtKeyword : ADDRESS_CONFIGURATION.getDistrictKeywords()) {
            if (StringUtils.containsIgnoreCase(component, districtKeyword)) {
                return Result.of(StringUtils.removeIgnoreCase(component, districtKeyword).trim(), true);
            }
        }

        if (provinceResult.isConfident()) {
            Map<String, List<String>> provinceMapData = ADDRESS_CONFIGURATION.getProvinces().get(provinceResult.getValue());
            if (provinceMapData == null) {
                System.err.println(String.format("PROVINCE NULL [%s]", provinceResult));
            }
            if (provinceMapData != null && provinceMapData.containsKey(PROVINCE_DISTRICT_KEY)) {
                List<String> districts = provinceMapData.get(PROVINCE_DISTRICT_KEY);
                for (String district : districts) {
                    if (StringUtils.containsIgnoreCase(component, district)) {
                        return Result.of(district, true);
                    }
                }
            }
        }
        return Result.of(component, provinceResult.isConfident());
    }

    public static Result checkWard(String component) {
        for (String wardKeyword : ADDRESS_CONFIGURATION.getWardKeywords()) {
            if (StringUtils.containsIgnoreCase(component, wardKeyword)) {
                return Result.of(StringUtils.removeIgnoreCase(component, wardKeyword).trim(), true, wardKeyword);
            }
        }
        return Result.of(component, false);
    }

    public static Result checkStreet(String component, boolean wardConfident) {
        for (String keyword : ADDRESS_CONFIGURATION.getStreetKeywords()) {
            if (StringUtils.containsIgnoreCase(component, keyword)) {
                return Result.of(component, true, keyword);
            }
        }
        return Result.of(component, wardConfident);
    }
}
