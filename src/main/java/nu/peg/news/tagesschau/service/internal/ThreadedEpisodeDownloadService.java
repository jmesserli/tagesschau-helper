package nu.peg.news.tagesschau.service.internal;

import nu.peg.news.tagesschau.model.Episode;
import nu.peg.news.tagesschau.service.EpisodeDownloadService;
import nu.peg.news.tagesschau.util.Utils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class ThreadedEpisodeDownloadService implements EpisodeDownloadService {

    private static final ExecutorService pool = Executors.newFixedThreadPool(4);

    @Inject
    public ThreadedEpisodeDownloadService() {
    }

    @Override
    public Future<Path> downloadEpisode(Episode episode, Path targetFolder) {
        String podcastUrl = episode.getPodcastUrl();
        String filename = String.format("%s.mp4", Utils.sha256(String.format("%s%s%s%s", episode.getGuid(), episode.getTitle(), episode.getSummary(), episode.getPublishedDate().toString())));

        return pool.submit(new DownloadRunnable(podcastUrl, targetFolder.resolve(Paths.get(filename))));
    }

    private class DownloadRunnable implements Callable<Path> {

        private String downloadUrl;
        private Path targetFile;

        public DownloadRunnable(String downloadUrl, Path targetFile) {
            this.downloadUrl = downloadUrl;
            this.targetFile = targetFile;
        }

        @Override
        public Path call() throws Exception {
            URL downloadUrl = new URL(this.downloadUrl);

            try (InputStream is = downloadUrl.openStream(); ReadableByteChannel inChannel = Channels.newChannel(is); FileOutputStream fos = new FileOutputStream(targetFile.toAbsolutePath().toFile())) {
                FileChannel outChannel = fos.getChannel();
                long count;
                do {
                    count = outChannel.transferFrom(inChannel, 0, Long.MAX_VALUE);
                } while (count > 0);
            }
            return targetFile;
        }
    }
}
