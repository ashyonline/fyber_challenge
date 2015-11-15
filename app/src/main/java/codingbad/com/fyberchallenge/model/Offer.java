package codingbad.com.fyberchallenge.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ayi on 11/12/15.
 */
public class Offer implements Serializable {
    @SerializedName("link")
    private String mLink;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("offer_id")
    private int mOfferId;
    @SerializedName("teaser")
    private String mTeaser;
    @SerializedName("required_actions")
    private String mRequiredActions;
    @SerializedName("thumbnail")
    private Thumbnail mThumbnail;
    @SerializedName("offer_types")
    private List<OfferType> mOfferTypes;
    @SerializedName("payout")
    private String mPayout;
    @SerializedName("time_to_payout")
    private TimeToPayOut mTimeToPayout;

    public void setThumbnail(Thumbnail thumbnail) {
        mThumbnail = thumbnail;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getLowresThumbnailUrl() {
        return mThumbnail.getLowres();
    }

    public String getHiresThumbnailUrl() {
        return mThumbnail.getHires();
    }

    public String getTeaser() {
        return mTeaser;
    }

    public void setTeaser(String teaser) {
        mTeaser = teaser;
    }

    public String getPayout() {
        return mPayout;
    }

    public void setPayout(String payout) {
        mPayout = payout;
    }
}
