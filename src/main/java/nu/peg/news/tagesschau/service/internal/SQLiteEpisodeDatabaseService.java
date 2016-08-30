package nu.peg.news.tagesschau.service.internal;

import nu.peg.news.tagesschau.model.Episode;
import nu.peg.news.tagesschau.service.ConfigurationService;
import nu.peg.news.tagesschau.service.EpisodeDatabaseService;

import javax.inject.Inject;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SQLiteEpisodeDatabaseService implements EpisodeDatabaseService {

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

            LocalDateTime parsedPublishedDate = LocalDateTime.parse(publisheddate, DateTimeFormatter.ISO_DATE_TIME);
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
        return null;
    }

    @Override
    public boolean add(Episode episode) {
        return false;
    }

    @Override
    public boolean delete(Episode episode) {
        return false;
    }

    @Override
    public boolean deleteByGuid(String guid) {
        return false;
    }
}
