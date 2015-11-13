package codingbad.com.fyberchallenge.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayi on 11/12/15.
 */
public class Information {
    @SerializedName("app_name")
    private String mAppName;
    @SerializedName("appid")
    private String mAppId;
    @SerializedName("virtual_currency")
    private String mVirtualCurrency;

    // this field is not in the documentation but I can see it in the response
    @SerializedName("virtual_currency_sale_enabled")
    private Boolean mVirtualCurrencySaleEnabled;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("language")
    private String mLanguage;
    @SerializedName("support_url")
    private String mSupportUrl;
}
