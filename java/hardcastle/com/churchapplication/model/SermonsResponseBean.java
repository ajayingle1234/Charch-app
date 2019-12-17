package hardcastle.com.churchapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SermonsResponseBean {

    @SerializedName("STATUS")
    @Expose
    private Integer sTATUS;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("DATA")
    @Expose
    private List<SermonList> sermonLists = null;

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

    public List<SermonList> getSermonLists() {
        return sermonLists;
    }

    public void setSermonLists(List<SermonList> sermonLists) {
        this.sermonLists = sermonLists;
    }

    public static class SermonList {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("sermons_photo")
        @Expose
        private String sermonsPhoto;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSermonsPhoto() {
            return sermonsPhoto;
        }

        public void setSermonsPhoto(String sermonsPhoto) {
            this.sermonsPhoto = sermonsPhoto;
        }
    }
}