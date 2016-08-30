package nu.peg.news.tagesschau.test.di;

import nu.peg.news.tagesschau.service.ConfigurationService;
import nu.peg.news.tagesschau.service.EpisodeDatabaseService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = SQLiteEpisodeDatabaseServiceTestModule.class)
public interface SQLiteEpisodeDatabaseServiceTestComponent {
    public EpisodeDatabaseService db();

    public ConfigurationService config();
}
