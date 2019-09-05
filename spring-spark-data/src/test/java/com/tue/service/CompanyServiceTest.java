package com.tue.service;

import com.tue.company.model.Address;
import com.tue.company.model.Company;
import com.tue.company.model.Description;
import com.tue.company.model.GeoDirectory;
import com.tue.company.model.LogData;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class CompanyServiceTest {
    @Autowired
    private CompanyService companyService;

    @Test
    public void findOneCompany() {
        Company company = companyService.getCompany("02774e64b4d6fc472ea77c7ade25d2abe609070b");
        log.info("company={}", company);
    }

    @Test
    public void getCompanyWithScroll() {
        AtomicInteger atomicInteger = new AtomicInteger();
        QueryBuilder queryBuilder = matchAllQuery();// existsQuery("website");// matchQuery("website", "\\*");
        companyService.fetchAllWithScroll(queryBuilder, 1000, companyPage -> {
            log.info("page={}", companyPage);
            companyPage.forEach(company -> log.info("{}. Company={}-{}", atomicInteger.incrementAndGet(), company.getId(), company.getName()));
        });
    }

    @Test
    public void getErrorCompanies() {
        List<Company> companies = companyService.getErrorCompanies();

        companies.forEach(company -> {
            log.info("error={}/{}/{}", company.getId(), company.getTaxId(), company.getGeoDirectory().toStringNonNull());
        });
        log.info("total={}", companies.size());
    }

    @Test
    public void getMigrationCompanies() {
        List<Company> companies = companyService.getMigrationCompanies();
        companies.forEach(company -> {
            log.info("Migration company={} / {}", company.getId(), company.getGeoDirectory().toStringNonNull());
        });
        log.info("total={}", companies.size());
    }

    @Test
    public void updateCompany() {
        String id="ba8c3c9f2638d71757487dbae54d535ff974d038";
//        companyService.updateCompany(id, "geovalue");
        companyService.removeCompanyField(id, "_geodirectory2");
    }

    @Test
    public void saveFullCompany() {
        String companyId = "123";
        Company company = Company.builder()
                .id(companyId)
                .dob(Instant.now())
                .email("ll@gmail.com")
                .website("http://www.hdsteel.com.vn")
                .mobile("12369874")
                .category("an category")
                .ceo("an ceo")
                .ceoAddress("ceo_address")
                .fax("15963258")
                .name("company name")
                .nameEn("company name in english")
                .address(Address.builder()
                        .address("full address")
                        .country("vietnam")
                        .district("Tan phu")
                        .location(new Address.GeoPoint(10.11D, 20.30D))
                        .build())
                .description(Description.builder()
                        .generalDescription("general description")
                        .ratingText("good")
                        .rating(4)
                        .logo("http://logo.com/image/1")
                        .businessRegistration("business registration")
                        .businessRegistrationDate(Instant.now())
                        .businessRegistrationPlace("registration place")
                        .numberOfEmployee(5)
//                        .images(Lists.newArrayList("http://image.com/1.jpg", "http://image.com/2.jpg"))
                        .build())
                .logData(LogData.builder()
                        .address("log address")
                        .geocoding("geocoding google")
                        .urlRefThongTinCongTy("http://thongtincongty.com")
                        .urlRefYellowPages("http://yellowpage.com")
                        .build())
                .geoDirectory(GeoDirectory.builder()
                        .error(400)
                        .guid("guuid-1234")
                        .postDate(Instant.now())
                        .postId("12")
                        .errorData(GeoDirectory.ErrorData.builder()
                                .geodirContact1("contact 1")
                                .geodirContact2("contact 2")
                                .geodirContact3("contact 3")
                                .geodirContact4("contact 4")
                                .geodirEmail1("email 1")
                                .geodirEmail2("email 2")
                                .geodirEmail3("email 3")
                                .geodirTaxCode("tax code")
                                .geodirWebsite("website")
                                .postLat("lat")
                                .postLon("long")
                                .vi("vietnamese")
                                .build())
                        .build())
                .build();

        companyService.saveCompany(company);

        Company companyGet = companyService.getCompany(companyId);
        // company
        assertThat(companyGet).isNotNull();
        assertThat(companyGet.getId()).isEqualTo(company.getId());
        assertThat(companyGet.getEmail()).isEqualTo(company.getEmail());
        assertThat(companyGet.getWebsite()).isEqualTo(company.getWebsite());
        assertThat(companyGet.getDob()).isEqualTo(company.getDob());
        assertThat(companyGet.getMobile()).isEqualTo(company.getMobile());
        assertThat(companyGet.getCategory()).isEqualTo(company.getCategory());
        assertThat(companyGet.getCeo()).isEqualTo(company.getCeo());
        assertThat(companyGet.getCeoAddress()).isEqualTo(company.getCeoAddress());
        assertThat(companyGet.getFax()).isEqualTo(company.getFax());
        assertThat(companyGet.getName()).isEqualTo(company.getName());
        assertThat(companyGet.getNameEn()).isEqualTo(company.getNameEn());

        // address

        // description
        assertThat(companyGet.getDescription().getLogo()).isEqualTo(company.getDescription().getLogo());
        assertThat(companyGet.getDescription().getGeneralDescription()).isEqualTo(company.getDescription().getGeneralDescription());

        // log data
        assertThat(companyGet.getLogData()).isNotNull();

        // geodirectory
        assertThat(companyGet.getGeoDirectory()).isNotNull();


        // TEARDOWN cleanup data
        companyService.deleteCompany(companyId);
        assertThat(companyService.getCompany(companyId)).isNull();

    }
}
