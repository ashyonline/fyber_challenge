package codingbad.com.fyberchallenge.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayi on 11/12/15.
 */
public class FyberErrorModel {
    @SerializedName("code")
    private String mCode;
    @SerializedName("message")
    private String mErrorDetails;

    public FyberErrorModel(String code, String errorMessage) {
        mCode = code;
        mErrorDetails = errorMessage;
    }
    public String getCode() {
        return mCode;
    }

    public String getErrorDetais() {
        return mErrorDetails;
    }
}
