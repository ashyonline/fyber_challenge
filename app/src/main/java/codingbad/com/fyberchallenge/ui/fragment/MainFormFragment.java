package codingbad.com.fyberchallenge.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.inject.Inject;
import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import codingbad.com.fyberchallenge.BuildConfig;
import codingbad.com.fyberchallenge.R;
import codingbad.com.fyberchallenge.model.FyberFormModel;
import codingbad.com.fyberchallenge.model.OfferResponse;
import codingbad.com.fyberchallenge.model.validator.OptionalCommaSeparatedTextViewValidator;
import codingbad.com.fyberchallenge.model.validator.OptionalTextViewValidator;
import codingbad.com.fyberchallenge.model.validator.TextViewValidator;
import codingbad.com.fyberchallenge.otto.OttoBus;
import codingbad.com.fyberchallenge.tasks.GetFyberOffersTask;
import codingbad.com.fyberchallenge.ui.view.LoadingIndicatorView;
import roboguice.inject.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFormFragment extends AbstractFragment<MainFormFragment.Callbacks> implements View.OnClickListener {
    // hardcoded since I don't have an account
    private static final String PS_TIME = "1312211903";

    @Inject
    private LoadingIndicatorView mLoadingIndicator;

    @Inject
    OttoBus ottoBus;

    @InjectView(R.id.fragment_main_form_format)
    private Spinner mFormatSpinner;
    @InjectView(R.id.fragment_main_form_appid)
    private EditText mAppid;
    @InjectView(R.id.fragment_main_form_uid)
    private EditText mUid;
    @InjectView(R.id.fragment_main_form_locale)
    private Spinner mLocale;
    @InjectView(R.id.fragment_main_form_os_version)
    private TextView mOsVersion;
    @InjectView(R.id.fragment_main_form_google_ad_id)
    private TextView mGoogleAdId;
    @InjectView(R.id.fragment_main_form_google_ad_limited)
    private TextView mIsAdTrackingLimited;
    @InjectView(R.id.fragment_main_form_advanced)
    private TextView mAdvancedButton;

    // Optional parameters:
    @InjectView(R.id.fragment_main_form_advanced_options)
    private LinearLayout mAdvancedOptions;
    @InjectView(R.id.fragment_main_form_ip)
    private EditText mIpAddress;
    @InjectView(R.id.fragment_main_form_custom_parameters)
    private EditText mCustomParameters;
    @InjectView(R.id.fragment_main_form_page)
    private EditText mPage;
    @InjectView(R.id.fragment_main_form_offer_types)
    private EditText mOfferTypes;
    @InjectView(R.id.fragment_main_form_ps_time)
    private TextView mPsTime;
    @InjectView(R.id.fragment_main_form_device)
    private TextView mDeviceType;
    // Checkboxes to decide if optional parameter should be sent
    @InjectView(R.id.send_ip)
    private CheckBox mSendIp;
    @InjectView(R.id.send_custom_parameters)
    private CheckBox mSendCustomParameters;
    @InjectView(R.id.send_page)
    private CheckBox mSendPage;
    @InjectView(R.id.send_offer_types)
    private CheckBox mSendOfferTypes;
    @InjectView(R.id.send_ps_time)
    private CheckBox mSendPsTime;
    @InjectView(R.id.send_device)
    private CheckBox mSendDevice;
    private FyberFormModel mFyberFormModel;
    private ViewGroup mViewGroup;

    public MainFormFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_form, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupFormatDropDown();
        setupLocaleDropDown();
        setupOsVersion();
        setupeGoogleAdInfo();
        setupAdvancedParameters();
        setupLoadingIndicator(view);
    }

    private void setupLoadingIndicator(View view) {
        mViewGroup = (ViewGroup) view;
        mLoadingIndicator.attach(mViewGroup);
    }

    @Override
    public void onStart() {
        super.onStart();
        ottoBus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        ottoBus.unregister(this);
        mLoadingIndicator.dismiss();
        mViewGroup.removeView(mLoadingIndicator);
    }

    private void submitFyberForm() {
        String format;
        int appid;
        String uid;
        String locale;
        String osVersion;
        long timestamp;
        String googleAdId;
        Boolean isLimitAdTrackingEnabled;
        String ip;
        String customParameters;
        Integer page;
        String offerTypes;
        Long psTime;
        String device;

        Boolean error = false;

        // TODO: think if mFormatSpinner and mLocale need validation

        // validate appid (it can only have numbers, so checking if not empty should be enough)
        if (!new TextViewValidator(mAppid, getString(R.string.field_should_not_be_empty)).validate()) {
            error = true;
        }

        // validate uid (it can have text, so checking if not empty should be enough)
        if (!new TextViewValidator(mUid, getString(R.string.field_should_not_be_empty)).validate()) {
            error = true;
        }

        // Optional parameters
        if (!new OptionalTextViewValidator(mSendIp, mIpAddress, getString(R.string.field_should_not_be_empty_or_unchecked)).validate()) {
            error = true;
        }

        if (!new OptionalCommaSeparatedTextViewValidator(mSendCustomParameters, mCustomParameters, getString(R.string.comma_separated_field_should_not_be_empty_or_unchecked)).validate()) {
            error = true;
        }

        if (!new OptionalCommaSeparatedTextViewValidator(mSendCustomParameters, mCustomParameters, getString(R.string.comma_separated_field_should_not_be_empty_or_unchecked)).validate()) {
            error = true;
        }

        if (!new OptionalTextViewValidator(mSendPage, mPage, getString(R.string.field_should_not_be_empty_or_unchecked)).validate()) {
            error = true;
        }

        if (!new OptionalCommaSeparatedTextViewValidator(mSendOfferTypes, mOfferTypes, getString(R.string.comma_separated_field_should_not_be_empty_or_unchecked)).validate()) {
            error = true;
        }

        if (!error) {
            format = mFormatSpinner.getSelectedItem().toString();
            appid = Integer.parseInt(mAppid.getText().toString());
            uid = mUid.getText().toString();
            locale = mLocale.getSelectedItem().toString();
            osVersion = mOsVersion.getText().toString();
            timestamp = System.currentTimeMillis() / 1000L;

            googleAdId = mGoogleAdId.getText().toString();
            isLimitAdTrackingEnabled = Boolean.parseBoolean(mIsAdTrackingLimited.getText().toString());

            // populate optional parameters only if checkbox is checked
            ip = mSendIp.isChecked() ? mIpAddress.getText().toString() : null;

            customParameters = mSendCustomParameters.isChecked() ? mCustomParameters.getText().toString() : null;
            page = mSendPage.isChecked() ? Integer.parseInt(mPage.getText().toString()) : null;
            offerTypes = mSendOfferTypes.isChecked() ? mOfferTypes.getText().toString() : null;
            psTime = mSendPsTime.isChecked() ? Long.parseLong(mPsTime.getText().toString()) : null;
            device = mSendDevice.isChecked() ? mDeviceType.getText().toString() : null;

            // I don't really understand where I should get the ps_time value
            // mOsVersion, mGoogleAdId, mIsAdTrackingLimited, psTime and device shouldn't be empty

            mFyberFormModel = new FyberFormModel(format,
                    appid,
                    uid,
                    locale,
                    osVersion,
                    timestamp,
                    googleAdId,
                    isLimitAdTrackingEnabled,
                    ip,
                    customParameters,
                    page,
                    offerTypes,
                    psTime,
                    device
            );

            // TODO: remove. This is here for debug purposes
            Log.d("Request", BuildConfig.HOST + "?" + mFyberFormModel.getRequestBody());

            mLoadingIndicator.show();
            mFyberFormModel.submit(this.getActivity());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            submitFyberForm();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupAdvancedParameters() {
        mAdvancedButton.setOnClickListener(this);
    }

    private void showAdvancedOptions() {
        initializeOptionals();
        mAdvancedOptions.setVisibility(View.VISIBLE);
        mAdvancedButton.setVisibility(View.GONE);
    }

    private void initializeOptionals() {
        mIpAddress.setText(getIpAddress());
        // TODO: initialize mPsTime
        mPsTime.setText(PS_TIME);
        if (isTablet()) {
            mDeviceType.setText(getString(R.string.tablet));
        } else {
            mDeviceType.setText(getString(R.string.phone));
        }
    }

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    public String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                for (Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
                    InetAddress inetAddress = inetAddresses.nextElement();

                    // get IPV4 format
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.error_getting_ip), Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    private void setupeGoogleAdInfo() {
        new GetAdvertisingIdInfo().execute();
    }

    private void setupOsVersion() {
        mOsVersion.setText(android.os.Build.VERSION.RELEASE);
    }

    private void setupLocaleDropDown() {

        // get all available locales and populate the spinner
        Locale[] availableLocales = Locale.getAvailableLocales();
        Set<String> localeLanguages = new HashSet<>();

        for (Locale locale : availableLocales) {
            localeLanguages.add(locale.getLanguage());
        }

        List<String> languages = new ArrayList<>();
        languages.addAll(localeLanguages);
        Collections.sort(languages);

        ArrayAdapter<String> localeAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, languages);
        mLocale.setAdapter(localeAdapter);

        // set default as the one found in the system
        Locale currentLocale = getResources().getConfiguration().locale;
        String selectedLanguage = currentLocale.getLanguage();

        if (!selectedLanguage.isEmpty()) {
            mLocale.setSelection(localeAdapter.getPosition(selectedLanguage));
        }
    }

    private void setupFormatDropDown() {
        ArrayAdapter<CharSequence> formatsAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.formats, android.R.layout.simple_spinner_item);
        formatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFormatSpinner.setAdapter(formatsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_main_form_advanced:
                showAdvancedOptions();
                break;
        }
    }

    /**
     * Otto events
     */
    @Subscribe
    public void onFormSubmitted(GetFyberOffersTask.Event event) {
        mLoadingIndicator.dismiss();
        callbacks.onFormSubmittedSuccessfully(event.getResult());
    }

    @Subscribe
    public void onFormSubmittedError(GetFyberOffersTask.FyberError error) {
        mLoadingIndicator.dismiss();
        callbacks.onFormSubmittedWithError(error);
    }

    public interface Callbacks {
        void onFormSubmittedSuccessfully(OfferResponse response);
        void onFormSubmittedWithError(GetFyberOffersTask.FyberError error);
    }

    private class GetAdvertisingIdInfo extends AsyncTask<Void, Void, Void> {
        private String mErrorMsg;
        private String mGoogleAdvertisingId = getString(R.string.google_ad_id_example);
        private boolean mIsLimited = true;

        @Override
        protected Void doInBackground(Void... params) {


            try {
                mGoogleAdvertisingId = AdvertisingIdClient.getAdvertisingIdInfo(getActivity()).getId();
                mIsLimited = AdvertisingIdClient.getAdvertisingIdInfo(getActivity()).isLimitAdTrackingEnabled();
            } catch (IOException e) {
                mErrorMsg = getString(R.string.google_ad_id_io_exception);
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
                mErrorMsg = getString(R.string.google_ad_id_gps_not_available_exception);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
                mErrorMsg = getString(R.string.google_ad_id_gps_repairable_exception);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mGoogleAdId.setText(mGoogleAdvertisingId);
            mIsAdTrackingLimited.setText(String.valueOf(mIsLimited));

            if (mErrorMsg != null) {
                Toast.makeText(getContext(), mErrorMsg, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
