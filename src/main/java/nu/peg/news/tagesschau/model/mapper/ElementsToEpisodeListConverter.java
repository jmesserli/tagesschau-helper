package nu.peg.news.tagesschau.model.mapper;

import nu.peg.news.tagesschau.model.Episode;

import org.jsoup.select.Elements;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class ElementsToEpisodeListConverter implements Converter<Elements, List<Episode>> {

    @Inject
    public ElementsToEpisodeListConverter() {
    }

    @Override
    public List<Episode> convert(Elements input) {
        return input.stream().map(element -> {
            DateTimeFormatter publishedFormatter = DateTimeFormatter.ofPattern("ccc, dd MMM yyyy HH:mm:ss x", Locale.ENGLISH);
            return new Episode(
                    element.select("guid").text(),
                    element.select("title").text(),
                    element.select("itunes|summary").text(),
                    LocalDateTime.parse(element.select("pubDate").text(), publishedFormatter),
                    Duration.ofSeconds(Long.parseLong(element.select("itunes|duration").text())),
                    element.select("enclosure").attr("url"),
                    Long.parseLong(element.select("enclosure").attr("length")),
                    null);
        }).collect(Collectors.toList());
    }
}
