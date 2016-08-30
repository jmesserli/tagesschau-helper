package nu.peg.news.tagesschau.service;

import nu.peg.news.tagesschau.model.Episode;

import java.util.List;

public interface EpisodeDatabaseService {

    public List<Episode> getAll();

    public Episode getByGuid(String guid);

    public boolean add(Episode episode);

    public boolean delete(Episode episode);

    public boolean deleteByGuid(String guid);

}
