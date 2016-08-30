package nu.peg.news.tagesschau.service.internal;

import nu.peg.news.tagesschau.model.Episode;
import nu.peg.news.tagesschau.service.ConfigurationService;
import nu.peg.news.tagesschau.service.EpisodeDatabaseService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SQLiteEpisodeDatabaseService implements EpisodeDatabaseService {

    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_DATE_TIME;
    private Connection connection;

    @Inject
    public SQLiteEpisodeDatabaseService(ConfigurationService configService) {
        String dbFile = configService.get("db.episode.sqlite.file");
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", dbFile));

            initializeTable();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initializeTable() throws SQLException {
        PreparedStatement createStmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Episode (guid TEXT PRIMARY KEY NOT NULL,  title TEXT NOT NULL,  summary TEXT NOT NULL,  publisheddate TEXT NOT NULL,  length NUMERIC NOT NULL,  podcasturl TEXT NOT NULL,  podcastsize NUMERIC NOT NULL)");
        createStmt.execute();
    }

    private List<Episode> resultSetToEpisode(ResultSet rs) throws SQLException {
        List<Episode> episodes = new ArrayList<>();

        while (rs.next()) {
            String guid = rs.getString("guid");
            String title = rs.getString("title");
            String summary = rs.getString("summary");
            String publisheddate = rs.getString("publisheddate");
            long length = rs.getLong("length");
            String podcasturl = rs.getString("podcasturl");
            long podcastsize = rs.getLong("podcastsize");

            LocalDateTime parsedPublishedDate = LocalDateTime.parse(publisheddate, dateTimeFormat);
            Duration parsedLength = Duration.ofSeconds(length);

            Episode episode = new Episode(guid, title, summary, parsedPublishedDate, parsedLength, podcasturl, podcastsize);
            episodes.add(episode);
        }

        return episodes;
    }

    @Override
    public List<Episode> getAll() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Episode");
            ResultSet resultSet = stmt.executeQuery();

            return resultSetToEpisode(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Episode getByGuid(String guid) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Episode WHERE guid = ?");
            stmt.setString(1, guid);
            ResultSet resultSet = stmt.executeQuery();

            List<Episode> episodes = resultSetToEpisode(resultSet);
            return episodes.size() > 0 ? episodes.get(0) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean add(Episode episode) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Episode (guid, title, summary, publisheddate, length, podcasturl, podcastsize) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, episode.getGuid());
            stmt.setString(2, episode.getTitle());
            stmt.setString(3, episode.getSummary());
            stmt.setString(4, episode.getPublishedDate().format(dateTimeFormat));
            stmt.setLong(5, episode.getLength().getSeconds());
            stmt.setString(6, episode.getPodcastUrl());
            stmt.setLong(7, episode.getPodcastSizeBytes());

            return stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Episode episode) {
        return deleteByGuid(episode.getGuid());
    }

    @Override
    public boolean deleteByGuid(String guid) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Episode WHERE guid = ?");
            stmt.setString(1, guid);
            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
