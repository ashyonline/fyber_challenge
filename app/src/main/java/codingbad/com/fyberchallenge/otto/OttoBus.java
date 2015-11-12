package codingbad.com.fyberchallenge.otto;

import android.os.Handler;
import android.os.Looper;

import com.google.inject.Singleton;
import com.squareup.otto.Bus;

/**
 * Created by ayi on 11/11/15.
 */
@Singleton
public class OttoBus extends Bus {
    private final Handler mainThread = new Handler(Looper.getMainLooper());

    public OttoBus() {
    }

    public void post(final Object event) {
        if(Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            this.mainThread.post(new Runnable() {
                public void run() {
                    OttoBus.super.post(event);
                }
            });
        }

    }
}
