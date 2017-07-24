package tv.superawesomeadvertiser.test.demoapp;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.json.JSONObject;

import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesomeadvertiser.sdk.install.SAInstall;

public class SACPI_SAInstall_Tests extends ApplicationTestCase<Application> {

    public SACPI_SAInstall_Tests() {
        super(Application.class);
    }


    @SmallTest
    public void testSAInstall_Create1 () {

        SAInstall install = new SAInstall(getContext());
        assertNotNull(install);

    }

    @SmallTest
    public void testSAInstall_getInstallUrl1_1 () {

        SAInstall install = new SAInstall(getContext());
        assertNotNull(install);

        SASession session = new SASession(getContext());
        session.setConfigurationProduction();

        String expected = "https://ads.superawesome.tv/v2/install";

        String result = install.getInstallUrl(session);

        assertEquals(expected, result);

    }

    @SmallTest
    public void testSAInstall_getInstallUrl1_2 () {

        SAInstall install = new SAInstall(getContext());
        assertNotNull(install);

        SASession session = new SASession(getContext());
        session.setConfigurationStaging();

        String expected = "https://ads.staging.superawesome.tv/v2/install";

        String result = install.getInstallUrl(session);

        assertEquals(expected, result);

    }

    @SmallTest
    public void testSAInstall_getInstallUrl1_3 () {

        SAInstall install = new SAInstall(getContext());
        assertNotNull(install);

        SASession session = new SASession(null);
        session.setConfigurationProduction();

        String expected = "https://ads.superawesome.tv/v2/install";

        String result = install.getInstallUrl(session);

        assertEquals(expected, result);

    }

    @SmallTest
    public void testSAInstall_getInstallUrl1_4 () {

        SAInstall install = new SAInstall(getContext());
        assertNotNull(install);

        String result = install.getInstallUrl(null);

        assertNull(result);

    }

    @SmallTest
    public void testSAInstall_getInstallQuery1_1 () {

        SAInstall install = new SAInstall(getContext());
        assertNotNull(install);

        JSONObject query = install.getInstallQuery("com.installed.app", "com.source.app");
        assertNotNull(query);

        String expected1 = "com.installed.app";
        String expected2 = "com.source.app";

        assertTrue(query.has("bundle"));
        assertTrue(query.has("sourceBundle"));
        assertEquals(expected1, query.opt("bundle"));
        assertEquals(expected2, query.opt("sourceBundle"));

    }

    @SmallTest
    public void testSAInstall_getInstallQuery1_2 () {

        SAInstall install = new SAInstall(getContext());
        assertNotNull(install);

        JSONObject query = install.getInstallQuery("com.installed.app", null);
        assertNotNull(query);

        String expected = "com.installed.app";

        assertTrue(query.has("bundle"));
        assertFalse(query.has("sourceBundle"));
        assertEquals(expected, query.opt("bundle"));
        assertNull(query.opt("sourceBundle"));

    }

    @SmallTest
    public void testSAInstall_getInstallQuery1_3 () {

        SAInstall install = new SAInstall(getContext());
        assertNotNull(install);

        JSONObject query = install.getInstallQuery(null, "com.source.app");
        assertNotNull(query);

        String expected = "com.source.app";

        assertFalse(query.has("bundle"));
        assertTrue(query.has("sourceBundle"));
        assertNull(query.opt("bundle"));
        assertEquals(expected, query.opt("sourceBundle"));

    }

    @SmallTest
    public void testSAInstall_getInstallQuery1_4 () {

        SAInstall install = new SAInstall(getContext());
        assertNotNull(install);

        JSONObject query = install.getInstallQuery(null, null);
        assertNotNull(query);

        assertFalse(query.has("bundle"));
        assertFalse(query.has("sourceBundle"));
        assertNull(query.opt("bundle"));
        assertNull(query.opt("sourceBundle"));

    }

    @SmallTest
    public void testSAInstall_getInstallHeader1_1 () {

        SAInstall install = new SAInstall(getContext());
        assertNotNull(install);

        JSONObject header = install.getInstallHeader();
        assertNotNull(header);

        String expected1 = "application/json";
        String expected2 = SAUtils.getUserAgent(getContext());

        assertTrue(header.has("Content-Type"));
        assertTrue(header.has("User-Agent"));
        assertEquals(expected1, header.opt("Content-Type"));
        assertEquals(expected2, header.opt("User-Agent"));

    }

    @SmallTest
    public void testSAInstall_parseServerResponse1_1 () {

        SAInstall install = new SAInstall(getContext());
        assertNotNull(install);

        boolean result = install.parseServerResponse("{ \"success\" : true }");
        assertTrue(result);
    }

    @SmallTest
    public void testSAInstall_parseServerResponse1_2 () {

        SAInstall install = new SAInstall(getContext());
        assertNotNull(install);

        boolean result = install.parseServerResponse("{ \"success\" : false }");
        assertFalse(result);
    }

    @SmallTest
    public void testSAInstall_parseServerResponse1_3 () {

        SAInstall install = new SAInstall(getContext());
        assertNotNull(install);

        boolean result = install.parseServerResponse("{ \"successlse }");
        assertFalse(result);
    }

    @SmallTest
    public void testSAInstall_parseServerResponse1_4 () {

        SAInstall install = new SAInstall(getContext());
        assertNotNull(install);

        boolean result = install.parseServerResponse(null);
        assertFalse(result);
    }

    @SmallTest
    public void testSAInstall_Create2 () {

        SAInstall install = new SAInstall(null);
        assertNotNull(install);

    }

    @SmallTest
    public void testSAInstall_getInstallUrl2_1 () {

        SAInstall install = new SAInstall(null);
        assertNotNull(install);

        SASession session = new SASession(null);
        session.setConfigurationProduction();

        String expected = "https://ads.superawesome.tv/v2/install";

        String result = install.getInstallUrl(session);

        assertEquals(expected, result);

    }

    @SmallTest
    public void testSAInstall_getInstallUrl2_4 () {

        SAInstall install = new SAInstall(null);
        assertNotNull(install);

        String result = install.getInstallUrl(null);

        assertNull(result);

    }

    @SmallTest
    public void testSAInstall_getInstallQuery2_1 () {

        SAInstall install = new SAInstall(null);
        assertNotNull(install);

        JSONObject query = install.getInstallQuery("com.installed.app", "com.source.app");
        assertNotNull(query);

        String expected1 = "com.installed.app";
        String expected2 = "com.source.app";

        assertTrue(query.has("bundle"));
        assertTrue(query.has("sourceBundle"));
        assertEquals(expected1, query.opt("bundle"));
        assertEquals(expected2, query.opt("sourceBundle"));

    }

    @SmallTest
    public void testSAInstall_getInstallHeader2_1 () {

        SAInstall install = new SAInstall(null);
        assertNotNull(install);

        JSONObject query = install.getInstallHeader();
        assertNotNull(query);

        String expected1 = "application/json";
        String expected2 = SAUtils.getUserAgent(null);

        assertTrue(query.has("Content-Type"));
        assertTrue(query.has("User-Agent"));
        assertEquals(expected1, query.opt("Content-Type"));
        assertEquals(expected2, query.opt("User-Agent"));

    }

    @SmallTest
    public void testSAInstall_parseServerResponse2_1 () {

        SAInstall install = new SAInstall(null);
        assertNotNull(install);

        boolean result = install.parseServerResponse("{ \"success\" : true }");
        assertTrue(result);
    }

}
