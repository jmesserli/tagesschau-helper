package nu.peg.news.tagesschau.service.internal;

import nu.peg.news.tagesschau.service.ConfigurationService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class HashMapConfigurationService implements ConfigurationService {

    private Map<String, String> configMap;

    @Inject
    public HashMapConfigurationService() {
        configMap = new HashMap<>();
    }

    @Override
    public String get(String key) {
        return getOr(key, null);
    }

    @Override
    public String getOr(String key, String value) {
        return configMap.getOrDefault(key, value);
    }

    @Override
    public void set(String key, String value) {
        configMap.put(key, value);
    }
}
