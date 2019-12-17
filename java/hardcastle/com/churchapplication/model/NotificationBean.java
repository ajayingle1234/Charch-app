package hardcastle.com.churchapplication.model;

public class NotificationBean {

    private String notificationTile;
    private String notificationDetails;

    public NotificationBean(String notificationTile, String notificationDetails) {
        this.notificationTile = notificationTile;
        this.notificationDetails = notificationDetails;
    }

    public String getNotificationTile() {
        return notificationTile;
    }

    public void setNotificationTile(String notificationTile) {
        this.notificationTile = notificationTile;
    }

    public String getNotificationDetails() {
        return notificationDetails;
    }

    public void setNotificationDetails(String notificationDetails) {
        this.notificationDetails = notificationDetails;
    }
}
