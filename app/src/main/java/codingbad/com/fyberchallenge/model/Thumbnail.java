package codingbad.com.fyberchallenge.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayi on 11/12/15.
 */
public class Thumbnail {
    @SerializedName("lowres")
    private String mLowres;
    @SerializedName("hires")
    private String mHires;

    public String getLowres() {
        return mLowres;
    }

    public String getHires() {
        return mHires;
    }
}
