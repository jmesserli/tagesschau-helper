package nu.peg.news.tagesschau.service.internal;

import nu.peg.news.tagesschau.api.pushbullet.PushbulletApi;
import nu.peg.news.tagesschau.service.ConfigurationService;
import nu.peg.news.tagesschau.service.NotifyService;

import javax.inject.Inject;

public class PushBulletNotifyService implements NotifyService {

    private PushbulletApi pushbulletApi;
    private ConfigurationService configService;

    @Inject
    public PushBulletNotifyService(PushbulletApi pushbulletApi, ConfigurationService configService) {
        this.pushbulletApi = pushbulletApi;
        this.configService = configService;
    }

    @Override
    public void sendNotification(String targetDevice, String title, String message, String url) {
        pushbulletApi.sendNotification(targetDevice, configService.get("api.pushbullet.access.token"), title, message, url);
    }
}
