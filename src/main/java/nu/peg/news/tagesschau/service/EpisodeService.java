package nu.peg.news.tagesschau.service;

import nu.peg.news.tagesschau.model.Episode;

import java.util.List;

public interface EpisodeService {

    public List<Episode> getLatestEpisodes(int episodeCount);

    public Episode getNewestEpisode();

}
