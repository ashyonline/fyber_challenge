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
public class FyberErrorFragment extends AbstractFragment<NoOffersFragment.Callbacks> {

    public static final String CODE = "error_code";
    public static final String MESSAGE = "error_message";

    private String mErrorMessage;
    private int mErrorCode;

    @InjectView(R.id.fragment_fyber_error_message)
    private TextView mErrorMessageTextView;

    @InjectView(R.id.fragment_fyber_error_code)
    private TextView mErrorCodeTextView;

    public FyberErrorFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mErrorCode = getArguments().getInt(CODE);
        mErrorMessage = getArguments().getString(MESSAGE);
        return inflater.inflate(R.layout.fragment_fyber_error, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mErrorCodeTextView.setText(String.valueOf(mErrorCode));
        mErrorMessageTextView.setText(mErrorMessage);
    }

    public interface Callbacks {
    }


}
