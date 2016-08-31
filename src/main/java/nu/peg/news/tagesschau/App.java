package nu.peg.news.tagesschau;

import nu.peg.news.tagesschau.di.AppComponent;
import nu.peg.news.tagesschau.di.DaggerAppComponent;
import nu.peg.news.tagesschau.model.Episode;
import nu.peg.news.tagesschau.service.ConfigurationService;
import nu.peg.news.tagesschau.service.EpisodeDatabaseService;
import nu.peg.news.tagesschau.service.EpisodeDownloadService;
import nu.peg.news.tagesschau.service.EpisodeService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class App {
    private EpisodeService episodeService;
    private EpisodeDatabaseService databaseService;
    private EpisodeDownloadService downloadService;
    private ConfigurationService configService;

    @Inject
    public App(EpisodeService episodeService, EpisodeDatabaseService databaseService, EpisodeDownloadService downloadService, ConfigurationService configService) {
        this.episodeService = episodeService;
        this.databaseService = databaseService;
        this.downloadService = downloadService;
        this.configService = configService;
    }

    public static void main(String[] args) {
        AppComponent appComponent = DaggerAppComponent.builder().build();
        App app = appComponent.app();

        app.startWatchingEpisodes();
    }

    public void startWatchingEpisodes() {
        int timeout = Integer.parseInt(configService.getOr("episodeservice.timer", "900"));
        Path targetFolder = Paths.get(configService.getOr("episodes.target.folder", "./episodes"));

        while (true) {
            Episode episode = episodeService.getNewestEpisode();

            // Check if we have the episode already
            if (databaseService.getByGuid(episode.getGuid()) == null) {
                Future<Path> downloadedEpisodePathFuture = downloadService.downloadEpisode(episode, targetFolder);
                System.out.printf("Downloading Episode: %s (%d MB)%n", episode.getTitle(), episode.getPodcastSizeBytes() / (1048576));

                long startTime = System.currentTimeMillis();
                Path downloadedEpisodePath = null;
                try {
                    downloadedEpisodePath = downloadedEpisodePathFuture.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                long endTime = System.currentTimeMillis();
                System.out.printf("Download of Episode \"%s\" finished in %d seconds.%n", episode.getTitle(), (endTime - startTime) / 1000);

                episode.setLocalPath(downloadedEpisodePath.toAbsolutePath().toString());
                databaseService.add(episode);
            }

            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
