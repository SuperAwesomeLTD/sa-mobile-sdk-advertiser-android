/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.advertiser.pack;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;
import tv.superawesome.sdk.advertiser.utils.SAdvConfiguration;

/**
 * This class holds some methods that check with the AwesomeAds server to see if there is a
 * potential list of Android Package names that could have generated an install from this IP
 * range for a particular package name.
 *
 * This is useful to be able to make sure our installs have more reliability
 *
 */
public class SACheck {

    // private member variables
    private SANetwork   network;
    private Context     context;

    /**
     * Normal constructor with context
     *
     * @param context current context (activity or fragment)
     */
    public SACheck(Context context) {

        // get the context reference
        this.context = context;

        // create the SANetwork object
        network = new SANetwork();
    }

    /**
     * Method that returns the standard check install url
     *
     * @param configuration     either staging or production
     * @return                  an url of one of two forms:
     *                          - https://ads.superawesome.tv/v2/checkinstall
     *                          - https://ads.staging.superawesome.tv/v2/checkinstall
     */
    public String getCheckInstallUrl (SAdvConfiguration configuration) {
        if (configuration == SAdvConfiguration.PRODUCTION) {
            return "https://ads.superawesome.tv/v2/checkinstall";
        } else  {
            return "https://ads.staging.superawesome.tv/v2/checkinstall";
        }
    }

    /**
     * Method that returns an additional query to be added to the check install event url.
     *
     * @param packageName package name of the current app; this will be used in the returned
     *                    JSONObject that will get added to the query
     * @return            a JSON object that looks like this:
     *                    {
     *                        "bundle" : "com.example.just.installed.app"
     *                    }
     */
    public JSONObject getCheckInstallQuery (String packageName) {
        return SAJsonParser.newObject("bundle", packageName);
    }

    /**
     * Method that parses a string returned by the ad server, possibly containing a JSON
     * array of package names as string, into a list of strings;
     *
     * @param serverResponse a JSON array as string; something like this:
     *                       " [ \"one.potential.app\", \"another.potential.app\" ] "
     * @return               a list of strings, like this:
     *                       ["one.potential.app", "another.potential.app"]
     */
    public List<String> parseServerResponse (String serverResponse) {

        // the array to return
        List<String> packages = new ArrayList<>();

        // try to parse the string containing a JSON array;
        // if it errors, it'll return an empty JSONArray
        JSONArray packagesJSON = SAJsonParser.newArray(serverResponse);

        // go through each element and try adding it to the list
        for (int i = 0; i < packagesJSON.length(); i++) {
            try {
                packages.add(packagesJSON.getString(i));
            } catch (Exception e) {
                // do nothing
            }
        }

        // finally return
        return packages;

    }

    /**
     * Method that asks the AwesomeAds server to return a list of potential package names that
     * could have generated an install from this IP range for a given package name
     *
     * @param packageName   the package name to ask the server about
     * @param configuration staging or production
     * @param listener      listener instance
     */
    public void askServerForPackagesThatGeneratedThisInstall(final String packageName, SAdvConfiguration configuration, final SACheckInstallInterface listener) {

        // create the event url
        String url = getCheckInstallUrl(configuration);

        // create the additional query
        JSONObject query = getCheckInstallQuery(packageName);

        // send a get request to the ad server
        network.sendGET(context, url, query, new JSONObject(), new SANetworkInterface() {
            @Override
            public void saDidGetResponse(int status, String payload, boolean success) {

                if (listener != null) {
                    listener.saDidGetListOfPackagesToCheck(parseServerResponse(payload));
                }
            }
        });
    }

    /**
     * Interface for the remote package inspector
     */
    public interface SACheckInstallInterface {

        /**
         * Only method to implement;
         *
         * @param packages list of string packages returned by the server
         */
        void saDidGetListOfPackagesToCheck (List<String> packages);
    }
}
