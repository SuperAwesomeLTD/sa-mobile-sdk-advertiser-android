package tv.superawesomeadvertiser.test.demoapp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SACPI_SAOnce_Tests.class,
        SACPI_SAPackage_Tests.class,
        SACPI_SAInstall_Tests.class,
        SACPI_SACheck_Tests.class,
        SACPI_SAReferral_Tests.class,
        SACPI_SARefferal_Async_Tests.class,
        SACPI_SACheck_Async_Tests.class,
        SACPI_SAInstall_Async_Tests.class,
        SACPI_SACPI_Async_Tests.class
})
public class TestSuite {
}
