package com.tue.spark.naming;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
public class CompanyNameParserTest {
    private CompanyNameParser companyNameParser = new CompanyNameParser();

    @DataProvider
    public static Object[][] companyProvider() {
        return new Object[][]{
                {"Công Ty TNHH Văn Kim", "Văn Kim", "TNHH"},
                {"Công ty TNHH Jet Studio Việt Nam", "Jet Studio", "TNHH"},
                {"Công Ty TNHH Thiên Minh Kỹ Thuật", "Thiên Minh Kỹ Thuật", "TNHH"},
                {"Công Ty TNHH Buhler Việt Nam", "Buhler", "TNHH"},
                {"Công Ty TNHH VSL Việt Nam", "VSL", "TNHH"},
                {"Công ty TNHH Chan Chem", "Chan Chem", "TNHH"},
                {"Công Ty TNHH Schenker Việt Nam", "Schenker", "TNHH"},
                {"Công ty TNHH TM Hiệp Minh Phát", "Hiệp Minh Phát", "TNHH"},
                {"Công Ty Wilo Việt Nam", "Wilo", null},
                {"Công Ty TNHH Koolman Việt Nam", "Koolman", "TNHH"},
                {"Công Ty Cổ Phần Thương Mại Và Dịch Vụ Cảng Cái Lân", "Cảng Cái Lân", "CP"},
                {"Công Ty Cổ Phần Thủy Sản Khu Vực 1", "Thủy Sản Khu Vực 1", "CP"},
                {"Công ty cổ phần kiến trúc gỗ cao bằng", "kiến trúc gỗ cao bằng", "CP"},
        };
    }

    @Test
    @UseDataProvider("companyProvider")
    public void parse_withFullInfo(String rawCompanyName, String mainName, String type) {
        CompanyNameComponent companyNameComponent = companyNameParser.parse(rawCompanyName);
        assertThat(companyNameComponent).isNotNull();
        assertThat(companyNameComponent.getName()).isEqualTo(mainName);
        assertThat(companyNameComponent.getType()).isEqualTo(type);
    }
}
