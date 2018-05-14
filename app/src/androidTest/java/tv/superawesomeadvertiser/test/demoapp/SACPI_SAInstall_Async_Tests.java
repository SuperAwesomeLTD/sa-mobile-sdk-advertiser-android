package tv.superawesomeadvertiser.test.demoapp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.advertiser.install.SAInstall;

public class SACPI_SAInstall_Async_Tests extends ActivityInstrumentationTestCase2<MainActivity> {

    public SACPI_SAInstall_Async_Tests() {
        super("tv.superawesome.sacpidemo", MainActivity.class);
    }

    @LargeTest
    public void testSAInstall_sendInstallEventToServer () throws Throwable {

        final CountDownLatch signal = new CountDownLatch(2);

        final SAInstall install = new SAInstall(getActivity());
        assertNotNull(install);

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
        final JSONObject clickHeader = SAJsonParser.newObject("User-Agent", SAUtils.getUserAgent(getActivity()));

        final SANetwork network = new SANetwork();

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {

                network.sendGET(clickUrl, clickQuery, clickHeader, new SANetworkInterface() {
                    @Override
                    public void saDidGetResponse(int i, String s, boolean b) {
                        Log.d("SuperAwesome", s);
                        assertTrue(b);
                        signal.countDown();
                    }
                });
            }
        });

        // now that the click was generated
        // - skip the "checking" part since that's done in another test *and* we know the
        //   app that generated the click is "tv.superawesome.sacpidemo"
        // - send an install event with both the target & source packages and see if the
        //   ad server returns true, in that it recognizes a valid install
        final String targetPackage = "tv.superawesome.demoapp";
        final String sourcePackage = "tv.superawesome.sacpidemo";

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                install.sendInstallEventToServer(targetPackage, sourcePackage, session, new SAInstall.SAInstallInterface() {
                    @Override
                    public void saDidCountAnInstall(boolean success) {
                        assertTrue(success);
                        signal.countDown();
                    }
                });
            }
        });

        signal.await();
    }

}
