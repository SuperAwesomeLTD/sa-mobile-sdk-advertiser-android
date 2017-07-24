package tv.superawesomeadvertiser.plugins.unity;

import android.content.Context;

import tv.superawesomeadvertiser.sdk.SuperAwesomeAdvertiser;

public class SAdvUnitySuperAwesomeAdvertiser {

    // CPI name
    private static final String unityName = "SuperAwesomeAdvertiser";

    /**
     * Method that sends a callback to Unity after a
     * CPI operation on production
     *
     * @param context current context (activity or fragment)
     */
    public static void SuperAwesomeAdvertiserUnitySACPIHandleInstall (Context context) {

        SuperAwesomeAdvertiser.getInstance().handleInstall(context, new SuperAwesomeAdvertiser.Interface() {
            @Override
            public void saDidCountAnInstall(boolean success) {
                SAdvUnityCallback.sendCPICallback(unityName, success, "HandleInstall");
            }
        });

    }
}
