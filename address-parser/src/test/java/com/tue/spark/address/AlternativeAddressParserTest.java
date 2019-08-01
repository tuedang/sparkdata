package com.tue.spark.address;

import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@Ignore
public class AlternativeAddressParserTest {
    private final AddressParser addressParser = new AddressParserDelegator();

    @Test
    public void parseCommonAddress_mixingSeparator() {
        String rawAddress = "Số 9, ngách 83/32, ngõ 83, đường Ngọc Hồi, tổ 7 - Phường Hoàng Liệt - Quận Hoàng Mai - Hà Nội";
        AddressComponent addressComponent = addressParser.parse(rawAddress);

        assertNotNull(addressComponent);

        assertThat(addressComponent.getCountry()).isEqualTo(null);
        assertThat(addressComponent.getProvince()).isEqualTo("Hà Nội");
        assertThat(addressComponent.getDistrict()).isEqualTo("Hoàng Mai");
        assertThat(addressComponent.getWard()).isEqualTo("Hoàng Liệt");
        assertThat(addressComponent.getStreet()).isEqualTo("Số 9, ngách 83/32, ngõ 83, đường Ngọc Hồi, tổ 7");
        System.out.println(addressComponent);
    }

    @Test
    public void parseCommonAddress_specialCharProvince() {
        String rawAddress = "Nhà B2- TT Bộ Giáo Dục , ngõ 191, Đường lạc Long Quân , Phường Xuân La , Quận Tây Hồ , TP, Hà Nội .";
        AddressComponent addressComponent = addressParser.parse(rawAddress);

        assertNotNull(addressComponent);

        assertThat(addressComponent.getCountry()).isEqualTo(null);
        assertThat(addressComponent.getProvince()).isEqualTo("Hà Nội");
        assertThat(addressComponent.getDistrict()).isEqualTo("Tây Hồ");
        assertThat(addressComponent.getWard()).isEqualTo("Xuân La");
        assertThat(addressComponent.getStreet()).isEqualTo("Nhà B2- TT Bộ Giáo Dục , ngõ 191, Đường lạc Long Quân");
        System.out.println(addressComponent);
    }

}
