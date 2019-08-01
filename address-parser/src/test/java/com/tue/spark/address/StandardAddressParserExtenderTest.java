package com.tue.spark.address;

import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public class StandardAddressParserExtenderTest {
    @Test
    public void parseCommonAddress_thitran() {
        String rawAddress = "Tổ 4, Thị Trấn Quang Minh, Huyện Mê Linh, Thành phố Hà Nội";
        AddressComponent addressComponent = StandardAddressParser.parse(rawAddress);

        assertNotNull(addressComponent);

        assertThat(addressComponent.getCountry()).isEqualTo(null);
        assertThat(addressComponent.getProvince()).isEqualTo("Hà Nội");
        assertThat(addressComponent.getDistrict()).isEqualTo("Mê Linh");
        assertThat(addressComponent.getWard()).isEqualTo("Quang Minh");
        assertThat(addressComponent.getStreet()).isEqualTo("Tổ 4");
        System.out.println(addressComponent);
    }

    @Test
    public void parseCommonAddress_type_onward() {
        String rawAddress = "390-392 Nguyễn Thị Minh Khai - Phuờng 05 - Quận 3 - TP Hồ Chí Minh";
        AddressComponent addressComponent = StandardAddressParser.parse(rawAddress);

        assertNotNull(addressComponent);

        assertThat(addressComponent.getCountry()).isEqualTo(null);
        assertThat(addressComponent.getProvince()).isEqualTo("Hồ Chí Minh");
        assertThat(addressComponent.getDistrict()).isEqualTo("3");
        assertThat(addressComponent.getWard()).isEqualTo("05");
        assertThat(addressComponent.getStreet()).isEqualTo("390- 392 Nguyễn Thị Minh Khai");
        System.out.println(addressComponent);
    }

    @Test
    @Ignore
    public void parseCommonAddress_mixingSeparator() {
        String rawAddress = "Số 9, ngách 83/32, ngõ 83, đường Ngọc Hồi, tổ 7 - Phường Hoàng Liệt - Quận Hoàng Mai - Hà Nội";
        AddressComponent addressComponent = StandardAddressParser.parse(rawAddress);

        assertNotNull(addressComponent);

        assertThat(addressComponent.getCountry()).isEqualTo(null);
        assertThat(addressComponent.getProvince()).isEqualTo("Hà Nội");
        assertThat(addressComponent.getDistrict()).isEqualTo("Hoàng Mai");
        assertThat(addressComponent.getWard()).isEqualTo("Hoàng Liệt");
        assertThat(addressComponent.getStreet()).isEqualTo("Số 9, ngách 83/32, ngõ 83, đường Ngọc Hồi, tổ 7");
        System.out.println(addressComponent);
    }




}
