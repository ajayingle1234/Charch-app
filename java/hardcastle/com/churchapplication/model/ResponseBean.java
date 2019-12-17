package hardcastle.com.churchapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBean {

    @SerializedName("STATUS")
    @Expose
    private Integer sTATUS;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;

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
}