package hardcastle.com.churchapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrayerResponseBean {

    @SerializedName("STATUS")
    @Expose
    private Integer sTATUS;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("DATA")
    @Expose
    private List<PrayerResponse> prayerResponseList = null;

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


    public List<PrayerResponse> getPrayerResponseList() {
        return prayerResponseList;
    }

    public void setPrayerResponseList(List<PrayerResponse> prayerResponseList) {
        this.prayerResponseList = prayerResponseList;
    }

    public static class PrayerResponse implements Comparable {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("prayer_video")
        @Expose
        private String prayerVideo;

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public boolean isLiked() {
            return isLiked;
        }

        public void setLiked(boolean liked) {
            isLiked = liked;
        }

        @SerializedName("prayer_photo")
        @Expose
        private String prayerPhoto;
        @SerializedName("like_count")
        @Expose
        private int likeCount;
        @SerializedName("comment_count")
        @Expose
        private int commentCount;
        @SerializedName("isLiked")
        @Expose
        private boolean isLiked;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrayerVideo() {
            return prayerVideo;
        }

        public void setPrayerVideo(String prayerVideo) {
            this.prayerVideo = prayerVideo;
        }

        public String getPrayerPhoto() {
            return prayerPhoto;
        }

        public void setPrayerPhoto(String prayerPhoto) {
            this.prayerPhoto = prayerPhoto;
        }

        @Override
        public int compareTo(Object o) {
            int compareID = Integer.parseInt(getId());
            /* For Ascending order*/
            return Integer.parseInt(this.id) - compareID;

        }
    }
}
