package codingbad.com.fyberchallenge.model;

/**
 * Created by ayi on 11/11/15.
 */
public class FyberParameter<T> {
    private T mValue;
    private String mParameterName;

    public FyberParameter(T value, String parameterName) {
        mValue = value;
        mParameterName = parameterName;
    }

    public String getParameterName() {
        return mParameterName;
    }

    public String getValue() {
        // TODO: check if toString is enough for all parameters
        return mValue.toString();
    }
}
