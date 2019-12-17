package hardcastle.com.churchapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SermonVideoDetailsBean {

    private String video_url;
    private String video_name;

    public SermonVideoDetailsBean(String video_url, String video_name) {
        this.video_url = video_url;
        this.video_name = video_name;
    }

    protected SermonVideoDetailsBean(Parcel in) {
        video_url = in.readString();
        video_name = in.readString();
    }


    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }


}
