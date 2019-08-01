package com.tue.spark.address.parsers;

import com.google.common.base.Splitter;
import com.tue.spark.address.AddressComponent;
import com.tue.spark.address.AddressDelimiter;
import com.tue.spark.address.AddressParser;
import com.tue.spark.address.AddressParserExtender;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.tue.spark.address.AddressComponentParser.checkCountry;
import static com.tue.spark.address.AddressComponentParser.checkDistrict;
import static com.tue.spark.address.AddressComponentParser.checkProvince;
import static com.tue.spark.address.AddressComponentParser.checkStreet;
import static com.tue.spark.address.AddressComponentParser.checkWard;

public class StandardAddressParser implements AddressParser {

    public AddressComponent parse(String rawAddress) {
        String delimitor = AddressDelimiter.detectDelimitor(rawAddress);
        if (delimitor == null) {
            return null;
        }
        List<String> components = Splitter.on(delimitor)
                .trimResults()
                .omitEmptyStrings()
                .splitToList(rawAddress)
                .stream()
                .map(s -> StringUtils.removeEnd(s, "."))
                .collect(Collectors.toList());

        AddressComponent addressComponent = new AddressComponent();
        int i = components.size() - 1;

        // country
        Result country = checkCountry(components.get(i));
        if (country.getValue() != null) {
            addressComponent.setCountry(country.getValue());
            i--;
        }
        // province
        Result province = checkProvince(components.get(i));
        if (province.getValue() != null) {
            addressComponent.setProvince(province.getValue());
            i--;
        }
        // district
        Result district = checkDistrict(components.get(i--), province);
        addressComponent.setDistrict(district.getValue());

        // ward || street
        Result ward = checkWard(components.get(i--));
        if (ward.isConfident()) {
            addressComponent.setWard(ward.getValue());
        }

        int streetIndex = i;
        if (i < 0) {
            return addressComponent;
        }
        Result street = checkStreet(components.get(i--), ward.isConfident());
        if (street.isConfident()) {
            addressComponent.setStreet(street.getValue());
            if (!ward.isConfident()) {
                addressComponent.setWard(ward.getValue());
            }
        }

        if (!street.isConfident() && !ward.isConfident()) {
            Result streetFromWard = checkStreet(ward.getValue(), false);
            if (streetFromWard.isConfident()) {
                addressComponent.setStreet(streetFromWard.getValue());
                streetIndex++;
            } else {
                addressComponent.setWard(ward.getValue());
                addressComponent.setStreet(street.getValue());
            }
        }

        if (streetIndex > -1) {
            String streetValue = String.join(delimitor + " ", components.subList(0, streetIndex + 1));
            addressComponent.setStreet(streetValue);
        }

        addressComponent.setConfident(country.isConfident()
                && province.isConfident()
                && district.isConfident()
                && ward.isConfident());
        if (!addressComponent.isConfident()) {
            addressComponent.setAddressParserExtender(AddressParserExtender.builder()
                    .addressComponentReference(addressComponent)
                    .countryResult(country)
                    .provinceResult(province)
                    .districtResult(district)
                    .wardResult(ward)
                    .streetResult(street)
                    .rawAddress(rawAddress)
                    .delimitor(delimitor)
                    .build());
        }
        return addressComponent;
    }



}
