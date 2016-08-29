package nu.peg.news.tagesschau;

import nu.peg.news.tagesschau.di.AppComponent;
import nu.peg.news.tagesschau.di.DaggerAppComponent;
import nu.peg.news.tagesschau.model.Episode;
import nu.peg.news.tagesschau.service.EpisodeService;

import java.util.List;

import javax.inject.Inject;

public class App {

    private EpisodeService episodeService;

    @Inject
    public App(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    public void startWatchingEpisodes() {
        while (true) {
            List<Episode> episodes = episodeService.getLatestEpisodes(5);
            break;
        }
    }

    public static void main(String[] args) {
        AppComponent appComponent = DaggerAppComponent.builder().build();
        App app = appComponent.app();

        app.startWatchingEpisodes();
    }
}
