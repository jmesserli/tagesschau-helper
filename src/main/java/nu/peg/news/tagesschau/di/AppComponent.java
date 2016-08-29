package nu.peg.news.tagesschau.di;

import nu.peg.news.tagesschau.App;

import dagger.Component;

@Component(modules = DefaultInjectionModule.class)
public interface AppComponent {
    public App app();
}
