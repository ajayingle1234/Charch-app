package hardcastle.com.churchapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GalleryResponseBean {

    @SerializedName("STATUS")
    @Expose
    private Integer sTATUS;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("DATA")
    @Expose
    private List<ImageList> imageLists = null;

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

    public List<ImageList> getImageLists() {
        return imageLists;
    }

    public void setImageLists(List<ImageList> imageLists) {
        this.imageLists = imageLists;
    }

    public static class ImageList {
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("img")
        @Expose
        private String img;
        @SerializedName("video")
        @Expose
        private String video;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }
    }
}