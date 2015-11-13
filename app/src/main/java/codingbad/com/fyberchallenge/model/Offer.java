package codingbad.com.fyberchallenge.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ayi on 11/12/15.
 */
public class Offer implements Serializable {
    private String link;
    private String title;
    private int offer_id;
    private String teaser;
    private String required_actions;
    private Thumbnail thumbnail;
    private List<OfferType> offer_types;
    private String payout;
    private TimeToPayOut time_to_payout;

    public String getTitle() {
        return title;
    }

    public String getLowresThumbnailUrl() {
        return thumbnail.getLowres();
    }

    public String getHiresThumbnailUrl() {
        return thumbnail.getHires();
    }

    public String getTeaser() {
        return teaser;
    }

    public String getPayout() {
        return payout;
    }
}
