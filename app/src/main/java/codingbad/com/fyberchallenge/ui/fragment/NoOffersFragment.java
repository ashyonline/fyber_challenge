package codingbad.com.fyberchallenge.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import codingbad.com.fyberchallenge.R;
import roboguice.inject.InjectView;

/**
 * Created by ayi on 11/12/15.
 */
public class NoOffersFragment extends AbstractFragment<NoOffersFragment.Callbacks> {
    public static final String MESSAGE = "response_message";
    private String mResponseMessage;

    @InjectView(R.id.fragment_no_offers_message)
    private TextView mNoOffersMessage;

    public NoOffersFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mResponseMessage = getArguments().getString(MESSAGE);
        return inflater.inflate(R.layout.fragment_no_offers, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNoOffersMessage.setText(mResponseMessage);
    }

    public interface Callbacks {
    }
}
