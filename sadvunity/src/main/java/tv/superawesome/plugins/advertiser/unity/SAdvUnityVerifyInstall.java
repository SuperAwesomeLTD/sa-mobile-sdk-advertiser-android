package tv.superawesome.plugins.advertiser.unity;

import android.content.Context;

import tv.superawesome.sdk.advertiser.SAVerifyInstall;

public class SAdvUnityVerifyInstall {

    // CPI name
    private static final String unityName = "SAVerifyInstall";

    /**
     * Method that sends a callback to Unity after a
     * CPI operation on production
     *
     * @param context current context (activity or fragment)
     */
    public static void SuperAwesomeAdvertiserUnitySAVerifyInstall (Context context) {

        SAVerifyInstall.getInstance().handleInstall(context, new SAVerifyInstall.Interface() {
            @Override
            public void saDidCountAnInstall(boolean success) {
                SAdvUnityCallback.sendCPICallback(unityName, success, "HandleInstall");
            }
        });

    }
}
