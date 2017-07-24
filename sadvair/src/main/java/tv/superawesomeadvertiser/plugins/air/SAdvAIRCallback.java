package tv.superawesomeadvertiser.plugins.air;

import com.adobe.fre.FREContext;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SAJsonParser;

public class SAdvAIRCallback {

    /**
     * Method that tries to send back data to an Adobe AIR app
     *
     * @param context   current FREContext object
     * @param data      the data package
     */
    public static void sendToAIR (FREContext context, JSONObject data) {
        if (context != null && data != null) {

            String packageString = data.toString();
            context.dispatchStatusEventAsync(packageString, "");

        }
    }

    /**
     * Method that sends CPI data back to an Adobe AIR app
     *
     * @param context   current FREContext object
     * @param name      name of the Ad to send back data to
     * @param success   success state of the CPI operation
     * @param callback  the callback name
     */
    public static void sendCPICallback (FREContext context, String name, boolean success, String callback) {

        JSONObject data = SAJsonParser.newObject(
                "name", name,
                "success", success,
                "callback", callback);
        sendToAIR(context, data);

    }
}
