package com.tue.domain.similarity;

import com.tue.service.Company;
import com.tue.spark.address.AddressComponent;
import com.tue.spark.address.AddressParser;
import com.tue.spark.address.AddressParserDelegator;
import com.tue.spark.naming.CompanyNameParser;
import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class CompanySimilarity {
    private static final AddressParser addressParser = new AddressParserDelegator();
    private static final CompanyNameParser COMPANY_NAME_PARSER = new CompanyNameParser();
    public static boolean isSimilar(Company rootCompany, Company referenceCompany) {
        double scoreAddress = StringSimilarity.isSimilarAddress(rootCompany.getAddress().getAddress(), referenceCompany.getAddress().getAddress());
        double scoreName = StringSimilarity.isSimilarAddress(rootCompany.getName(), referenceCompany.getName());
        boolean rawSelected = scoreAddress > 0.7 && scoreName > 0.7;
        if (rawSelected) {
            AddressComponent addressRootComponent = addressParser.parse(rootCompany.getAddress().getAddress());
            AddressComponent addressRefComponent = addressParser.parse(referenceCompany.getAddress().getAddress());
            return addressRootComponent != null && addressRefComponent != null
                    && equalsIgnoreCase(addressRootComponent.getDistrict(), addressRefComponent.getDistrict())
                    && equalsIgnoreCase(addressRootComponent.getProvince(), addressRefComponent.getProvince())
                    && equalsIgnoreCase(addressRootComponent.getWard(), addressRefComponent.getWard())
//                    && equalsIgnoreCase(addressRootComponent.getStreet(), addressRefComponent.getStreet())
                    ;
        }
        return rawSelected;
    }

    public static boolean isSimilarByName(Company rootCompany, Company referenceCompany) {
        return StringUtils.equalsIgnoreCase(COMPANY_NAME_PARSER.parse(rootCompany.getName()).getName(),
                COMPANY_NAME_PARSER.parse(referenceCompany.getName()).getName());
    }
}
