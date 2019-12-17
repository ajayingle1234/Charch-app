package hardcastle.com.churchapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GalleryBean implements Parcelable {

    @SerializedName("STATUS")
    @Expose
    private Integer sTATUS;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("DATA")
    @Expose
    private ArrayList<GalleryBean> galleryResponseList = null;

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


    public ArrayList<GalleryBean> getGalleryResponseList() {
        return galleryResponseList;
    }

    public void setGalleryResponseList(ArrayList<GalleryBean> galleryResponseList) {
        this.galleryResponseList = galleryResponseList;
    }
    private int type;
    private String img,video;


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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public GalleryBean(int type, String img, String video) {
        this.type = type;
        this.img = img;
        this.video = video;
    }

    public GalleryBean(Parcel in) {
        type = in.readInt();
        img = in.readString();
        video = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(img);
        dest.writeString(video);

    }


    public static final Creator<GalleryBean> CREATOR = new Creator<GalleryBean>()
    {
        public GalleryBean createFromParcel(Parcel in)
        {
            return new GalleryBean(in);
        }
        public GalleryBean[] newArray(int size)
        {
            return new GalleryBean[size];
        }
    };
}
