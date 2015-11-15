package codingbad.com.fyberchallenge;

import android.os.Bundle;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import codingbad.com.fyberchallenge.ui.activity.MainActivity;
import codingbad.com.fyberchallenge.ui.fragment.NoOffersFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by ayi on 11/14/15.
 */
@RunWith(AndroidJUnit4.class)
public class NoOffersFragmentTest {
    private static final String ERROR_MESSAGE = "Successful request, but no offers are currently available for this user.";
    private static final String NO_OFFERS_FRAGMENT_TAG = "tag";
    @Rule
    public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    private NoOffersFragment startFragment() {
        FragmentTransaction startFragment = main.get().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(NoOffersFragment.MESSAGE, ERROR_MESSAGE);
        NoOffersFragment noOffersFragment = new NoOffersFragment();
        noOffersFragment.setArguments(bundle);
        startFragment.addToBackStack(NO_OFFERS_FRAGMENT_TAG);
        startFragment.replace(R.id.fragment, noOffersFragment, NO_OFFERS_FRAGMENT_TAG);
        startFragment.commit();

        return noOffersFragment;
    }

    @Test
    public void testErrorFragment() {
        startFragment();
        onView(withText(ERROR_MESSAGE)).check(ViewAssertions.matches(isDisplayed()));
    }

}
