package codingbad.com.fyberchallenge.tasks;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

import codingbad.com.fyberchallenge.R;
import codingbad.com.fyberchallenge.model.FyberErrorModel;
import codingbad.com.fyberchallenge.model.OfferResponse;
import codingbad.com.fyberchallenge.network.client.FyberClient;
import codingbad.com.fyberchallenge.otto.OttoBus;
import codingbad.com.fyberchallenge.utils.StringUtils;
import retrofit.RetrofitError;
import roboguice.util.RoboAsyncTask;

/**
 * Created by ayi on 11/11/15.
 */
public class GetFyberOffersTask extends RoboAsyncTask<OfferResponse> {

    @Inject
    protected OttoBus ottoBus;
    @Inject
    private FyberClient mFyberClient;
    private final String mFormat;
    private final Integer mAppid;
    private final String mUid;
    private final String mLocale;
    private final String mOsVersion;
    private final Long mTimestamp;
    private final String mHashkey;
    private final String mGoogleAdId;
    private final Boolean mGoogleAdIdLimitedTrackingEnabled;
    private final String mIp;
    private final String mPub0;
    private final Integer mPage;
    private final String mOfferTypes;
    private final Long mPsTime;
    private final String mDevice;

    // first approach only accepts one custom param
    public GetFyberOffersTask(Context context,
                              String format,
                              Integer appid,
                              String uid,
                              String locale,
                              String osVersion,
                              Long timestamp,
                              String hashkey,
                              String googleAdId,
                              Boolean googleAdIdLimitedTrackingEnabled,
                              String ip,
                              String pub0,
                              Integer page,
                              String offerTypes,
                              Long psTime,
                              String device) {
        super(context);
        mFormat = format;
        mAppid = appid;
        mUid = uid;
        mLocale = locale;
        mOsVersion = osVersion;
        mTimestamp = timestamp;
        mHashkey = hashkey;
        mGoogleAdId = googleAdId;
        mGoogleAdIdLimitedTrackingEnabled = googleAdIdLimitedTrackingEnabled;
        mIp = ip;
        mPub0 = pub0;
        mPage = page;
        mOfferTypes = offerTypes;
        mPsTime = psTime;
        mDevice = device;
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
                mGoogleAdIdLimitedTrackingEnabled,
                mIp,
                mPub0,
                mPage,
                mOfferTypes,
                mPsTime,
                mDevice
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
        if (e instanceof RetrofitError) {
            RetrofitError e1 = (RetrofitError) e;
            // Possible expected errors
            // 400: is for invalid parameter
            // 401: An invalid or missing has h key for this appid was given as a parameter in the request.
            // 500: An unknown error happened on the Fyber server.

            if (e1.getResponse() != null) {
                int status = e1.getResponse().getStatus();
                if (status == 400 || status == 401 || status == 500) {
                    try {
                        String errorText = StringUtils.convertToString(e1.getResponse().getBody().in());
                        FyberErrorModel fyberError = (FyberErrorModel) e1.getBodyAs(FyberErrorModel.class);
                        ottoBus.post(new FyberError(fyberError));
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                } else {
                    ottoBus.post(new FyberError(status, context.getString(R.string.unexpected_error)));
                }
            } else {
                super.onException(e);
                e.printStackTrace();
            }
        } else {
            super.onException(e);
        }
    }

    /*
         * Otto Event
    */
    public class Event {
        private OfferResponse mResult;

        public OfferResponse getResult() {
            return mResult;
        }

        public Event(OfferResponse result) {
            this.mResult = result;
        }
    }

    public class FyberError {

        private FyberErrorModel mFyberErrorModel;

        public FyberError(FyberErrorModel fyberError) {
            mFyberErrorModel = fyberError;
        }

        public FyberError(int code, String error) {
            mFyberErrorModel = new FyberErrorModel(code, error);
        }

        public String getErrorMessage() {
            return mFyberErrorModel.getErrorDetais();
        }

        public int getCode() {
            return mFyberErrorModel.getCode();
        }
    }
}
