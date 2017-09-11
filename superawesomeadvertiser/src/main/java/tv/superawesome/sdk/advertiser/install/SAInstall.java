/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.advertiser.install;

import android.content.Context;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;
import tv.superawesome.sdk.advertiser.utils.SAAdvUtils;
import tv.superawesome.sdk.advertiser.utils.SAdvConfiguration;

/**
 * Class that contains methods to generate all necessary GET request elements for an install
 * event:
 * - the install event url
 * - the install event additional url query, containing at least a "?bundle=just.installed.app"
 *   part and additionally, an "&sourceBundle=source.of.install" part
 *   Thus the url will be /install?bundle=just.installed.app&sourceBundle=source.of.install
 * - the install header, as a JSONObject
 */
public class SAInstall {

    // private context reference
    private Context     context;
    private SANetwork   network;

    /**
     * Normal constructor with context
     *
     * @param context current context (activity or fragment)
     */
    public SAInstall (Context context) {
        // get the context reference
        this.context = context;

        // create the SANetwork object
        network = new SANetwork();
    }

    /**
     * Get the base install url
     *
     * @return        an url of one of two forms:
     *                - https://ads.superawesome.tv/v2/install
     *                - https://ads.staging.superawesome.tv/v2/install
     */
    public String getInstallUrl (SAdvConfiguration configuration) {
        if (configuration == SAdvConfiguration.PRODUCTION) {
            return "https://ads.superawesome.tv/v2/install";
        } else {
            return "https://ads.staging.superawesome.tv/v2/install";
        }
    }

    /**
     * Get the additional install query
     *
     * @param targetPackageName the package name of the app that's just been installed on a user's
     *                          device; this is a string of the form com.example.my.app
     * @param sourcePackageName the package name of the potential app that was generated the install
     *                          of the current app, if it's found on the device.
     *                          this is also a string of the form com.example.some.other.app
     * @return                  a JSONObject of one of two forms, depending on whether
     *                          sourcePackageName is null or not:
     *                          - {
     *                              "bundle": "com.example.my.app"
     *                            }
     *                          - {
     *                              "bundle : "com.example.my.app",
     *                              "sourceBundle": "com.example.some.other.app"
     *                            }
     */
    public JSONObject getInstallQuery (String targetPackageName, String sourcePackageName) {
        return SAJsonParser.newObject(
                "bundle", targetPackageName,
                "sourceBundle", sourcePackageName);
    }

    /**
     * Get the install header needed for the GET operation to the Awesome Ads server
     *
     * @return  a JSONObject used as a header for the GET operation. It'll look something like this:
     *          {
     *              "Content-Type" : "application/json",
     *              "User-Agent" : "Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19"
     *          }
     */
    public JSONObject getInstallHeader () {
        return SAJsonParser.newObject(
                "Content-Type", "application/json",
                "User-Agent", SAAdvUtils.getUserAgent(context));
    }

    /**
     * Method that parser the server response for an "/install" GET event call.
     *
     * @param serverResponse the server response, as a string; it usually is of the form:
     *                       - { "success" : true }
     *                       - { "success" : false }
     *                       but if the ad server responde with 404 or 400 or another error code,
     *                       it could also be null.
     * @return               true or false, depending on the actual serverResponse string
     */
    public boolean parseServerResponse (String serverResponse) {

        // get the response as a JSON object
        JSONObject response = SAJsonParser.newObject(serverResponse);

        // and try to parse it as a boolean
        return SAJsonParser.getBoolean(response, "success", false);
    }

    /**
     * Method that actually sends an install event to the server, based on a number of
     * parameters.
     *
     * @param targetPackageName the package name of the app just being installed
     * @param sourcePackageName the package name of an app that's possible to have generated this
     *                          current app to be installed
     * @param configuration     either staging or production
     * @param listener          an instance of a SAInstallInterface listener to send a callback
     */
    public void sendInstallEventToServer (String targetPackageName, String sourcePackageName, SAdvConfiguration configuration, final SAInstallInterface listener) {

        // get the event url
        String url = getInstallUrl(configuration);

        // get the associated url query
        JSONObject query = getInstallQuery(targetPackageName, sourcePackageName);

        // get the install header
        JSONObject header = getInstallHeader();

        // send the GET request and await a result
        network.sendGET(context, url, query, header, new SANetworkInterface() {
            @Override
            public void saDidGetResponse(int status, String payload, boolean success) {
                if (listener != null) {
                    listener.saDidCountAnInstall(parseServerResponse(payload));
                }
            }
        });
    }

    /**
     * Interface defining a callback method to be called when an install was successfully counted
     */
    public interface SAInstallInterface {

        /**
         * Method to implement. It just "returns" a success callback parameter to indicate all is OK
         *
         * @param success whether the install counting operation was done OK by the ad server
         */
        void saDidCountAnInstall (boolean success);
    }
}
