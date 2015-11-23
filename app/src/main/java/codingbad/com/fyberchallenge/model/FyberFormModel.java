package codingbad.com.fyberchallenge.model;

import android.content.Context;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import codingbad.com.fyberchallenge.FyberChallengeApplication;
import codingbad.com.fyberchallenge.tasks.GetFyberOffersTask;
import codingbad.com.fyberchallenge.utils.StringUtils;

/**
 * Created by ayi on 11/11/15.
 */
public class FyberFormModel {
    public static final String FORMAT = "format";
    public static final String APPID = "appid";
    public static final String UID = "uid";
    public static final String LOCALE = "locale";
    public static final String OS_VERSION = "os_version";
    public static final String TIMESTAMP = "timestamp";
    public static final String GOOGLE_AD_ID = "google_ad_id";
    public static final String GOOGLE_AD_ID_LIMITED_TRACKING_ENABLED = "google_ad_id_limited_tracking_enabled";
    public static final String IP = "ip";
    public static final String PUB = "pub";
    public static final String PAGE = "page";
    public static final String OFFER_TYPES = "offer_types";
    public static final String PS_TIME = "ps_time";
    public static final String DEVICE = "device";
    public static final String HASHKEY = "hashkey";
    private static final String EQUAL = "=";
    private static final String AND = "&";
    private final String mFormat;
    private final int mAppId;
    private final String mUid;
    private final String mLocale;
    private final String mOsVersion;
    private final long mTimestamp;
    private final String mGoogleAdId;
    private final Boolean mGoogleAdIdLimitedTrackingEnabled;
    private final String mHashKey;
    private final String mIp;
    private final String mPub0;
    private final Integer mPage;
    private final String mOfferTypes;
    private final Long mPsTime;
    private final String mDevice;

    private List<FyberParameter> mParams = new ArrayList<FyberParameter>();

    public FyberFormModel(String format, int appid, String uid, String locale, String osVersion, long timestamp, String googleAdId, Boolean isLimitAdTrackingEnabled,
                          String ip, String customParameters, Integer page, String offerTypes, Long psTime, String device) {
        mParams.add(new FyberParameter(format, FORMAT));
        mFormat = format;

        mParams.add(new FyberParameter(appid, APPID));
        mAppId = appid;

        mParams.add(new FyberParameter(uid, UID));
        mUid = uid;

        mParams.add(new FyberParameter(locale, LOCALE));
        mLocale = locale;

        mParams.add(new FyberParameter(osVersion, OS_VERSION));
        mOsVersion = osVersion;

        mParams.add(new FyberParameter(timestamp, TIMESTAMP));
        mTimestamp = timestamp;

        mParams.add(new FyberParameter(googleAdId, GOOGLE_AD_ID));
        mGoogleAdId = googleAdId;

        mParams.add(new FyberParameter(isLimitAdTrackingEnabled, GOOGLE_AD_ID_LIMITED_TRACKING_ENABLED));
        mGoogleAdIdLimitedTrackingEnabled = isLimitAdTrackingEnabled;

        // optional params
        if (ip != null) {
            mParams.add(new FyberParameter(ip, IP));
        }
        mIp = ip;

        String[] customParams = getCommaSeparatedParams(customParameters);

        // using only first custom param:
        // TODO: just using first custom params, think how to send all
        if (customParams != null && customParams.length > 0) {
            mPub0 = customParams[0];
            mParams.add(new FyberParameter(mPub0, PUB + 0));

            // to add all params, uncomment:
            /*
                String param;
                for (int i = 0; i < customParams.length; i++) {
                    param = customParams[i];
                    mParams.add(new FyberParameter(param, PUB + i));
                }
            */
        } else {
            mPub0 = null;
        }


        if (page != null) {
            mParams.add(new FyberParameter(page, PAGE));
        }
        mPage = null;

        if (offerTypes != null) {
            mParams.add(new FyberParameter(offerTypes, OFFER_TYPES));
        }
        mOfferTypes = offerTypes;

        if (psTime != null) {
            mParams.add(new FyberParameter(psTime, PS_TIME));
        }

        mPsTime = psTime;

        if (device != null) {
            mParams.add(new FyberParameter(device, DEVICE));
        }
        mDevice = device;

        mHashKey = calculateHashKey();
    }

    private String[] getCommaSeparatedParams(String customParameters) {
        if (customParameters == null) {
            return null;
        }

        // TODO: convert to comma separated params to be sent as pub0, pub1, and so on
        return customParameters.split(",");
    }

    protected String calculateHashKey() {
        String concatenatedExistingParams = getConcatenatedParams();

        // 4. Concatenate the resulting string with & and the API Key handed out to you by Fyber.
        concatenatedExistingParams = concatenatedExistingParams + AND + FyberChallengeApplication.getApiKey();

        // 5. Hash the whole resulting string, using SHA1.
        return  new String(Hex.encodeHex(DigestUtils.sha1(concatenatedExistingParams)));
    }

    protected String getConcatenatedParams() {
        // 1. Get all request parameters and their values (except hashkey)
        // done in constructor
        // remove hashkey

        // 2. Order theses pairs alphabetically by parameter name
        Collections.sort(mParams, new Comparator<FyberParameter>() {
            @Override
            public int compare(FyberParameter lhs, FyberParameter rhs) {
                return lhs.getParameterName().compareToIgnoreCase(rhs.getParameterName());
            }
        });

        // 3. Concatenate all pairs using = between key and value and & between the pairs.
        FyberParameter firstParam = mParams.get(0);
        String result = firstParam.getParameterName() + EQUAL + firstParam.getValue();
        for (int i = 1; i < mParams.size(); i++) {
            FyberParameter parameter = mParams.get(i);
            result = result + AND + parameter.getParameterName() + EQUAL + parameter.getValue();
        }

        return result;
    }

    public void submit(Context context) {
        new GetFyberOffersTask(context, mFormat, mAppId, mUid, mLocale, mOsVersion, mTimestamp, mHashKey, mGoogleAdId, mGoogleAdIdLimitedTrackingEnabled, mIp, mPub0, mPage, mOfferTypes, mPsTime, mDevice)
                .execute();
    }
}
