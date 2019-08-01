package com.tue.spark.address;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public final class AddressConfiguration {
    private static final String RESOURCE = "address.yaml";

    private static AddressConfiguration INSTANCE = null;

    private List<String> countries;
    private Map<String, Map<String, List<String>>> provinces;
    private List<String> wardKeywords;
    private List<String> provinceKeywords;
    private List<String> districtKeywords;
    private List<String> streetKeywords;

    @SuppressWarnings("unchecked")
    private static <T> T evaluateMap(Map map, String path) {
        String[] paths = path.split("\\.");
        Map current = map;
        for (int i = 0; i < paths.length - 1; i++) {
            current = (Map) current.get(paths[i]);
        }
        return (T) current.get(paths[paths.length - 1]);
    }

    public static AddressConfiguration getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        INSTANCE = load();
        return INSTANCE;
    }

    private static AddressConfiguration load() {
        log.info("LOADING... AddressConfiguration from yaml file={}", RESOURCE);

        Yaml yaml = new Yaml();
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(RESOURCE);
        Map<String, Object> configuration = yaml.load(inputStream);

        AddressConfiguration addressConfiguration = new AddressConfiguration();
        addressConfiguration.countries = evaluateMap(configuration, "address.name.countries");
        addressConfiguration.provinces = evaluateMap(configuration, "address.name.provinces");
        addressConfiguration.wardKeywords = evaluateMap(configuration, "address.keyword.ward");
        addressConfiguration.provinceKeywords = evaluateMap(configuration, "address.keyword.province");
        addressConfiguration.districtKeywords = evaluateMap(configuration, "address.keyword.district");
        addressConfiguration.streetKeywords = evaluateMap(configuration, "address.keyword.street");
        return addressConfiguration;
    }
}
