package hardcastle.com.churchapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EventsResponseBean implements Serializable {
    @SerializedName("STATUS")
    @Expose
    private String STATUS;
    @SerializedName("MESSAGE")
    @Expose
    private String MESSAGE;
    @SerializedName("DATA")
    @Expose
    private List<DATum> DATA;

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public List<DATum> getDATA() {
        return DATA;
    }

    public void setDATA(List<DATum> DATA) {
        this.DATA = DATA;
    }

    public static class DATum implements Parcelable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("event_name")
        @Expose
        private String eventName;
        @SerializedName("event_date")
        @Expose
        private String eventDate;
        @SerializedName("event_time")
        @Expose
        private String eventTime;
        @SerializedName("event_place")
        @Expose
        private String eventPlace;
        @SerializedName("event_ticket_slot")
        @Expose
        private String eventTicketSlot;
        @SerializedName("event_free_paid")
        @Expose
        private String eventFreePaid;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("event_photo")
        @Expose
        private String eventPhoto;

        protected DATum(Parcel in) {
            id = in.readString();
            eventName = in.readString();
            eventDate = in.readString();
            eventTime = in.readString();
            eventPlace = in.readString();
            eventTicketSlot = in.readString();
            eventFreePaid = in.readString();
            description = in.readString();
            eventPhoto = in.readString();
        }

        public static final Creator<DATum> CREATOR = new Creator<DATum>() {
            @Override
            public DATum createFromParcel(Parcel in) {
                return new DATum(in);
            }

            @Override
            public DATum[] newArray(int size) {
                return new DATum[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getEventDate() {
            return eventDate;
        }

        public void setEventDate(String eventDate) {
            this.eventDate = eventDate;
        }

        public String getEventTime() {
            return eventTime;
        }

        public void setEventTime(String eventTime) {
            this.eventTime = eventTime;
        }

        public String getEventPlace() {
            return eventPlace;
        }

        public void setEventPlace(String eventPlace) {
            this.eventPlace = eventPlace;
        }

        public String getEventTicketSlot() {
            return eventTicketSlot;
        }

        public void setEventTicketSlot(String eventTicketSlot) {
            this.eventTicketSlot = eventTicketSlot;
        }

        public String getEventFreePaid() {
            return eventFreePaid;
        }

        public void setEventFreePaid(String eventFreePaid) {
            this.eventFreePaid = eventFreePaid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEventPhoto() {
            return eventPhoto;
        }

        public void setEventPhoto(String eventPhoto) {
            this.eventPhoto = eventPhoto;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(eventName);
            dest.writeString(eventDate);
            dest.writeString(eventTime);
            dest.writeString(eventPlace);
            dest.writeString(eventTicketSlot);
            dest.writeString(eventFreePaid);
            dest.writeString(description);
            dest.writeString(eventPhoto);
        }
    }
}