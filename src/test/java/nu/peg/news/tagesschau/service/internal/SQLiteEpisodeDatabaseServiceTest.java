package nu.peg.news.tagesschau.service.internal;

import nu.peg.news.tagesschau.model.Episode;
import nu.peg.news.tagesschau.service.ConfigurationService;
import nu.peg.news.tagesschau.service.EpisodeDatabaseService;
import nu.peg.news.tagesschau.test.di.DaggerSQLiteEpisodeDatabaseServiceTestComponent;
import nu.peg.news.tagesschau.test.di.SQLiteEpisodeDatabaseServiceTestComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class SQLiteEpisodeDatabaseServiceTest {

    private SQLiteEpisodeDatabaseServiceTestComponent component;
    private ConfigurationService configService;
    private EpisodeDatabaseService dbService;

    private List<Episode> testEpisodes;

    public SQLiteEpisodeDatabaseServiceTest() {
        this.component = DaggerSQLiteEpisodeDatabaseServiceTestComponent.builder().build();

        configService = component.config();
        configService.set("db.episode.sqlite.file", "test_database.sqlite");
        dbService = component.db();
    }

    @Before
    public void setUp() throws Exception {
        testEpisodes = new ArrayList<>();

        Episode episode1 = new Episode(
                "testing-guid-number-1",
                "Tagesschau vom x.y.z",
                "Wichtige Sachen",
                LocalDateTime.now(),
                Duration.ofSeconds(500),
                "someurl",
                1_000_000L,
                null);

        Episode episode2 = new Episode(
                "testing-guid-number-2",
                "Tagesschau vom z.y.x",
                "Wichtige Sachen und noch mehr",
                LocalDateTime.now().minusDays(1),
                Duration.ofSeconds(120),
                "someslightlydifferenturl",
                2_048_000L,
                "C:\\episode2.mp4");

        testEpisodes.add(episode1);
        testEpisodes.add(episode2);
    }

    @After
    public void tearDown() throws Exception {
        testEpisodes.stream().map(Episode::getGuid).forEach(dbService::deleteByGuid);
    }

    @Test
    public void getAll() throws Exception {
        Boolean addAllTrue = testEpisodes.stream().map(dbService::add).reduce(true, (b1, b2) -> b1 & b2);
        assertThat(addAllTrue).isTrue();

        List<Episode> all = dbService.getAll();
        assertThat(all).hasSize(2);
        assertThat(all).containsAllIn(testEpisodes);
    }

    @Test
    public void getByGuid() throws Exception {
        Episode episode = testEpisodes.get(1);

        assertThat(dbService.add(episode)).isTrue();
        assertThat(dbService.getByGuid(episode.getGuid())).isEqualTo(episode);
    }

    @Test
    public void delete() throws Exception {
        Episode episode = testEpisodes.get(0);

        assertThat(dbService.add(episode)).isTrue();
        assertThat(dbService.getAll()).hasSize(1);
        assertThat(dbService.delete(episode)).isTrue();
        assertThat(dbService.getAll()).isEmpty();
    }

    @Test
    public void deleteByGuid() throws Exception {
        Episode episode = testEpisodes.get(0);

        assertThat(dbService.add(episode)).isTrue();
        assertThat(dbService.getAll()).hasSize(1);
        assertThat(dbService.deleteByGuid(episode.getGuid())).isTrue();
        assertThat(dbService.getAll()).isEmpty();
    }
}