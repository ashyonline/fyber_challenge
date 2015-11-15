package codingbad.com.fyberchallenge.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ayi on 11/12/15.
 */
public class Thumbnail implements Serializable {
    @SerializedName("lowres")
    private String mLowres;
    @SerializedName("hires")
    private String mHires;

    public String getLowres() {
        return mLowres;
    }

    public void setLowres(String lowres) {
        this.mLowres = lowres;
    }

    public String getHires() {
        return mHires;
    }

    public void setHires(String hires) {
        this.mHires = hires;
    }
}
