package codingbad.com.fyberchallenge;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import codingbad.com.fyberchallenge.model.FyberFormModel;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class FyberFormModelTest {
    public static final int APPID = 2070;
    public static final String GOOGLE_AD_ID = "googleid";
    public static final String LOCALE = "de";
    public static final String OS_VERSION = "5.1.1";
    public static final int TIMESTAMP = 1447537498;
    public static final String FORMAT = "json";
    private static final boolean GOOGLE_AD_LIMITED = false;
    private static final String IP = "2.16.1.147";
    private static final String UID = "spiderman";
    FakeFyberFormModel fyberFormModelWithMandatoryParams;
    @Mock
    Context fakeContext;
    private FakeFyberFormModel fyberFormModelWithOptionalParams;

    @Before
    public void setup() {
        fyberFormModelWithMandatoryParams = new FakeFyberFormModel(FORMAT,
                APPID,
                UID,
                LOCALE,
                OS_VERSION,
                TIMESTAMP,
                GOOGLE_AD_ID,
                GOOGLE_AD_LIMITED,
                null,
                null,
                null,
                null,
                null,
                null
        );
        fyberFormModelWithOptionalParams = new FakeFyberFormModel(FORMAT,
                APPID,
                UID,
                LOCALE,
                OS_VERSION,
                TIMESTAMP,
                GOOGLE_AD_ID,
                GOOGLE_AD_LIMITED,
                IP,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Test
    public void verifyConcatenatedParams() {
        String concatenatedParams = fyberFormModelWithMandatoryParams.getConcatenatedParams();
        String expectedResult = "appid=" + APPID + "&format=" + FORMAT + "&google_ad_id=" + GOOGLE_AD_ID + "&google_ad_id_limited_tracking_enabled=" + GOOGLE_AD_LIMITED + "&locale=" + LOCALE + "&os_version=" + OS_VERSION + "&timestamp=" + TIMESTAMP + "&uid=" + UID;

        // should have mandatory params
        assert (concatenatedParams.contains(FyberFormModel.FORMAT));
        assert (concatenatedParams.contains(FyberFormModel.APPID));
        assert (concatenatedParams.contains(FyberFormModel.UID));
        assert (concatenatedParams.contains(FyberFormModel.LOCALE));
        assert (concatenatedParams.contains(FyberFormModel.OS_VERSION));
        assert (concatenatedParams.contains(FyberFormModel.GOOGLE_AD_ID));
        assert (concatenatedParams.contains(FyberFormModel.GOOGLE_AD_ID_LIMITED_TRACKING_ENABLED));

        // should have user input
        assert (concatenatedParams.contains(FORMAT));
        assert (concatenatedParams.contains(String.valueOf(APPID)));
        assert (concatenatedParams.contains(GOOGLE_AD_ID));
        assert (concatenatedParams.contains(String.valueOf(GOOGLE_AD_LIMITED)));
        assert (concatenatedParams.contains(LOCALE));
        assert (concatenatedParams.contains(OS_VERSION));

        // shouldn't have any null params
        assert (!concatenatedParams.contains(FyberFormModel.IP));
        assert (!concatenatedParams.contains(FyberFormModel.PUB));
        assert (!concatenatedParams.contains(FyberFormModel.PAGE));
        assert (!concatenatedParams.contains(FyberFormModel.OFFER_TYPES));
        assert (!concatenatedParams.contains(FyberFormModel.PS_TIME));
        assert (!concatenatedParams.contains(FyberFormModel.DEVICE));
        assertEquals(expectedResult, concatenatedParams);
    }

    @Test
    public void verifyConcatenatedParamsWithOptional() {
        String concatenatedParams = fyberFormModelWithOptionalParams.getConcatenatedParams();
        String expectedResult = "appid=" + APPID + "&format=" + FORMAT + "&google_ad_id=" + GOOGLE_AD_ID + "&google_ad_id_limited_tracking_enabled=" + GOOGLE_AD_LIMITED + "&ip=" + IP + "&locale=" + LOCALE + "&os_version=" + OS_VERSION + "&timestamp=" + TIMESTAMP + "&uid=" + UID;

        // should have mandatory params
        assert (concatenatedParams.contains(FyberFormModel.FORMAT));
        assert (concatenatedParams.contains(FyberFormModel.APPID));
        assert (concatenatedParams.contains(FyberFormModel.UID));
        assert (concatenatedParams.contains(FyberFormModel.LOCALE));
        assert (concatenatedParams.contains(FyberFormModel.OS_VERSION));
        assert (concatenatedParams.contains(FyberFormModel.GOOGLE_AD_ID));
        assert (concatenatedParams.contains(FyberFormModel.GOOGLE_AD_ID_LIMITED_TRACKING_ENABLED));

        // should have user input
        assert (concatenatedParams.contains(FORMAT));
        assert (concatenatedParams.contains(String.valueOf(APPID)));
        assert (concatenatedParams.contains(GOOGLE_AD_ID));
        assert (concatenatedParams.contains(String.valueOf(GOOGLE_AD_LIMITED)));
        assert (concatenatedParams.contains(LOCALE));
        assert (concatenatedParams.contains(OS_VERSION));
        assert (concatenatedParams.contains(IP));

        // should have ip
        assert (concatenatedParams.contains(FyberFormModel.IP));

        // shouldn't have any null params
        assert (!concatenatedParams.contains(FyberFormModel.PUB));
        assert (!concatenatedParams.contains(FyberFormModel.PAGE));
        assert (!concatenatedParams.contains(FyberFormModel.OFFER_TYPES));
        assert (!concatenatedParams.contains(FyberFormModel.PS_TIME));
        assert (!concatenatedParams.contains(FyberFormModel.DEVICE));
        assertEquals(expectedResult, concatenatedParams);
    }

    @Test
    public void verifyHashKeyWithMandatory() {
        String hashKey = fyberFormModelWithMandatoryParams.calculateHashKey();
        String expectedHashKey = "1cf932a9b9fb84c5c7773ea7ee64530c8cddab4e";
        assertEquals(expectedHashKey, hashKey);
    }

    @Test
    public void verifyHashKeyWithOptional() {
        String hashKey = fyberFormModelWithOptionalParams.calculateHashKey();
        String expectedHashKey = "6d63ff2f901419daa105bc3cc305680878b3f4f9";
        assertEquals(expectedHashKey, hashKey);
    }

    private class FakeFyberFormModel extends FyberFormModel {
        public FakeFyberFormModel(String format, int appid, String uid, String locale, String osVersion, long timestamp, String googleAdId, Boolean isLimitAdTrackingEnabled,
                                  String ip, String customParameters, Integer page, String offerTypes, Long psTime, String device) {
            super(format, appid, uid, locale, osVersion, timestamp, googleAdId, isLimitAdTrackingEnabled, ip, customParameters, page, offerTypes, psTime, device);
        }

        public String getConcatenatedParams() {
            return super.getConcatenatedParams();
        }

        public String calculateHashKey() {
            return super.calculateHashKey();
        }
    }
}