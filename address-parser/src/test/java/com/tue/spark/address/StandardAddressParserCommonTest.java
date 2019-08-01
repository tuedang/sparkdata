package com.tue.spark.address;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class StandardAddressParserCommonTest {
    @Test
    public void parseCommonAddress_withDashSeparator() {
        String rawAddress = "76D Năm Châu - Phường 11 - Quận Tân Bình - TP Hồ Chí Minh - Việt Nam";
        AddressComponent addressComponent = StandardAddressParser.parse(rawAddress);

        assertNotNull(addressComponent);
        assertThat(addressComponent.getCountry()).isEqualTo("Việt Nam");
        assertThat(addressComponent.getProvince()).isEqualTo("Hồ Chí Minh");
        assertThat(addressComponent.getDistrict()).isEqualTo("Tân Bình");
        assertThat(addressComponent.getWard()).isEqualTo("11");
        assertThat(addressComponent.getStreet()).isEqualTo("76D Năm Châu");
    }

    @Test
    public void parseCommonAddress_withCommonSeparator() {
        String rawAddress = "144/4 Âu Cơ, Phường 9, Quận Tân Bình, TP. Hồ Chí Minh";
        AddressComponent addressComponent = StandardAddressParser.parse(rawAddress);

        assertNotNull(addressComponent);

        assertNull(addressComponent.getCountry());
        assertThat(addressComponent.getProvince()).isEqualTo("Hồ Chí Minh");
        assertThat(addressComponent.getDistrict()).isEqualTo("Tân Bình");
        assertThat(addressComponent.getWard()).isEqualTo("9");
        assertThat(addressComponent.getStreet()).isEqualTo("144/4 Âu Cơ");
    }

    @Test
    public void parseCommonAddress_withComma_ngongach() {
        String rawAddress = "Số nhà 64, ngách 28, ngõ Tô Tiền - Phường Trung Phụng - Quận Đống đa - Hà Nội";
        AddressComponent addressComponent = StandardAddressParser.parse(rawAddress);

        assertNotNull(addressComponent);

        assertThat(addressComponent.getCountry()).isEqualTo(null);
        assertThat(addressComponent.getProvince()).isEqualTo("Hà Nội");
        assertThat(addressComponent.getDistrict()).isEqualTo("Đống đa");
        assertThat(addressComponent.getWard()).isEqualTo("Trung Phụng");
        assertThat(addressComponent.getStreet()).isEqualTo("Số nhà 64, ngách 28, ngõ Tô Tiền");
    }


    @Test
    public void parseCommonAddress_withCountry() {
        String rawAddress = "Lô 28-B2.7 đường Hoàng Sa, Phường Thọ Quang, Quận Sơn Trà, Thành phố Đà Nẵng, Việt Nam";
        AddressComponent addressComponent = StandardAddressParser.parse(rawAddress);

        assertNotNull(addressComponent);

        assertThat(addressComponent.getCountry()).isEqualTo("Việt Nam");
        assertThat(addressComponent.getProvince()).isEqualTo("Đà Nẵng");
        assertThat(addressComponent.getDistrict()).isEqualTo("Sơn Trà");
        assertThat(addressComponent.getWard()).isEqualTo("Thọ Quang");
        assertThat(addressComponent.getStreet()).isEqualTo("Lô 28-B2.7 đường Hoàng Sa");
    }

    @Test
    public void parseCommonAddress_inEnglish() {
        String rawAddress = "My Xuan A2 Industrial Zone, My Xuan Commune, Tan Thanh District, Ba Ria-Vung Tau Province, Viet Nam";
        AddressComponent addressComponent = StandardAddressParser.parse(rawAddress);

        assertNotNull(addressComponent);

        assertThat(addressComponent.getCountry()).isEqualTo("Viet Nam");
        assertThat(addressComponent.getProvince()).isEqualTo("Ba Ria-Vung Tau");
        assertThat(addressComponent.getDistrict()).isEqualTo("Tan Thanh");
        assertThat(addressComponent.getWard()).isEqualTo("My Xuan");
        assertThat(addressComponent.getStreet()).isEqualTo("My Xuan A2 Industrial Zone");
    }

    @Test
    public void parseCommonAddress_nonPrefix() {
        String rawAddress = "Unit 1020, 12th Floor, CMC Building, Duy Tan Street, Dich Vong Hau District, Ha Noi, Viet Nam";
        AddressComponent addressComponent = StandardAddressParser.parse(rawAddress);

        assertNotNull(addressComponent);

        assertThat(addressComponent.getCountry()).isEqualTo("Viet Nam");
        assertThat(addressComponent.getProvince()).isEqualTo("Hà Nội");
        assertThat(addressComponent.getDistrict()).isEqualTo("Dich Vong Hau");
        assertThat(addressComponent.getWard()).isEqualTo(null);
        assertThat(addressComponent.getStreet()).isEqualTo("Unit 1020, 12th Floor, CMC Building, Duy Tan Street");
    }

    @Test
    public void parseCommonAddress_nonPrefix2() {
        String rawAddress = "5th Floor, CMC Building, Duy Tan Street, Dich Vong Hau District, Ha Noi, Viet Nam";
        AddressComponent addressComponent = StandardAddressParser.parse(rawAddress);

        assertNotNull(addressComponent);

        assertThat(addressComponent.getCountry()).isEqualTo("Viet Nam");
        assertThat(addressComponent.getProvince()).isEqualTo("Hà Nội");
        assertThat(addressComponent.getDistrict()).isEqualTo("Dich Vong Hau");
        assertThat(addressComponent.getWard()).isEqualTo(null);
        assertThat(addressComponent.getStreet()).isEqualTo("5th Floor, CMC Building, Duy Tan Street");
    }

    @Test
    public void parseCommonAddress_nonPrefix3() {
        String rawAddress = "CMC Building, Duy Tan Street, Dich Vong Hau District, Ha Noi, Viet Nam";
        AddressComponent addressComponent = StandardAddressParser.parse(rawAddress);

        assertNotNull(addressComponent);

        assertThat(addressComponent.getCountry()).isEqualTo("Viet Nam");
        assertThat(addressComponent.getProvince()).isEqualTo("Hà Nội");
        assertThat(addressComponent.getDistrict()).isEqualTo("Dich Vong Hau");
        assertThat(addressComponent.getWard()).isEqualTo(null);
        assertThat(addressComponent.getStreet()).isEqualTo("CMC Building, Duy Tan Street");
    }
    @Test
    public void parseCommonAddress_nonPrefix_full() {
        String rawAddress = "95 Đường B2 - Phường Tây Thạnh - Quận Tân phú - TP Hồ Chí Minh";
        AddressComponent addressComponent = StandardAddressParser.parse(rawAddress);

        assertNotNull(addressComponent);

        assertThat(addressComponent.getCountry()).isEqualTo(null);
        assertThat(addressComponent.getProvince()).isEqualTo("Hồ Chí Minh");
        assertThat(addressComponent.getDistrict()).isEqualTo("Tân phú");
        assertThat(addressComponent.getWard()).isEqualTo("Tây Thạnh");
        assertThat(addressComponent.getStreet()).isEqualTo("95 Đường B2");
    }


}
