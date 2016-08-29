package nu.peg.news.tagesschau.api.pushbullet;

public interface PushbulletApi {

    public boolean sendNotification(String deviceId, String accessToken, String title, String message, String link);

}
