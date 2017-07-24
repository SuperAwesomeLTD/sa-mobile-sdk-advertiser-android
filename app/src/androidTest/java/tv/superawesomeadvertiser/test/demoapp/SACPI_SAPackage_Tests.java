package tv.superawesomeadvertiser.test.demoapp;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.Arrays;
import java.util.Collections;

import tv.superawesomeadvertiser.sdk.pack.SAPackage;

public class SACPI_SAPackage_Tests extends ApplicationTestCase<Application> {

    public SACPI_SAPackage_Tests() {
        super(Application.class);
    }

    @SmallTest
    public void testSAPackage_Create1 () {

        SAPackage pack = new SAPackage(getContext());
        assertNotNull(pack);

    }

    @SmallTest
    public void testSAPackage_isPackageOnDevice1_1 () {

        SAPackage pack = new SAPackage(getContext());
        assertNotNull(pack);

        boolean isOnDevice = pack.isPackageOnDevice("tv.superawesome.sacpidemo");
        assertTrue(isOnDevice);

    }

    @SmallTest
    public void testSAPackage_isPackageOnDevice1_2 () {

        SAPackage pack = new SAPackage(getContext());
        assertNotNull(pack);

        boolean isOnDevice = pack.isPackageOnDevice("com.android.gesture.builder");
        assertTrue(isOnDevice);
    }

    @SmallTest
    public void testSAPackage_isPackageOnDevice1_3 () {

        SAPackage pack = new SAPackage(getContext());
        assertNotNull(pack);

        boolean isOnDevice = pack.isPackageOnDevice("com.example.someapp");
        assertFalse(isOnDevice);
    }

    @SmallTest
    public void testSAPackage_findFirstPackageOnDeviceFrom1_1 () {

        SAPackage pack = new SAPackage(getContext());
        assertNotNull(pack);

        String expected = "com.android.gesture.builder";

        String found = pack.findFirstPackageOnDeviceFrom(Arrays.asList("tv.superawesome.sacpidemo", "com.android.gesture.builder"));

        assertEquals(expected, found);

    }

    @SmallTest
    public void testSAPackage_findFirstPackageOnDeviceFrom1_2 () {

        SAPackage pack = new SAPackage(getContext());
        assertNotNull(pack);

        String expected = "com.android.gesture.builder";

        String found = pack.findFirstPackageOnDeviceFrom(Arrays.asList("com.android.gesture.builder", "tv.superawesome.sacpidemo"));

        assertEquals(expected, found);

    }

    @SmallTest
    public void testSAPackage_findFirstPackageOnDeviceFrom1_3 () {

        SAPackage pack = new SAPackage(getContext());
        assertNotNull(pack);

        String expected = "com.android.gesture.builder";

        String found = pack.findFirstPackageOnDeviceFrom(Arrays.asList("com.example.someapp", "com.android.gesture.builder"));

        assertEquals(expected, found);

    }

    @SmallTest
    public void testSAPackage_findFirstPackageOnDeviceFrom1_4 () {

        SAPackage pack = new SAPackage(getContext());
        assertNotNull(pack);


        String found = pack.findFirstPackageOnDeviceFrom(Collections.singletonList("tv.superawesome.sacpidemo"));

        assertNull(found);

    }

    @SmallTest
    public void testSAPackage_findFirstPackageOnDeviceFrom1_5 () {

        SAPackage pack = new SAPackage(getContext());
        assertNotNull(pack);

        String found = pack.findFirstPackageOnDeviceFrom(Arrays.asList("com.example.someapp", "com.example.someapp2"));
        assertNull(found);

    }

    @SmallTest
    public void testSAPackage_findFirstPackageOnDeviceFrom1_6 () {

        SAPackage pack = new SAPackage(getContext());
        assertNotNull(pack);

        String found = pack.findFirstPackageOnDeviceFrom(null);
        assertNull(found);

    }

    @SmallTest
    public void testSAPackage_Create2 () {

        SAPackage pack = new SAPackage(null);
        assertNotNull(pack);

    }

    @SmallTest
    public void testSAPackage_isPackageOnDevice2_1 () {

        SAPackage pack = new SAPackage(null);
        assertNotNull(pack);

        boolean isOnDevice = pack.isPackageOnDevice("tv.superawesome.sacpidemo");
        assertFalse(isOnDevice);

    }

    @SmallTest
    public void testSAPackage_isPackageOnDevice2_2 () {

        SAPackage pack = new SAPackage(null);
        assertNotNull(pack);

        boolean isOnDevice = pack.isPackageOnDevice("com.android.gesture.builder");
        assertFalse(isOnDevice);
    }

    @SmallTest
    public void testSAPackage_isPackageOnDevice2_3 () {

        SAPackage pack = new SAPackage(null);
        assertNotNull(pack);

        boolean isOnDevice = pack.isPackageOnDevice("com.example.someapp");
        assertFalse(isOnDevice);
    }

    @SmallTest
    public void testSAPackage_findFirstPackageOnDeviceFrom2_1 () {

        SAPackage pack = new SAPackage(null);
        assertNotNull(pack);

        String found = pack.findFirstPackageOnDeviceFrom(Arrays.asList("tv.superawesome.sacpidemo", "com.android.gesture.builder"));

        assertNull(found);

    }
}
