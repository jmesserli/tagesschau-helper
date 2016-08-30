package nu.peg.news.tagesschau.di;

import dagger.Component;
import nu.peg.news.tagesschau.App;

import javax.inject.Singleton;

@Singleton
@Component(modules = DefaultInjectionModule.class)
public interface AppComponent {
    public App app();
}
