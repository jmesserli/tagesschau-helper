package nu.peg.news.tagesschau.service;

public interface ConfigurationService {

    public String get(String key);

    public String getOr(String key, String value);

    public void set(String key, String value);
}
