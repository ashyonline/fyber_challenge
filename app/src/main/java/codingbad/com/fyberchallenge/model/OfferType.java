package codingbad.com.fyberchallenge.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ayi on 11/12/15.
 */
public class OfferType implements Serializable {
    @SerializedName("offer_type_id")
    private String mOfferTypeId;
    @SerializedName("readable")
    private String mReadable;
}
