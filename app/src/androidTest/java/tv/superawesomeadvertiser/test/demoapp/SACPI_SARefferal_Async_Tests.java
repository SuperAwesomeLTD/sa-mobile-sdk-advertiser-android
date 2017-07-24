package tv.superawesomeadvertiser.test.demoapp;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesomeadvertiser.sdk.referral.SAReceiver;

public class SACPI_SARefferal_Async_Tests extends ActivityInstrumentationTestCase2<MainActivity> {

    public SACPI_SARefferal_Async_Tests() {
        super("tv.superawesome.sacpidemo", MainActivity.class);
    }

    @LargeTest
    public void testSAReferral_onReceive () throws Throwable {

        final CountDownLatch signal = new CountDownLatch(2);

        // create a new session (staging) object
        final SASession session = new SASession(getActivity());
        session.setConfigurationStaging();

        // first generate a new click on the ad server coming from this app,
        // "tv.superawesome.sacpidemo" as if to install the "tv.superawesome.demoapp", which is
        // the target that was setup in the dashboard
        final String clickUrl = session.getBaseUrl() + "/click";
        final JSONObject clickQuery = SAJsonParser.newObject(
                "placement", 588,
                "bundle", session.getPackageName(),
                "creative", 5778,
                "line_item", 1063,
                "ct", session.getConnectionType(),
                "sdkVersion", "0.0.0",
                "rnd", session.getCachebuster());

        final SANetwork network = new SANetwork();

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                network.sendGET(getActivity(), clickUrl, clickQuery, new JSONObject(), new SANetworkInterface() {
                    @Override
                    public void saDidGetResponse(int i, String s, boolean b) {
                        assertTrue(b);
                        signal.countDown();
                    }
                });
            }
        });

        // now after the click is sent pretend that the user installed the app alongside some
        // referral data (passed through an intent)
        // then check to see if the server can work with that
        final Intent intent = new Intent();
        intent.putExtra("referrer", "utm_source=1&utm_campaign=1218&utm_term=1063&utm_content=5778&utm_medium=588");

        final SAReceiver referral = new SAReceiver(getActivity());

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {

                referral.sendReferralEvent(intent, new SAReceiver.SAReceiverInterface() {
                    @Override
                    public void saDidSendReferralData(boolean success) {
                        assertTrue(success);
                        signal.countDown();
                    }
                });

            }
        });

        signal.await();
    }
}
