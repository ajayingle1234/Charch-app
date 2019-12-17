package hardcastle.com.churchapplication.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationResponseBean {


    @SerializedName("STATUS")
    @Expose
    private Integer sTATUS;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("DATA")
    @Expose
    private List<NotificationBeanList> notificationBeanList = null;

    public Integer getSTATUS() {
        return sTATUS;
    }

    public void setSTATUS(Integer sTATUS) {
        this.sTATUS = sTATUS;
    }

    public String getMESSAGE() {
        return mESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }

    public List<NotificationBeanList> getNotificationBeanList() {
        return notificationBeanList;
    }

    public void setNotificationBeanList(List<NotificationBeanList> notificationBeanList) {
        this.notificationBeanList = notificationBeanList;
    }

    public static class NotificationBeanList {

        @SerializedName("notification")
        @Expose
        private String notification;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("now_or_schedule")
        @Expose
        private String nowOrSchedule;
        @SerializedName("notification_date")
        @Expose
        private String notificationDate;
        @SerializedName("notification_time")
        @Expose
        private String notificationTime;

        public String getNotification() {
            return notification;
        }

        public void setNotification(String notification) {
            this.notification = notification;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getNowOrSchedule() {
            return nowOrSchedule;
        }

        public void setNowOrSchedule(String nowOrSchedule) {
            this.nowOrSchedule = nowOrSchedule;
        }

        public String getNotificationDate() {
            return notificationDate;
        }

        public void setNotificationDate(String notificationDate) {
            this.notificationDate = notificationDate;
        }

        public String getNotificationTime() {
            return notificationTime;
        }

        public void setNotificationTime(String notificationTime) {
            this.notificationTime = notificationTime;
        }

    }
}

