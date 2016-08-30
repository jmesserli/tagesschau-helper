package nu.peg.news.tagesschau.service.internal;

import nu.peg.news.tagesschau.service.ConfigurationService;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Properties;

public class ResourcePropertiesConfigurationService implements ConfigurationService {

    private Properties properties;

    @Inject
    public ResourcePropertiesConfigurationService() {
        try {
            properties = new Properties();
            properties.load(getClass().getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("config.properties is not available to the class loader as resource", e);
        }
    }

    @Override
    public String get(String key) {
        return properties.getProperty(key);
    }

    @Override
    public String getOr(String key, String value) {
        return properties.getProperty(key, value);
    }

    @Override
    public void set(String key, String value) {
        properties.setProperty(key, value);
    }
}
