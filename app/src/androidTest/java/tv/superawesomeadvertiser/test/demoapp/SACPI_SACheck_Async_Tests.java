package tv.superawesomeadvertiser.test.demoapp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesomeadvertiser.sdk.pack.SACheck;


public class SACPI_SACheck_Async_Tests extends ActivityInstrumentationTestCase2<MainActivity> {

    public SACPI_SACheck_Async_Tests() {
        super("tv.superawesome.sacpidemo", MainActivity.class);
    }

    @LargeTest
    public void testSACheck_askServerForPackagesThatGeneratedThisInstall () throws Throwable {

        final CountDownLatch signal = new CountDownLatch(2);

        // create a new check object
        final SACheck check = new SACheck(getActivity());
        assertNotNull(check);

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

        // after the click has been executed, check to see that for "tv.superawesome.demoapp"
        // the ad server will return an array of one possible app, this one,
        // "tv.superawesome.sacpidemo", that has just triggered the click
        final String targetPackage = "tv.superawesome.demoapp";
//        final List<String> expectedPackages = Collections.singletonList("tv.superawesome.sacpidemo");
//        final String expectedPackage = "tv.superawesome.sacpidemo";

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {

                check.askServerForPackagesThatGeneratedThisInstall(targetPackage, session, new SACheck.SACheckInstallInterface() {
                    @Override
                    public void saDidGetListOfPackagesToCheck(List<String> packages) {

                        // test assumptions
                        assertNotNull(packages);
                        assertFalse(packages.isEmpty());
                        assertTrue(packages.size() > 0);

                        signal.countDown();

//                String firstPackage = packages.get(0);
//                assertNotNull(firstPackage);
//                assertEquals(expectedPackage, firstPackage);

                    }
                });
            }
        });

        signal.await();

    }
}
