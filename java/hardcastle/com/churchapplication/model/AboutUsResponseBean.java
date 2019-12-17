package hardcastle.com.churchapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutUsResponseBean {

    @SerializedName("STATUS")
    @Expose
    private Integer sTATUS;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("DATA")
    @Expose
    private AboutUs aboutUs;

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

    public AboutUs getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(AboutUs aboutUs) {
        this.aboutUs = aboutUs;
    }

    public static class AboutUs {

        @SerializedName("about_descriptionn")
        @Expose
        private String aboutDescriptionn;

        public String getAboutDescriptionn() {
            return aboutDescriptionn;
        }

        public void setAboutDescriptionn(String aboutDescriptionn) {
            this.aboutDescriptionn = aboutDescriptionn;
        }
    }
}