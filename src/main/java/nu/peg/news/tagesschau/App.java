package nu.peg.news.tagesschau;

import nu.peg.news.tagesschau.di.AppComponent;
import nu.peg.news.tagesschau.di.DaggerAppComponent;
import nu.peg.news.tagesschau.model.Episode;
import nu.peg.news.tagesschau.service.ConfigurationService;
import nu.peg.news.tagesschau.service.EpisodeDatabaseService;
import nu.peg.news.tagesschau.service.EpisodeDownloadService;
import nu.peg.news.tagesschau.service.EpisodeService;
import nu.peg.news.tagesschau.service.NotifyService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class App {
    private EpisodeService episodeService;
    private EpisodeDatabaseService databaseService;
    private EpisodeDownloadService downloadService;
    private NotifyService notifyService;
    private ConfigurationService configService;

    @Inject
    public App(EpisodeService episodeService, EpisodeDatabaseService databaseService, EpisodeDownloadService downloadService, NotifyService notifyService, ConfigurationService configService) {
        this.episodeService = episodeService;
        this.databaseService = databaseService;
        this.downloadService = downloadService;
        this.notifyService = notifyService;
        this.configService = configService;
    }

    public static void main(String[] args) {
        AppComponent appComponent = DaggerAppComponent.builder().build();
        App app = appComponent.app();

        app.startWatchingEpisodes();
    }

    public void startWatchingEpisodes() {
        long timeout = Long.parseLong(configService.getOr("episodeservice.timer", "900")) * 1000L;
        Path targetFolder = Paths.get(configService.getOr("episodes.target.folder", "./episodes"));

        while (true) {
            Episode episode = episodeService.getNewestEpisode();

            // Check if we have the episode already
            if (databaseService.getByGuid(episode.getGuid()) == null) {
                Future<Path> downloadedEpisodePathFuture = downloadService.downloadEpisode(episode, targetFolder);
                System.out.printf("%s: Downloading Episode: %s (%d MB)%n", LocalDateTime.now(), episode.getTitle(), episode.getPodcastSizeBytes() / (1048576));

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
                System.out.printf("%s: Download of Episode \"%s\" finished in %d seconds.%n", LocalDateTime.now(), episode.getTitle(), (endTime - startTime) / 1000);

                episode.setLocalPath(downloadedEpisodePath.toAbsolutePath().toString());
                databaseService.add(episode);

                notifyService.sendNotification(configService.get("api.pushbullet.target.id"), "Neue Tagesschau verfügbar", String.format("Die Episode \"%s\" ist gerade erschienen.\nInhalt: %s", episode.getTitle(), episode.getSummary()), String.format("%s%s", configService.get("notification.base.url"), targetFolder.toAbsolutePath().relativize(Paths.get(episode.getLocalPath()))));
            } else {
                System.out.printf("%s: No new episodes found.%n", LocalDateTime.now());
            }

            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
