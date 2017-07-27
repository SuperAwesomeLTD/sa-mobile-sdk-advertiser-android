package tv.superawesomeadvertiser.test.demoapp;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import tv.superawesome.sdk.advertiser.install.SAOnce;

public class SACPI_SAOnce_Tests extends ApplicationTestCase<Application> {

    public SACPI_SAOnce_Tests() {
        super(Application.class);
    }

    @SmallTest
    public void testSAOnce_Create1 () {

        SAOnce once = new SAOnce(getContext());
        assertNotNull(once);

    }

    @SmallTest
    public void testSAOnce_Reset1 () {

        SAOnce once = new SAOnce(getContext());
        assertNotNull(once);

        boolean success = once.resetCPISent();
        assertTrue(success);

        boolean isSent = once.isCPISent();
        assertFalse(isSent);

    }

    @SmallTest
    public void testSAOnce_Set1 () {

        SAOnce once = new SAOnce(getContext());
        assertNotNull(once);

        boolean success = once.setCPISent();
        assertTrue(success);

        boolean isSent = once.isCPISent();
        assertTrue(isSent);

    }

    @SmallTest
    public void testSAOnce_Successive1 () {

        SAOnce once = new SAOnce(getContext());
        assertNotNull(once);

        boolean success = once.resetCPISent();
        assertTrue(success);

        boolean isSent = once.isCPISent();
        assertFalse(isSent);

        success = once.setCPISent();
        assertTrue(success);

        isSent = once.isCPISent();
        assertTrue(isSent);

        success = once.resetCPISent();
        assertTrue(success);

        isSent = once.isCPISent();
        assertFalse(isSent);

    }

    @SmallTest
    public void testSAOnce_Create2 () {

        SAOnce once = new SAOnce(null);
        assertNotNull(once);

    }

    @SmallTest
    public void testSAOnce_Reset2 () {

        SAOnce once = new SAOnce(null);
        assertNotNull(once);

        boolean success = once.resetCPISent();
        assertFalse(success);
    }

    @SmallTest
    public void testSAOnce_Set2 () {

        SAOnce once = new SAOnce(null);
        assertNotNull(once);

        boolean success = once.setCPISent();
        assertFalse(success);

        boolean isSent = once.isCPISent();
        assertFalse(isSent);

    }

    @SmallTest
    public void testSAOnce_Successive2 () {

        SAOnce once = new SAOnce(null);
        assertNotNull(once);

        boolean success = once.resetCPISent();
        assertFalse(success);

        boolean isSent = once.isCPISent();
        assertFalse(isSent);

        success = once.setCPISent();
        assertFalse(success);

        isSent = once.isCPISent();
        assertFalse(isSent);

        success = once.resetCPISent();
        assertFalse(success);

        isSent = once.isCPISent();
        assertFalse(isSent);

    }
}
