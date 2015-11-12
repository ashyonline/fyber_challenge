package codingbad.com.fyberchallenge.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayi on 11/12/15.
 */
public class FyberErrorModel {
    @SerializedName("code")
    private int mCode;
    @SerializedName("error")
    private String mErrorDetails;

    public FyberErrorModel(int code, String errorMessage) {
        mCode = code;
        mErrorDetails = errorMessage;
    }
    public int getCode() {
        return mCode;
    }

    public String getErrorDetais() {
        return mErrorDetails;
    }
}
