package nu.peg.news.tagesschau.di;

import nu.peg.news.tagesschau.App;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DefaultInjectionModule.class)
public interface AppComponent {
    public App app();
}
