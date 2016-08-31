package nu.peg.news.tagesschau.api.pushbullet.internal;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import nu.peg.news.tagesschau.api.pushbullet.PushbulletApi;

public class DefaultPushbulletApi implements PushbulletApi {

    private Gson gson;

    public DefaultPushbulletApi() {
        gson = new Gson();
    }

    @Override
    public boolean sendNotification(String deviceId, String accessToken, String title, String message, String link) {
        try {
            Unirest.post("https://api.pushbullet.com/v2/pushes")
                   .header("Access-Token", accessToken)
                   .header("Content-type", "application/json")
                   .body(gson.toJson(new LinkMessage(deviceId, title, message, link)))
                   .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static class LinkMessage {
        @SerializedName("device_iden")
        private String deviceIdentifier;

        private String type = "link";
        private String title;
        private String body;
        private String url;

        public LinkMessage(String deviceIdentifier, String title, String body, String url) {
            this.deviceIdentifier = deviceIdentifier;
            this.title = title;
            this.body = body;
            this.url = url;
        }

        public String getDeviceIdentifier() {
            return deviceIdentifier;
        }

        public void setDeviceIdentifier(String deviceIdentifier) {
            this.deviceIdentifier = deviceIdentifier;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
