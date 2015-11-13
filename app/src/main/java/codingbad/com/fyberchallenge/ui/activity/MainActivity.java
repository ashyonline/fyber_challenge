package codingbad.com.fyberchallenge.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import java.util.List;

import codingbad.com.fyberchallenge.R;
import codingbad.com.fyberchallenge.model.Offer;
import codingbad.com.fyberchallenge.model.OfferResponse;
import codingbad.com.fyberchallenge.tasks.GetFyberOffersTask;
import codingbad.com.fyberchallenge.ui.fragment.FyberErrorFragment;
import codingbad.com.fyberchallenge.ui.fragment.FyberOffersFragment;
import codingbad.com.fyberchallenge.ui.fragment.MainFormFragment;
import codingbad.com.fyberchallenge.ui.fragment.NoOffersFragment;
import codingbad.com.fyberchallenge.ui.fragment.OfferDetailDialogFragment;
import roboguice.activity.RoboActionBarActivity;

public class MainActivity extends RoboActionBarActivity implements MainFormFragment.Callbacks, NoOffersFragment.Callbacks, FyberErrorFragment.Callbacks, FyberOffersFragment.Callbacks {

    private static final String MAIN_FORM_FRAGMENT_TAG = "main_form_fragment";
    private static final String OK = "OK";
    private static final String NO_CONTENT = "NO_CONTENT";
    private static final String NO_OFFERS_FRAGMENT_TAG = "no_offers_fragment";
    private static final String FYBER_ERROR_FRAGMENT_TAG = "fyber_error_fragment";
    private static final String OFFERS_FRAGMENT_TAG = "fyber_offers_fragment";
    private static final String OFFER_DETAIL_DIALOG_TAG = "offer_detail_dialog";

    private FragmentManager mFragmentManager;

    //
    private OfferResponse mLastResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMainFragment();
    }

    private void setMainFragment() {
        mFragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        startFragment.addToBackStack(MAIN_FORM_FRAGMENT_TAG);
        startFragment.add(R.id.fragment, new MainFormFragment(), MAIN_FORM_FRAGMENT_TAG);
        startFragment.commit();
    }


    @Override
    public void onFormSubmittedSuccessfully(OfferResponse response) {
        if (response.getCode().equals(OK)) {
            showOffers(response);
        } else if (response.getCode().equals(NO_CONTENT)) {
            showNotOffersFragment(response);
        } else {
            // unexpected result, do something!
        }
    }

    @Override
    public void onFormSubmittedWithError(GetFyberOffersTask.FyberError error) {
        showErrorFragment(error.getCode(), error.getErrorMessage());
    }

    private void showErrorFragment(int code, String errorMessage) {
        mFragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt(FyberErrorFragment.CODE, code);
        bundle.putString(FyberErrorFragment.MESSAGE, errorMessage);
        FyberErrorFragment fyberErrorFragment = new FyberErrorFragment();
        fyberErrorFragment.setArguments(bundle);
        startFragment.addToBackStack(NO_OFFERS_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, fyberErrorFragment, FYBER_ERROR_FRAGMENT_TAG);
        startFragment.commit();
    }

    private void showOffers(OfferResponse response) {
        mLastResponse = response;
        mFragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        FyberOffersFragment fyberOffersFragment = new FyberOffersFragment();
        startFragment.addToBackStack(OFFERS_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, fyberOffersFragment, OFFERS_FRAGMENT_TAG);
        startFragment.commit();
    }

    private void showNotOffersFragment(OfferResponse response) {
        mFragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction startFragment = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(NoOffersFragment.MESSAGE, response.getMessage());
        NoOffersFragment noOffersFragment = new NoOffersFragment();
        noOffersFragment.setArguments(bundle);
        startFragment.addToBackStack(NO_OFFERS_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, noOffersFragment, NO_OFFERS_FRAGMENT_TAG);
        startFragment.commit();
    }

    @Override
    public List<Offer> getOffers() {
        return mLastResponse.getOffers();
    }

    @Override
    public void showDetails(Offer offer) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        OfferDetailDialogFragment addParticipantDialog = new OfferDetailDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(OfferDetailDialogFragment.OFFER, offer);
        addParticipantDialog.setArguments(bundle);
        addParticipantDialog.show(fragmentManager, OFFER_DETAIL_DIALOG_TAG);

    }
}
