package codingbad.com.fyberchallenge.model;

import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by ayi on 11/11/15.
 */
public class OptionalTextViewValidator extends TextViewValidator {
    private final CheckBox mCheckbox;

    public OptionalTextViewValidator(CheckBox checkbox, TextView textView, String errorMessage) {
        super(textView, errorMessage);
        this.mCheckbox = checkbox;
    }

    public boolean validate() {
        if (mCheckbox.isChecked()) {
            return super.validate();
        } else {
            mTextView.setError(null);
            return true;
        }
    }
}
