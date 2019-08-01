package com.tue.spark.address;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(DataProviderRunner.class)
public class SampleAddressParserTest {
    private final AddressParser addressParser = new AddressParserDelegator();

    @DataProvider
    public static Object[][] addressProvider() {
        return new Object[][]{
                {"Km 33, Quốc lộ 5A, Xã Minh Đức, Huyện Mỹ Hào, Tỉnh Hưng Yên", "Hưng Yên", "Mỹ Hào", "Minh Đức"},
        };
    }

    @Test
    @UseDataProvider("addressProvider")
    public void parseCommonAddress(String rawAddress, String expectedProvince, String expectedDistrict, String expectedWard) {
        AddressComponent addressComponent = addressParser.parse(rawAddress);

        assertNotNull(addressComponent);

        assertThat(addressComponent.getCountry()).isEqualTo(null);
        assertThat(addressComponent.getProvince()).isEqualTo(expectedProvince);
        assertThat(addressComponent.getDistrict()).isEqualTo(expectedDistrict);
        assertThat(addressComponent.getWard()).isEqualTo(expectedWard);
        assertThat(addressComponent.getStreet()).isNotEmpty();
        assertThat(addressComponent.isConfident()).isTrue();
        System.out.println(addressComponent);
    }

}
