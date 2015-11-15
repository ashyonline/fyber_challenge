package codingbad.com.fyberchallenge;

import android.content.pm.ActivityInfo;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import codingbad.com.fyberchallenge.ui.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by ayi on 11/13/15.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final String APPID = "111";
    private static final String UID = "one_uid";
    private static final String IP = "2.16.1.147";
    private static final String PARAMS = "campaign2";
    private static final String PAGE = "1";
    private static final String OFFER_TYPES = "112";

    @Rule
    public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    private static Matcher<? super View> hasErrorText() {
        return new ErrorTextMatcher();
    }

    /**
     * Check if Advanced options we being displayed, there keep being displayed after screen rotation
     */
    /*
    @Test
    public void testWhenDeviceRotatesAdvancedOptionsKeepDisplaying() {
        pressAdvancedButton();
        onView(withText(R.string.ip)).perform(scrollTo()).check(ViewAssertions.matches(isDisplayed()));
        rotateDevice();
        onView(R.id.fragment_main_form_ip).perform(scrollTo()).check(ViewAssertions.matches(isDisplayed()));
    }
    */
    public static ViewAction swipeDown() {
        return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.TOP_CENTER,
                GeneralLocation.BOTTOM_CENTER, Press.FINGER);
    }

    /**
     * This class shows some basic UI tests for illustrative purposes.
     * A complete UI test set would take more than a couple of hours.
     */

    @Test
    public void testSendButtonStatus() {
        onView(withId(R.id.action_send)).check(ViewAssertions.matches(isDisplayed()))
                .check(ViewAssertions.matches(isClickable()))
                .check(ViewAssertions.matches(isEnabled()));
    }

    @Test
    public void testStaticTexts() {
        // title
        testDisplayWithId(R.id.fragment_main_form_title);
        // subtitles
        testDisplayWithText(R.string.form_title);
        testDisplayWithText(R.string.format);
        testDisplayWithText(R.string.appid);
        testDisplayWithText(R.string.uid);
        testDisplayWithText(R.string.locale);
        testDisplayWithText(R.string.os_version);
        testDisplayWithText(R.string.google_ad_id);
        testDisplayWithText(R.string.google_ad_limited);
    }

    @Test
    public void testAdvancedButton() {
        onView(withId(R.id.fragment_main_form_advanced)).check(matches(isEnabled()))
                .check(ViewAssertions.matches(isClickable()));
    }

    @Test
    public void testCheckboxesCanBeChecked() {
        pressAdvancedButton();

        testCheckbox(R.id.send_ip);
        testCheckbox(R.id.send_custom_parameters);
        testCheckbox(R.id.send_page);
        testCheckbox(R.id.send_offer_types);
        testCheckbox(R.id.send_ps_time);
        testCheckbox(R.id.send_device);
    }

    private void pressAdvancedButton() {
        // press advanced button
        onView(withId(R.id.fragment_main_form_advanced)).perform(scrollTo()).perform(click());
    }

    private void testCheckbox(int id) {
        onView(withId(id)).perform(scrollTo()).check(matches(isDisplayed())).check(matches(isNotChecked())).perform(click()).check(matches(isChecked()));
    }

    @Test
    public void testSpinners() {
        testSpinner(R.id.fragment_main_form_format);
        testSpinner(R.id.fragment_main_form_locale);
    }

    private void testSpinner(int id) {
        onView(withId(id)).check(ViewAssertions.matches(isDisplayed()))
                .check(ViewAssertions.matches(isEnabled()));
    }

    @Test
    public void testEditFields() {
        testEditText(R.id.fragment_main_form_appid, APPID);
        testEditText(R.id.fragment_main_form_uid, UID);
        pressAdvancedButton();
        testEditText(R.id.fragment_main_form_ip, IP);
        testEditText(R.id.fragment_main_form_custom_parameters, PARAMS);
        testEditText(R.id.fragment_main_form_page, PAGE);
        testEditText(R.id.fragment_main_form_offer_types, OFFER_TYPES);
    }

    private void testEditText(int id, String text) {
        onView(withId(id)).perform(scrollTo()).check(ViewAssertions.matches(isDisplayed()))
                .perform(clearText()).perform(typeText(text)).check(ViewAssertions.matches(withText(text)));
    }

    @Test
    public void testMandatoryTextFieldsAreVisible() {
        onView(withId(R.id.fragment_main_form_os_version)).perform(scrollTo()).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.fragment_main_form_google_ad_id)).perform(scrollTo()).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.fragment_main_form_google_ad_limited)).perform(scrollTo()).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testInvalidIpLeadsToError() {
        onView(withId(R.id.fragment_main_form_advanced)).perform(scrollTo()).perform(click());
        onView(withId(R.id.send_ip)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.send_ip)).perform(click());

        checkEditTextError(R.id.fragment_main_form_ip);
        checkEditTextError(R.id.fragment_main_form_uid);
        checkEditTextError(R.id.fragment_main_form_appid);
    }

    @Test
    public void testNonMandatoryParameters() {
        pressAdvancedButton();
        ViewInteraction ipEditText = onView(withId(R.id.fragment_main_form_ip));
        ipEditText.perform(scrollTo(), clearText());
        ipEditText.check(ViewAssertions.matches(not(hasErrorText())));
        sendForm();
        onView(withId(R.id.send_ip)).perform(scrollTo()).perform(click());
        sendForm();
        ipEditText.check(ViewAssertions.matches(hasErrorText()));
    }

    private void checkEditTextError(int id) {
        ViewInteraction editText = onView(withId(id));
        editText.perform(clearText());
        sendForm();
        editText.check(ViewAssertions.matches(hasErrorText()));
    }

    private void sendForm() {
        onView(withId(R.id.action_send)).perform(click());
    }

    @Test
    public void testShouldShowMainFormTitle() {
        onView(withId(R.id.fragment_main_form_title)).check(ViewAssertions.matches(isDisplayed()));
    }

    public void setText(int id, String text) {
        onView(ViewMatchers.withId(id)).perform(scrollTo()).perform(typeText(text));
    }

    public void checkText(int id, String text) {
        onView(allOf(withId(id), isDisplayed())).check(matches(withText(text)));
    }

    private void testDisplayWithId(int id) {
        onView(withId(id)).perform(scrollTo()).check(ViewAssertions.matches(isDisplayed()));
    }

    private void testDisplayWithText(int textId) {
        onView(withText(textId)).perform(scrollTo()).check(ViewAssertions.matches(isDisplayed()));
    }

    /**
     * This test checks that what has been typed into EditText is keep after screen rotation
     * Incomplete, only showing some values.
     */
    @Test
    public void testWhenDeviceRotatesSameTextInputIsRetained() {
        setText(R.id.fragment_main_form_uid, UID);
        setText(R.id.fragment_main_form_appid, APPID);
        rotateDevice();
        checkText(R.id.fragment_main_form_uid, UID);
        checkText(R.id.fragment_main_form_appid, APPID);
    }

    private void rotateDevice() {
        main.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private static class ErrorTextMatcher extends TypeSafeMatcher<View> {
        private ErrorTextMatcher() {
        }

        @Override
        public boolean matchesSafely(View view) {
            if (!(view instanceof EditText)) {
                return false;
            }
            EditText editText = (EditText) view;
            return (editText.getError() != null) && !editText.getError().toString().isEmpty();
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("error is empty");
        }
    }
}
