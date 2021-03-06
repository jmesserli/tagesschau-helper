package nu.peg.news.tagesschau.di;

import nu.peg.news.tagesschau.api.pushbullet.PushbulletApi;
import nu.peg.news.tagesschau.api.pushbullet.internal.DefaultPushbulletApi;
import nu.peg.news.tagesschau.model.Episode;
import nu.peg.news.tagesschau.model.mapper.Converter;
import nu.peg.news.tagesschau.model.mapper.ElementsToEpisodeListConverter;
import nu.peg.news.tagesschau.service.ConfigurationService;
import nu.peg.news.tagesschau.service.EpisodeDatabaseService;
import nu.peg.news.tagesschau.service.EpisodeDownloadService;
import nu.peg.news.tagesschau.service.EpisodeService;
import nu.peg.news.tagesschau.service.NotifyService;
import nu.peg.news.tagesschau.service.internal.FeedEpisodeService;
import nu.peg.news.tagesschau.service.internal.PushBulletNotifyService;
import nu.peg.news.tagesschau.service.internal.ResourcePropertiesConfigurationService;
import nu.peg.news.tagesschau.service.internal.SQLiteEpisodeDatabaseService;
import nu.peg.news.tagesschau.service.internal.ThreadedEpisodeDownloadService;

import org.jsoup.select.Elements;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DefaultInjectionModule {

    @Provides
    static NotifyService provideNotifyService(PushBulletNotifyService pushBulletNotifyService) {
        return pushBulletNotifyService;
    }

    @Provides
    static EpisodeService provideEpisodeService(FeedEpisodeService feedEpisodeService) {
        return feedEpisodeService;
    }

    @Provides
    static EpisodeDownloadService provideEpisodeDownloadService(ThreadedEpisodeDownloadService threadedEpisodeDownloadService) {
        return threadedEpisodeDownloadService;
    }

    @Provides
    @Singleton
    static EpisodeDatabaseService provideEpisodeDatabaseService(SQLiteEpisodeDatabaseService episodeDatabaseService) {
        return episodeDatabaseService;
    }

    @Provides
    static PushbulletApi providePushbulletApi(DefaultPushbulletApi defaultPushbulletApi) {
        return defaultPushbulletApi;
    }

    @Provides
    @Singleton
    static ConfigurationService provideConfigurationService(ResourcePropertiesConfigurationService resourcePropertiesConfigurationService) {
        return resourcePropertiesConfigurationService;
    }

    @Provides
    static Converter<Elements, List<Episode>> provideElementsToEpisodeListConverter(ElementsToEpisodeListConverter converter) {
        return converter;
    }
}
