package nu.peg.news.tagesschau.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Episode {

    private String guid;
    private String title;
    private String summary;
    private LocalDateTime publishedDate;
    private Duration length;
    private String podcastUrl;
    private long podcastSizeBytes;

    public Episode() {
    }

    public Episode(String guid, String title, String summary, LocalDateTime publishedDate, Duration length, String podcastUrl, long podcastSizeBytes) {
        this.guid = guid;
        this.title = title;
        this.summary = summary;
        this.publishedDate = publishedDate;
        this.length = length;
        this.podcastUrl = podcastUrl;
        this.podcastSizeBytes = podcastSizeBytes;
    }

    //region Getters and setters
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Duration getLength() {
        return length;
    }

    public void setLength(Duration length) {
        this.length = length;
    }

    public String getPodcastUrl() {
        return podcastUrl;
    }

    public void setPodcastUrl(String podcastUrl) {
        this.podcastUrl = podcastUrl;
    }

    public long getPodcastSizeBytes() {
        return podcastSizeBytes;
    }

    public void setPodcastSizeBytes(long podcastSizeBytes) {
        this.podcastSizeBytes = podcastSizeBytes;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }
    //endregion
}
