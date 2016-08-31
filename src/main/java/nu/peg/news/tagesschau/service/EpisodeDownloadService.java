package nu.peg.news.tagesschau.service;

import nu.peg.news.tagesschau.model.Episode;

import java.nio.file.Path;
import java.util.concurrent.Future;

public interface EpisodeDownloadService {

    public Future<Path> downloadEpisode(Episode episode, Path targetFolder);

}
