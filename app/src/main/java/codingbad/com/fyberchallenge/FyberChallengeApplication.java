package codingbad.com.fyberchallenge;

import android.app.Application;

/**
 * Created by ayi on 11/11/15.
 */
public class FyberChallengeApplication extends Application {
    // hardcoded for now
    private static String API_KEY = "1c915e3b5d42d05136185030892fbb846c278927";

    public static String getApiKey() {
        return API_KEY;
    }
}
