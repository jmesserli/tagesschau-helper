package nu.peg.news.tagesschau.service.internal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import nu.peg.news.tagesschau.model.Episode;
import nu.peg.news.tagesschau.model.mapper.Converter;
import nu.peg.news.tagesschau.service.EpisodeService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class FeedEpisodeService implements EpisodeService {

    private Converter<Elements, List<Episode>> elementsToEpisodesConverter;

    @Inject
    public FeedEpisodeService(Converter<Elements, List<Episode>> elementsToEpisodesConverter) {
        this.elementsToEpisodesConverter = elementsToEpisodesConverter;
    }

    @Override
    public List<Episode> getLatestEpisodes(int limit) {

        HttpResponse<String> stringResponse = null;
        try {
            stringResponse = Unirest.get("http://www.srf.ch/feed/podcast/hd/ff969c14-c5a7-44ab-ab72-14d4c9e427a9.xml")
                                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (stringResponse.getStatus() != 200) {
            return null;
        }

        Document document = Jsoup.parse(stringResponse.getBody(), "", Parser.xmlParser());
        Elements items = document.select("rss channel item");

        List<Episode> episodeList = elementsToEpisodesConverter.convert(items);
        return episodeList.stream().limit(limit).collect(Collectors.toList());
    }

    @Override
    public Episode getNewestEpisode() {
        return getLatestEpisodes(1).get(0);
    }
}
