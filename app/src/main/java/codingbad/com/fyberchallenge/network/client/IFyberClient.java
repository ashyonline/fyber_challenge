package codingbad.com.fyberchallenge.network.client;

import codingbad.com.fyberchallenge.model.OfferResponse;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;

/**
 * Created by ayi on 11/11/15.
 */
public interface IFyberClient {
    @FormUrlEncoded
    @GET("/offers/get")
    OfferResponse getOffers(@Field("format") String format,
                            @Field("appid") int appid,
                            @Field("uid") String uid,
                            @Field("locale") String locale,
                            @Field("os_version") String os_version,
                            @Field("timestamp") long timestamp,
                            @Field("hashkey") String hashkey,
                            @Field("google_ad_id") String google_ad_id,
                            @Field("google_ad_id_limited_tracking_enabled") Boolean google_ad_id_limited_tracking_enabled
    );
}
