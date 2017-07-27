package tv.superawesomeadvertiser.test.demoapp;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.sdk.advertiser.pack.SACheck;

public class SACPI_SACheck_Tests extends ApplicationTestCase<Application> {

    public SACPI_SACheck_Tests() {
        super(Application.class);
    }

    @SmallTest
    public void testSACheck_Create1 () {

        SACheck check = new SACheck(getContext());
        assertNotNull(check);

    }

    @SmallTest
    public void testSACheck_getCheckInstallUrl1_1 () {

        SACheck check = new SACheck(getContext());
        assertNotNull(check);

        SASession session = new SASession(getContext());
        session.setConfigurationProduction();

        String expected = "https://ads.superawesome.tv/v2/checkinstall";

        String result = check.getCheckInstallUrl(session);
        assertNotNull(result);
        assertEquals(expected, result);

    }

    @SmallTest
    public void testSACheck_getCheckInstallUrl1_2 () {

        SACheck check = new SACheck(getContext());
        assertNotNull(check);

        SASession session = new SASession(getContext());
        session.setConfigurationStaging();

        String expected = "https://ads.staging.superawesome.tv/v2/checkinstall";

        String result = check.getCheckInstallUrl(session);

        assertEquals(expected, result);

    }

    @SmallTest
    public void testSACheck_getCheckInstallUrl1_3 () {

        SACheck check = new SACheck(getContext());
        assertNotNull(check);

        SASession session = new SASession(null);
        session.setConfigurationProduction();

        String expected = "https://ads.superawesome.tv/v2/checkinstall";

        String result = check.getCheckInstallUrl(session);
        assertNotNull(result);
        assertEquals(expected, result);

    }

    @SmallTest
    public void testSACheck_getCheckInstallUrl1_4 () {

        SACheck check = new SACheck(getContext());
        assertNotNull(check);

        String result = check.getCheckInstallUrl(null);

        assertNull(result);

    }

    @SmallTest
    public void testSACheck_getCheckInstallQuery1_1 () {

        SACheck check = new SACheck(getContext());
        assertNotNull(check);

        String expected = "com.test.installedapp";

        JSONObject query = check.getCheckInstallQuery("com.test.installedapp");

        assertNotNull(query);
        assertTrue(query.has("bundle"));
        assertNotNull(query.opt("bundle"));
        assertEquals(expected, query.opt("bundle"));
    }

    @SmallTest
    public void testSACheck_getCheckInstallQuery1_2 () {

        SACheck check = new SACheck(getContext());
        assertNotNull(check);

        JSONObject query = check.getCheckInstallQuery(null);

        assertNotNull(query);
        assertFalse(query.has("bundle"));
        assertNull(query.opt("bundle"));
    }

    @SmallTest
    public void testSACheck_parseServerResponse1_1 () {

        SACheck check = new SACheck(getContext());
        assertNotNull(check);

        List<String> expected = Arrays.asList("com.test.thisapp", "com.test.thatapp");
        List<String> response = check.parseServerResponse("[ \"com.test.thisapp\", \"com.test.thatapp\" ]");

        assertNotNull(response);
        assertEquals(expected.size(), response.size());
        assertTrue(response.get(0).equals("com.test.thisapp"));
        assertTrue(response.get(1).equals("com.test.thatapp"));

    }

    @SmallTest
    public void testSACheck_parseServerResponse1_2 () {

        SACheck check = new SACheck(getContext());
        assertNotNull(check);

        List<String> expected = new ArrayList<>();
        List<String> response = check.parseServerResponse("[ \"com.test.t////sahisap, \''sest.that ]");

        assertNotNull(response);
        assertEquals(expected.size(), response.size());

    }

    @SmallTest
    public void testSACheck_parseServerResponse1_3 () {

        SACheck check = new SACheck(getContext());
        assertNotNull(check);

        List<String> expected = new ArrayList<>();
        List<String> response = check.parseServerResponse(null);

        assertNotNull(response);
        assertEquals(expected.size(), response.size());

    }

    @SmallTest
    public void testSACheck_Create2 () {

        SACheck check = new SACheck(null);
        assertNotNull(check);

    }

    @SmallTest
    public void testSACheck_getCheckInstallUrl2_1 () {

        SACheck check = new SACheck(null);
        assertNotNull(check);

        SASession session = new SASession(null);
        session.setConfigurationProduction();

        String expected = "https://ads.superawesome.tv/v2/checkinstall";

        String result = check.getCheckInstallUrl(session);
        assertNotNull(result);
        assertEquals(expected, result);

    }

    @SmallTest
    public void testSACheck_getCheckInstallQuery2_1 () {

        SACheck check = new SACheck(null);
        assertNotNull(check);

        String expected = "com.test.installedapp";

        JSONObject query = check.getCheckInstallQuery("com.test.installedapp");

        assertNotNull(query);
        assertTrue(query.has("bundle"));
        assertNotNull(query.opt("bundle"));
        assertEquals(expected, query.opt("bundle"));
    }

    @SmallTest
    public void testSACheck_parseServerResponse2_1 () {

        SACheck check = new SACheck(null);
        assertNotNull(check);

        List<String> expected = Arrays.asList("com.test.thisapp", "com.test.thatapp");
        List<String> response = check.parseServerResponse("[ \"com.test.thisapp\", \"com.test.thatapp\" ]");

        assertNotNull(response);
        assertEquals(expected.size(), response.size());
        assertTrue(response.get(0).equals("com.test.thisapp"));
        assertTrue(response.get(1).equals("com.test.thatapp"));

    }
}
