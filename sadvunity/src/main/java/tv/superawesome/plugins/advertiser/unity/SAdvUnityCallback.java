package tv.superawesome.plugins.advertiser.unity;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

public class SAdvUnityCallback {

    /**
     * Method that tries to send back data to an Unity app
     *
     * @param unityAd   current unity ad to send data to
     * @param data      the data package
     */
    public static void sendToUnity (String unityAd, JSONObject data) {

        // don't do anything if class is not available
        if (!isClassAvailable("com.unity3d.player.UnityPlayer")) return;

        String payload = data.toString();

        // try to call the unity player
        try {
            Class<?> unity = Class.forName("com.unity3d.player.UnityPlayer");
            java.lang.reflect.Method method = unity.getMethod("UnitySendMessage", String.class, String.class, String.class);
            method.invoke(unity, unityAd, "nativeCallback", payload);
        } catch (ClassNotFoundException e) {
            //
        } catch (NoSuchMethodException e) {
            //
        } catch (InvocationTargetException e) {
            //
        } catch (IllegalAccessException e) {
            //
        }
    }

    /**
     * Method that sends CPI data back to an Adobe AIR app
     *
     * @param unityAd   name of the Ad to send back data to
     * @param success   success state of the CPI operation
     * @param callback  the callback name
     */
    public static void sendCPICallback (String unityAd, boolean success, String callback) {

        JSONObject data = new JSONObject();
        try {
            data.put("success", "" + success + "");
            data.put("type", "sacallback_" + callback);
        } catch (Exception e) {
            // do nothing
        }

        sendToUnity(unityAd, data);

    }

    private static boolean isClassAvailable (String className) {
        boolean driverAvailable = true;

        try {
            Class.forName(className);
        } catch (NullPointerException | ClassNotFoundException var3) {
            driverAvailable = false;
        }

        return driverAvailable;
    }
}
