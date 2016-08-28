package nu.peg.news.tagesschau.service;

import java.nio.file.Path;
import java.util.concurrent.Future;

public interface EpisodeDownloadService {

    public Future<Path> downloadEpisode(String episodeId, Path targetFolder);

}
