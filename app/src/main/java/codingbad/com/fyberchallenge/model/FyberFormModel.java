package codingbad.com.fyberchallenge.model;

import android.app.Application;

import org.apache.commons.lang.CharSet;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import codingbad.com.fyberchallenge.FyberChallengeApplication;

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
    private static final String EQUAL = "=";
    private static final String AND = "&";
    public static final String HASHKEY = "hashkey";

    private List<FyberParameter> mParams = new ArrayList<FyberParameter>();

    public FyberFormModel(String format, int appid, String uid, String locale, String osVersion, long timestamp, String googleAdId, Boolean isLimitAdTrackingEnabled,
                          String ip, String customParameters, int page, String offerTypes, long psTime, String device) {
        mParams.add(new FyberParameter(format, FORMAT));
        mParams.add(new FyberParameter(appid, APPID));
        mParams.add(new FyberParameter(uid, UID));
        mParams.add(new FyberParameter(locale, LOCALE));
        mParams.add(new FyberParameter(osVersion, OS_VERSION));
        mParams.add(new FyberParameter(timestamp, TIMESTAMP));
        mParams.add(new FyberParameter(googleAdId, GOOGLE_AD_ID));
        mParams.add(new FyberParameter(isLimitAdTrackingEnabled, GOOGLE_AD_ID_LIMITED_TRACKING_ENABLED));

        // optional params
        if (ip != null) {
            mParams.add(new FyberParameter(ip, IP));
        }

        String[] customParams = getCommaSeparatedParams(customParameters);
        if (customParams != null) {
            String param;
            for (int i = 0; i < customParams.length; i++) {
                param = customParams[i];
                mParams.add(new FyberParameter(param, PUB + i));
            }
        }

        if (page != 1) {
            mParams.add(new FyberParameter(page, PAGE));
        }

        if (offerTypes != null) {
            mParams.add(new FyberParameter(offerTypes, OFFER_TYPES));
        }

        if (psTime != 0) {
            mParams.add(new FyberParameter(psTime, PS_TIME));
        }

        if (device != null) {
            mParams.add(new FyberParameter(device, DEVICE));
        }

        mParams.add(new FyberParameter(calculateHashKey(), HASHKEY));
    }

    private String[] getCommaSeparatedParams(String customParameters) {
        if (customParameters == null) {
            return null;
        }

        // TODO: convert to comma separated params to be sent as pub0, pub1, and so on
        return new String[0];
    }

    private String calculateHashKey() {
        // 1. Get all request parameters and their values (except hashkey)
        // done in constructor

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

        // 4. Concatenate the resulting string with & and the API Key handed out to you by Fyber.
        result = result + AND + FyberChallengeApplication.getApiKey();

        // 5. Hash the whole resulting string, using SHA1.
        return calculateSha1(result);
    }

    private static String convertToHex(byte[] data) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int twoHalfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    stringBuffer.append((char) ('0' + halfbyte));
                } else {
                    stringBuffer.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (twoHalfs++ < 1);
        }
        return stringBuffer.toString();
    }


    public static String calculateSha1(String text) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(text.getBytes("iso-8859-1"), 0, text.length());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

        byte[] sha1hash = messageDigest.digest();
        return convertToHex(sha1hash);
    }
}
