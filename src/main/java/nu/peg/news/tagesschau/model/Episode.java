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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Episode)) return false;

        Episode episode = (Episode) o;

        if (getPodcastSizeBytes() != episode.getPodcastSizeBytes()) return false;
        if (getGuid() != null ? !getGuid().equals(episode.getGuid()) : episode.getGuid() != null)
            return false;
        if (getTitle() != null ? !getTitle().equals(episode.getTitle()) : episode.getTitle() != null)
            return false;
        if (getSummary() != null ? !getSummary().equals(episode.getSummary()) : episode.getSummary() != null)
            return false;
        if (getPublishedDate() != null ? !getPublishedDate().equals(episode.getPublishedDate()) : episode.getPublishedDate() != null)
            return false;
        if (getLength() != null ? !getLength().equals(episode.getLength()) : episode.getLength() != null)
            return false;
        return getPodcastUrl() != null ? getPodcastUrl().equals(episode.getPodcastUrl()) : episode.getPodcastUrl() == null;
    }

    @Override
    public int hashCode() {
        int result = getGuid() != null ? getGuid().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getSummary() != null ? getSummary().hashCode() : 0);
        result = 31 * result + (getPublishedDate() != null ? getPublishedDate().hashCode() : 0);
        result = 31 * result + (getLength() != null ? getLength().hashCode() : 0);
        result = 31 * result + (getPodcastUrl() != null ? getPodcastUrl().hashCode() : 0);
        result = 31 * result + (int) (getPodcastSizeBytes() ^ (getPodcastSizeBytes() >>> 32));
        return result;
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
