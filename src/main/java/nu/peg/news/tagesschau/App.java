package nu.peg.news.tagesschau;

import nu.peg.news.tagesschau.di.AppComponent;
import nu.peg.news.tagesschau.di.DaggerAppComponent;
import nu.peg.news.tagesschau.model.Episode;
import nu.peg.news.tagesschau.service.ConfigurationService;
import nu.peg.news.tagesschau.service.EpisodeDatabaseService;
import nu.peg.news.tagesschau.service.EpisodeDownloadService;
import nu.peg.news.tagesschau.service.EpisodeService;

import javax.inject.Inject;
import java.util.List;

public class App {

    private EpisodeService episodeService;
    private EpisodeDownloadService downloadService;
    private EpisodeDatabaseService databaseService;
    private ConfigurationService configService;

    @Inject
    public App(EpisodeService episodeService, EpisodeDownloadService downloadService, EpisodeDatabaseService databaseService, ConfigurationService configService) {
        this.episodeService = episodeService;
        this.downloadService = downloadService;
        this.databaseService = databaseService;
        this.configService = configService;
    }

    public void startWatchingEpisodes() {
        List<Episode> episodes = episodeService.getLatestEpisodes(5);


    }

    public static void main(String[] args) {
        AppComponent appComponent = DaggerAppComponent.builder().build();
        App app = appComponent.app();

        app.startWatchingEpisodes();
    }
}
