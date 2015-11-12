package codingbad.com.fyberchallenge.tasks;

import android.content.Context;

import javax.inject.Inject;

import codingbad.com.fyberchallenge.model.OfferResponse;
import codingbad.com.fyberchallenge.network.client.FyberClient;
import codingbad.com.fyberchallenge.otto.OttoBus;
import roboguice.util.RoboAsyncTask;

/**
 * Created by ayi on 11/11/15.
 */
public class SubmitFormTask extends RoboAsyncTask<OfferResponse> {

    @Inject
    private FyberClient mFyberClient;
    private String mFormat;
    private int mAppid;
    private String mUid;
    private String mLocale;
    private String mOsVersion;
    private long mTimestamp;
    private String mHashkey;
    private String mGoogleAdId;
    private Boolean mGoogleAdIdLimitedTrackingEnabled;

    @Inject
    protected OttoBus ottoBus;

    protected SubmitFormTask(Context context) {
        super(context);
    }

    @Override
    public OfferResponse call() throws Exception {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        return mFyberClient.getOffers(mFormat,
            mAppid,
            mUid,
            mLocale,
            mOsVersion,
            mTimestamp,
            mHashkey,
            mGoogleAdId,
            mGoogleAdIdLimitedTrackingEnabled
        );
    }

    @Override
    protected void onSuccess(OfferResponse result) throws Exception {
        super.onSuccess(result);
        if (!isCancelled()) {
            ottoBus.post(new Event(result));
        }

    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);
    }

    /*
         * Otto Event
    */
    public class Event {
        public OfferResponse result;

        public  Event(OfferResponse result) {
            this.result = result;
        }
    }
}
