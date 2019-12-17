package hardcastle.com.churchapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GivingTypeBean {

    @SerializedName("STATUS")
    @Expose
    private Integer sTATUS;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("DATA")
    @Expose
    private List<TypeOptions> typeOptionsList = null;

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

    public List<TypeOptions> getTypeOptionsList() {
        return typeOptionsList;
    }

    public void setTypeOptionsList(List<TypeOptions> typeOptionsList) {
        this.typeOptionsList = typeOptionsList;
    }

    public static class TypeOptions {

        @SerializedName("Option")
        @Expose
        private String option;

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }
    }
}