package codingbad.com.fyberchallenge.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ayi on 11/12/15.
 */
public class TimeToPayOut implements Serializable {
    @SerializedName("amount")
    private int mAmount;
    @SerializedName("readable")
    private String mReadable;
}
