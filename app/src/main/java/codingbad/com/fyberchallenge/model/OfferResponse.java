package codingbad.com.fyberchallenge.model;

import java.util.List;

/**
 * Created by ayi on 11/11/15.
 */
public class OfferResponse {
    private String code;
    private String message;
    private int count;
    private int pages;
    private Information information;
    private List<Offer> offers;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<Offer> getOffers() {
        return offers;
    }
}
