package nu.peg.news.tagesschau;

import nu.peg.news.tagesschau.di.AppComponent;
import nu.peg.news.tagesschau.di.DaggerAppComponent;
import nu.peg.news.tagesschau.model.Episode;
import nu.peg.news.tagesschau.service.ConfigurationService;
import nu.peg.news.tagesschau.service.EpisodeDatabaseService;
import nu.peg.news.tagesschau.service.EpisodeService;

import java.util.List;

import javax.inject.Inject;

public class App {

    private EpisodeService episodeService;
    private EpisodeDatabaseService databaseService;
    private ConfigurationService configService;

    @Inject
    public App(EpisodeService episodeService, EpisodeDatabaseService databaseService, ConfigurationService configService) {
        this.episodeService = episodeService;
        this.databaseService = databaseService;
        this.configService = configService;
    }

    public static void main(String[] args) {
        AppComponent appComponent = DaggerAppComponent.builder().build();
        App app = appComponent.app();

        app.startWatchingEpisodes();
    }

    public void startWatchingEpisodes() {
        List<Episode> episodes = episodeService.getLatestEpisodes(5);
    }
}
