package nu.peg.news.tagesschau.test.di;

import nu.peg.news.tagesschau.service.ConfigurationService;
import nu.peg.news.tagesschau.service.EpisodeDatabaseService;
import nu.peg.news.tagesschau.service.internal.HashMapConfigurationService;
import nu.peg.news.tagesschau.service.internal.SQLiteEpisodeDatabaseService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SQLiteEpisodeDatabaseServiceTestModule {

    @Provides
    @Singleton
    static ConfigurationService provideConfigurationService(HashMapConfigurationService configurationService) {
        return configurationService;
    }

    @Provides
    static EpisodeDatabaseService provideDatabaseService(SQLiteEpisodeDatabaseService sqliteDatabaseService) {
        return sqliteDatabaseService;
    }

}
