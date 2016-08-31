package nu.peg.news.tagesschau.service;

public interface NotifyService {

    public void sendNotification(String targetDevice, String title, String message, String url);

}
