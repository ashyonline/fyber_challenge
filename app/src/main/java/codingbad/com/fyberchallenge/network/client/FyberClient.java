package codingbad.com.fyberchallenge.network.client;

import com.squareup.okhttp.OkHttpClient;

import codingbad.com.fyberchallenge.BuildConfig;
import codingbad.com.fyberchallenge.model.OfferResponse;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by ayi on 11/11/15.
 */
public class FyberClient {
    private RestAdapter restAdapter;
    private IFyberClient mFyberClient;

    public FyberClient() {
        if (restAdapter == null || mFyberClient == null) {
            OkHttpClient okHttpClient = new OkHttpClient();

            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BuildConfig.HOST)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(okHttpClient))
                    .build();

            mFyberClient = restAdapter.create(IFyberClient.class);
        }
    }

    public OfferResponse getOffers(String format, int appid, String uid, String locale, String osVersion, long timestamp, String hashkey, String googleAdId, Boolean googleAdIdLimitedTrackingEnabled) {
        return mFyberClient.getOffers(format, appid, uid, locale, osVersion, timestamp, hashkey, googleAdId, googleAdIdLimitedTrackingEnabled);
    }
}
