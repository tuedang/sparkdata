package com.tue.spark.address.parsers;

import com.tue.spark.address.AddressComponent;
import com.tue.spark.address.AddressComponentParser;
import com.tue.spark.address.AddressDelimiter;
import com.tue.spark.address.AddressParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;

@Slf4j
public class WardTokenAddressParser implements AddressParser {
    private static final String RESOLVER_NAME = "ward";

    @Override
    public AddressComponent parse(String rawAddress) {
        Result result = AddressComponentParser.checkWard(rawAddress);
        if (!result.isConfident()) {
            return null;
        }
        int wardIndex = StringUtils.indexOf(rawAddress, result.getRawValue());

        int separatorIndex = -1;
        while (--wardIndex > 0) {
            if (rawAddress.charAt(wardIndex) == AddressDelimiter.DASH.charAt(0)
                    || rawAddress.charAt(wardIndex) == AddressDelimiter.COMMA.charAt(0)) {
                separatorIndex = wardIndex;
                break;
            }
        }
        if (separatorIndex < 0) {
            return null;
        }
        String streetPart = rawAddress.substring(0, separatorIndex).trim();
        String mainPart = rawAddress.substring(separatorIndex + 1).trim();
        char separator = rawAddress.charAt(separatorIndex);

        if (!mainPart.substring(0, result.getRawValue().length()).equals(result.getRawValue())) {
            log.warn("Separator ward issue - cannot take street and province, please check..."); //TODO improve ward parsing
            return null;
        }
        Validate.isTrue(mainPart.substring(0, result.getRawValue().length()).equals(result.getRawValue()),
                "Parsing issue with '%s'", result.getRawValue());

        List<String> mainParts = AddressDelimiter.splitByDelimitor(mainPart, separator);
        int len = mainParts.size();

        AddressComponent addressComponent = new AddressComponent();
        addressComponent.setStreet(streetPart);

        Result countryResult = AddressComponentParser.checkCountry(mainParts.get(len - 1));
        if (!countryResult.isConfident()) {
            return null;
        }
        addressComponent.setCountry(countryResult.getValue());
        int addressPartIndex = countryResult.getValue() == null ? len : len - 1;
        if (addressPartIndex < 3) {
            log.warn("Separator ward issue - missing info part on {}:[{}]", addressPartIndex, rawAddress);
            return null;
        }

        Result provinceResult = AddressComponentParser.checkProvince(mainParts.get(addressPartIndex - 1));
        Result districtResult = AddressComponentParser.checkDistrict(mainParts.get(addressPartIndex - 2), provinceResult);
        Result wardResult = AddressComponentParser.checkWard(mainParts.get(addressPartIndex - 3));
        if (provinceResult.isConfident()) {
            addressComponent.setProvince(provinceResult.getValue());
        }
        if (districtResult.isConfident()) {
            addressComponent.setDistrict(districtResult.getValue());
        }
        if (wardResult.isConfident()) {
            addressComponent.setWard(wardResult.getValue());
        }
        addressComponent.setConfident(countryResult.isConfident()
                && provinceResult.isConfident()
                && districtResult.isConfident()
                && wardResult.isConfident());
        if(addressComponent.isConfident()) {
            addressComponent.setResolver(RESOLVER_NAME);
        } else {
            Result wardResultTry = AddressComponentParser.checkWard(mainParts.get(0));
            Result districtResultTry = AddressComponentParser.checkDistrict(mainParts.get(1), provinceResult);

            if (districtResultTry.isConfident()) {
                addressComponent.setDistrict(districtResultTry.getValue());
            }
            if (wardResultTry.isConfident()) {
                addressComponent.setWard(wardResultTry.getValue());
            }
            addressComponent.setConfident(countryResult.isConfident()
                    && (provinceResult.isConfident())
                    && (districtResult.isConfident() || districtResultTry.isConfident())
                    && (wardResult.isConfident() || wardResultTry.isConfident()));
            if (addressComponent.isConfident()) {
                addressComponent.setResolver(RESOLVER_NAME);
            }
        }
        return addressComponent;
    }
}
