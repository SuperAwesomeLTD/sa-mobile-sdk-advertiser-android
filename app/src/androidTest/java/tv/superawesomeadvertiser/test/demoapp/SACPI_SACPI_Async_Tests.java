package tv.superawesomeadvertiser.test.demoapp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.sdk.advertiser.SAVerifyInstall;
import tv.superawesome.sdk.advertiser.install.SAOnce;

public class SACPI_SACPI_Async_Tests extends ActivityInstrumentationTestCase2<MainActivity> {

    public SACPI_SACPI_Async_Tests () {
        super("tv.superawesome.sacpidemo", MainActivity.class);
    }

    @LargeTest
    public void testSACPI_sendInstallEvent1 () throws Throwable {

        final CountDownLatch signal = new CountDownLatch(2);

        // create a new session (staging) object
        final SASession session = new SASession(getActivity());
        session.setConfigurationStaging();

        // always reset the CPI
        final SAOnce once = new SAOnce(getActivity());
        once.resetCPISent();

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
                network.sendGET(getActivity(), clickUrl, clickQuery, clickHeader, new SANetworkInterface() {
                    @Override
                    public void saDidGetResponse(int i, String s, boolean b) {
                        assertTrue(b);
                        signal.countDown();
                    }
                });
            }
        });

        // call the whole CPI process:
        // - as if I was running this from the app that's just been installed "tv.superawesome.demoapp",
        // - see if the main CPI method executes correctly against a new click
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {

                SAVerifyInstall.getInstance().handleInstall(getActivity(), session, "tv.superawesome.demoapp", new SAVerifyInstall.Interface() {
                    @Override
                    public void saDidCountAnInstall(boolean success) {
                        assertTrue(success);
                        assertTrue(once.isCPISent());
                        signal.countDown();
                    }
                });
            }
        });

        signal.await();

    }

    @LargeTest
    public void testSACPI_sendInstallEvent2 () throws Throwable {

        final CountDownLatch signal = new CountDownLatch(2);

        // create a new session (staging) object
        final SASession session = new SASession(getActivity());
        session.setConfigurationStaging();

        // always reset the CPI
        final SAOnce once = new SAOnce(getActivity());
        once.resetCPISent();

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
                network.sendGET(getActivity(), clickUrl, clickQuery, clickHeader, new SANetworkInterface() {
                    @Override
                    public void saDidGetResponse(int i, String s, boolean b) {
                        assertTrue(b);
                        signal.countDown();
                    }
                });
            }
        });

        // call the whole CPI process:
        // - as if I was running this from another app that's got nothing to do with
        //   "tv.superawesome.demoapp"
        // - see if the main CPI method executes correctly, and for this other app doesn't
        //   return "true", but "false"
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                SAVerifyInstall.getInstance().handleInstall(getActivity(), session, "some.other.app", new SAVerifyInstall.Interface() {
                    @Override
                    public void saDidCountAnInstall(boolean success) {
                        assertFalse(success);
                        assertTrue(once.isCPISent());
                        signal.countDown();
                    }
                });
            }
        });

        signal.await();

    }

}
