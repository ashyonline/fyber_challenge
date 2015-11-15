package codingbad.com.fyberchallenge.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ayi on 11/11/15.
 */
public class OfferResponse implements Serializable {

    @SerializedName("code")
    private String mCode;

    @SerializedName("message")
    private String mMessage;

    @SerializedName("count")
    private int mCount;

    @SerializedName("pages")
    private int mPages;

    @SerializedName("information")
    private Information mInformation;

    @SerializedName("offers")
    private List<Offer> mOffers;

    public String getCode() {
        return mCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public List<Offer> getOffers() {
        return mOffers;
    }
}
