package nu.peg.news.tagesschau.di;

import nu.peg.news.tagesschau.api.pushbullet.PushbulletApi;
import nu.peg.news.tagesschau.api.pushbullet.internal.DefaultPushbulletApi;
import nu.peg.news.tagesschau.service.ConfigurationService;
import nu.peg.news.tagesschau.service.EpisodeDownloadService;
import nu.peg.news.tagesschau.service.EpisodeService;
import nu.peg.news.tagesschau.service.NotifyService;
import nu.peg.news.tagesschau.service.internal.ResourcePropertiesConfigurationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DefaultInjectionModule {

    @Provides
    static NotifyService provideNotifyService() {
        return null;
    }

    @Provides
    static EpisodeService provideEpisodeService() {
        return null;
    }

    @Provides
    static EpisodeDownloadService provideEpisodeDownloadService() {
        return null;
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
}
