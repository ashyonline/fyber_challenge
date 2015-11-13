package codingbad.com.fyberchallenge.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayi on 11/12/15.
 */
public class TimeToPayOut {
    @SerializedName("amount")
    private int mAmount;
    @SerializedName("readable")
    private String mReadable;
}
