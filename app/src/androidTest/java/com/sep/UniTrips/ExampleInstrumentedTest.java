package com.sep.UniTrips;

import android.app.Instrumentation;
import android.app.Notification;
import android.app.UiAutomation;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.StringContains.containsString;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends TestCase {

    private UiDevice mUIDevice = null;
    private Context mContext = null;
    private Instrumentation mInstrumentation = null;
    String APP = "com.sep.UniTrips";
    String toastMessage;
    long toastOccurTime;

    @Before
    public void setUp() throws RemoteException {
        mUIDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());  //get Device Object
        mContext = InstrumentationRegistry.getContext();

        if (!mUIDevice.isScreenOn()) {  //awake screen
            mUIDevice.wakeUp();
        }
        initToastListener();
    }

    private void initToastListener() {
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
        mInstrumentation.getUiAutomation().setOnAccessibilityEventListener(new UiAutomation.OnAccessibilityEventListener() {
            @Override
            public void onAccessibilityEvent(AccessibilityEvent event) {

                if (event.getEventType() != AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
                    return;
                }
                String sourcePackageName = (String) event.getPackageName();
                //get event detail
                Parcelable parcelable = event.getParcelableData();
                //get notification message (toast and notification)
                if (!(parcelable instanceof Notification)) {
                    toastMessage = (String) event.getText().get(0);
                    toastOccurTime = event.getEventTime();
                    Log.i("Info", "Latest Toast Message: " + toastMessage + " [Time: " + toastOccurTime + ", Source: " + sourcePackageName + "]");
                }
            }
        });
    }

    /***
     *
     * test for signup function
     * @throws UiObjectNotFoundException
     *
     */
    @Test
    public void test001_SignUp() throws UiObjectNotFoundException {
        Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(APP);  //启动app
        mContext.startActivity(myIntent);
        mUIDevice.waitForWindowUpdate(APP, 5 * 1000);

        UiObject signin = mUIDevice.findObject(new UiSelector().text("Sign up"));

        signin.clickAndWaitForNewWindow();
        mUIDevice.pressBack();
        UiObject email = mUIDevice.findObject(new UiSelector().resourceId(APP + ":id/signUpEmailEt"));
        UiObject password = mUIDevice.findObject(new UiSelector().resourceId(APP + ":id/signUpPasswordEt"));
        UiObject passwordconf = mUIDevice.findObject(new UiSelector().resourceId(APP + ":id/confirmedPasswordEt"));
        UiObject button = mUIDevice.findObject(new UiSelector().resourceId(APP + ":id/sign_up_button"));

        /*Test for different password input*/
        email.setText("sinoreimu@gmail.com");
        password.setText("123456");
        passwordconf.setText("1234567");
        button.click();
        UiObject emailHintf = mUIDevice.findObject(new UiSelector().text("Password and confirm password do not match!"));
        Assert.assertNotNull("Not give hint to different password", emailHintf);


        /*Test for used email input*/
        email.setText("su@geekzh.com");
        password.setText("123456");
        passwordconf.setText("123456");
        button.click();
        long startTimeMillis = SystemClock.uptimeMillis();
        boolean isSuccessfulCatchToast;
        while (true) {
            long currentTimeMillis = SystemClock.uptimeMillis();
            long elapsedTimeMillis = currentTimeMillis - startTimeMillis;
            if (elapsedTimeMillis > 10000L) {
                isSuccessfulCatchToast = false;
                break;
            }
            if (toastOccurTime > startTimeMillis) {
                if ("You input email address has been used. Please try again...".equals(toastMessage)) {
                    isSuccessfulCatchToast = true;
                    break;
                } else {
                    isSuccessfulCatchToast = false;
                }
            }
        }
        Assert.assertTrue("not give toast for used email address", isSuccessfulCatchToast);


    }

    /*Test for inputcheck, errorhint and page jump*/
    @Test
    public void test002_LoginAndForget() throws UiObjectNotFoundException, InterruptedException {
        Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(APP);  //启动app
        mContext.startActivity(myIntent);
        mUIDevice.waitForWindowUpdate(APP, 5 * 1000);

        UiObject signin = mUIDevice.findObject(new UiSelector().text("Sign in"));

        signin.clickAndWaitForNewWindow();
        mUIDevice.pressBack();


        /*Test for forget password function*/
        UiObject forget = mUIDevice.findObject(new UiSelector().text("Forgot your password?"));
        forget.clickAndWaitForNewWindow();
        UiObject emailf = mUIDevice.findObject(new UiSelector().resourceId(APP + ":id/restPassword_email_Et"));
        UiObject buttonf = mUIDevice.findObject(new UiSelector().text("SUBMIT"));
        emailf.setText("s");
        buttonf.click();
        UiObject emailHintf = mUIDevice.findObject(new UiSelector().text("Invalid email address!"));
        Assert.assertNotNull("Not give hint to invalid email address", emailHintf);

        emailf.setText("SS@S.S");
//        UiObject subBtn = mUIDevice.findObject(new UiSelector().text("CANCEL"));
        buttonf.click();
        long startTimeMillis = SystemClock.uptimeMillis();
        boolean isSuccessfulCatchToast;
        while (true) {
            long currentTimeMillis = SystemClock.uptimeMillis();
            long elapsedTimeMillis = currentTimeMillis - startTimeMillis;
            if (elapsedTimeMillis > 5000L) {
                Log.i("AAA", "OverTime!");
                isSuccessfulCatchToast = false;
                break;
            }
            if (toastOccurTime > startTimeMillis) {
                isSuccessfulCatchToast = "Fail".equals(toastMessage);
                break;
            }
        }
        Assert.assertTrue("not give failed for unknown email", isSuccessfulCatchToast);

        emailf.setText("su@geekzh.com");
        buttonf.click();
        startTimeMillis = SystemClock.uptimeMillis();
        while (true) {
            long currentTimeMillis = SystemClock.uptimeMillis();
            long elapsedTimeMillis = currentTimeMillis - startTimeMillis;
            if (elapsedTimeMillis > 5000L) {
                Log.i("AAA", "OverTime!");
                isSuccessfulCatchToast = false;
                break;
            }
            if (toastOccurTime > startTimeMillis) {
                isSuccessfulCatchToast = "A reset password link has been sent to your email address! Please check your email!".equals(toastMessage);
                break;
            }
        }
        Assert.assertTrue("send error", isSuccessfulCatchToast);


        UiObject buttonc = mUIDevice.findObject(new UiSelector().text("CANCEL"));
        buttonc.clickAndWaitForNewWindow();
        mUIDevice.pressBack();


        /*Test for sign in function*/
        UiObject email = mUIDevice.findObject(new UiSelector().resourceId(APP + ":id/email"));
        UiObject password = mUIDevice.findObject(new UiSelector().resourceId(APP + ":id/password"));
        UiObject btnSignin = mUIDevice.findObject(new UiSelector().resourceId(APP + ":id/sign_in_button"));


        /*Test for unregister email input*/
        email.setText("sinoreimu@gamil.com");
        password.setText("123456");
        btnSignin.click();

        startTimeMillis = SystemClock.uptimeMillis();
        while (true) {
            long currentTimeMillis = SystemClock.uptimeMillis();
            long elapsedTimeMillis = currentTimeMillis - startTimeMillis;
            if (elapsedTimeMillis > 5000L) {
                Log.i("AAA", "OverTime!");
                isSuccessfulCatchToast = false;
                break;
            }
            if (toastOccurTime > startTimeMillis) {
                isSuccessfulCatchToast = "The input email address does not exist! Please try again...".equals(toastMessage);
                break;
            }
        }
        Assert.assertTrue("Not give hint for address does not exist", isSuccessfulCatchToast);


        /*Test for invalid email address input*/
        email.setText("123123");
        btnSignin.click();
        UiObject emailHint = mUIDevice.findObject(new UiSelector().text("Invalid email address!"));
        Assert.assertNotNull("Not give hint to invalid email address", emailHint);


        /*Test for empty input*/
        email.setText("");
        password.setText("");
        btnSignin.click();
        UiObject emptyHint = mUIDevice.findObject(new UiSelector().text("This field is required"));
        Assert.assertNotNull("Not give hint to empty email or password", emptyHint);

        email.setText("su@geekzh.com");
        password.setText("");
        btnSignin.click();
        emptyHint = mUIDevice.findObject(new UiSelector().text("This field is required"));
        Assert.assertNotNull("Not give hint to empty email or password", emptyHint);


        /*Test for short password input*/
        email.setText("su@geekzh.com");
        password.setText("123");
        btnSignin.click();
        emptyHint = mUIDevice.findObject(new UiSelector().text("Invalid password! Possword should be at least 6 characters."));
        Assert.assertNotNull("Not give hint to short password", emptyHint);


        /*Test for error password input*/
        email.setText("su@geekzh.com");
        password.setText("123asdf");
        btnSignin.click();
        startTimeMillis = SystemClock.uptimeMillis();
        while (true) {
            long currentTimeMillis = SystemClock.uptimeMillis();
            long elapsedTimeMillis = currentTimeMillis - startTimeMillis;
            if (elapsedTimeMillis > 5000L) {
                isSuccessfulCatchToast = false;
                break;
            }
            if (toastOccurTime > startTimeMillis) {
                isSuccessfulCatchToast = "The input email and password do not match! Please try again...".equals(toastMessage);
                break;
            }
        }
        Assert.assertTrue("Not give hint for password doesn't match", isSuccessfulCatchToast);


        /*Test for current password input*/
        email.setText("su@geekzh.com");
        password.setText("123456");
        boolean inNewActivity = btnSignin.clickAndWaitForNewWindow();
        Assert.assertTrue("Can not enter MainActivity", inNewActivity);


    }

    /**
     *
     * Test for mainpage refresh button
     *
     * */
    @Test
    public void test003_MainPageTest() throws UiObjectNotFoundException {
        Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(APP);
        mContext.startActivity(myIntent);
        mUIDevice.waitForWindowUpdate(APP, 5 * 1000);

        UiObject refresh = mUIDevice.findObject(new UiSelector().resourceId(APP + ":id/refresh"));

        boolean isSuccessfulCatchToast;

        long startTimeMillis = SystemClock.uptimeMillis();
        refresh.click();

        //Enter the main page then click refresh then check if this toast right
        while (true) {
            Log.i("AAA", toastMessage);
            long currentTimeMillis = SystemClock.uptimeMillis();
            long elapsedTimeMillis = currentTimeMillis - startTimeMillis;
            if (elapsedTimeMillis > 5000L) {
                isSuccessfulCatchToast = false;
                break;
            }

            if (toastOccurTime > startTimeMillis) {
                isSuccessfulCatchToast = "do refresh".equals(toastMessage);
                break;
            }
        }
        Assert.assertTrue("refresh toast not show", isSuccessfulCatchToast);
    }

    /**
     *
     * Test for mainpage refresh button
     *
     * */
    @Test
    public void test004_Setting() throws UiObjectNotFoundException {
        Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(APP);
        mContext.startActivity(myIntent);
        mUIDevice.waitForWindowUpdate(APP, 5 * 1000);

        UiObject setting = mUIDevice.findObject(new UiSelector().text("setting"));
        setting.clickAndWaitForNewWindow();
        UiObject mp = mUIDevice.findObject(new UiSelector().text("My preference"));
        mp.clickAndWaitForNewWindow();
        //Enter the setting page and check if those text right
        onView(withId(R.id.transportSettingSpinner)).check(matches(withSpinnerText(containsString("Train"))));
        onView(withId(R.id.notification_Time_hours_spinner)).check(matches(withSpinnerText(containsString("HH"))));
        onView(withId(R.id.notification_time_min_spinner)).check(matches(withSpinnerText(containsString("MM"))));
        onView(withId(R.id.arrival_Time_hours_spinner)).check(matches(withSpinnerText(containsString("HH"))));
        onView(withId(R.id.arrival_time_min_spinner)).check(matches(withSpinnerText(containsString("MM"))));

    }

    /**
     *
     * Test for main page location
     *
     * */
    @Test
    public void test005_Location() throws UiObjectNotFoundException {

        // when main page located an element text will be set to "located"
        // then check if this element text become "located"
        // or no location data set in main page

        Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(APP);
        mContext.startActivity(myIntent);
        mUIDevice.waitForWindowUpdate(APP, 5 * 1000);

        UiObject location = mUIDevice.findObject(new UiSelector().resourceId(APP + ":id/location_test"));

        boolean isSuccessfulCatchToast;

        long startTimeMillis = SystemClock.uptimeMillis();

        while (true) {
            long currentTimeMillis = SystemClock.uptimeMillis();
            long elapsedTimeMillis = currentTimeMillis - startTimeMillis;
            if (elapsedTimeMillis > 30000L) {
                isSuccessfulCatchToast = false;
                break;
            }

            if (toastOccurTime > startTimeMillis) {
                if(location.getText().equals("located")) {
                    isSuccessfulCatchToast = true;
                    break;
                }
            }
        }
        Assert.assertTrue("can not get location in 30 seconds", isSuccessfulCatchToast);
    }

}
