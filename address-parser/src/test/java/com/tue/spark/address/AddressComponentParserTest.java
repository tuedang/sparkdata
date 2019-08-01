package com.tue.spark.address;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(DataProviderRunner.class)
public class AddressComponentParserTest {

    @DataProvider
    public static Object[][] dataProvinceWrongAccents() {
        return new Object[][]{
            {"Bình Đinh", "Bình Định"},
            {"Thanh Phố Hồ Chí Minh", "Hồ Chí Minh"},
            {"Ho Chi Minh City", "Hồ Chí Minh"},
            {"TP.HCM", "Hồ Chí Minh"},
            {"TP. HCM", "Hồ Chí Minh"},
        };
    }

    @Test
    @UseDataProvider("dataProvinceWrongAccents")
    public void checkProvince_autoCorrectAccents(String province, String expectedProvince) {
        AddressParser.Result result = AddressComponentParser.checkProvince(province);

        assertThat(result).isNotEqualTo(null);
        assertThat(result.getValue()).isEqualTo(expectedProvince);
        assertThat(result.getRawValue()).isEqualTo(province);
        assertThat(result.isConfident()).isEqualTo(true);
    }
}
