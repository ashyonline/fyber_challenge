package codingbad.com.fyberchallenge.model;

import android.widget.TextView;

/**
 * Created by ayi on 11/11/15.
 */
public class TextViewValidator {
    protected final TextView mTextView;
    protected final String mErrorMessage;

    public TextViewValidator(TextView textView, String errorMessage) {
        this.mTextView = textView;
        this.mErrorMessage = errorMessage;
    }

    public boolean validate() {
        if (!validateFieldIsNotEmpty()) {
            mTextView.setError(mErrorMessage);
            return false;
        }

        mTextView.setError(null);
        return true;
    }

    protected boolean validateFieldIsNotEmpty() {
        return !mTextView.getText().toString().trim().isEmpty();
    }

}
