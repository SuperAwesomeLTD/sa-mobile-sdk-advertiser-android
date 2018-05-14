/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.advertiser.referral;

import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.referral.SAReferral;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;
import tv.superawesome.sdk.advertiser.utils.SAAdvUtils;
import tv.superawesome.sdk.advertiser.utils.SAdvConfiguration;

/**
 * Class that contains methods to handle when the app receives referral data from the google
 * play store.
 */
public class SAReceiver {

    // current context
    private Context     context;
    private SANetwork   network;

    /**
     * Normal constructor with context
     *
     * @param context current context (activity or fragment)
     */
    public SAReceiver (Context context) {
        // get the context reference
        this.context = context;

        // create the SANetwork object
        network = new SANetwork();
    }

    /**
     * Method that takes an intent (that should be sent by the Android Broadcast service)
     * and find out if it has an associated "referrer" string extra.
     * If it does, it's going to parse the referral response;
     *
     * @param intent current intent
     * @return       valid referral data
     */
    public SAReferral parseReferralResponse (Intent intent) {

        String referralString = null;

        // guard against an invalid intent
        try {
            referralString = intent.getStringExtra("referrer");
        } catch (Exception ignored) {
            // do nothing
        }

        return parseReferralResponse(referralString);

    }

    /**
     * Method that transforms a received string that supposedly contains referral data into
     * a proper JSON string and then into a new SAReferralData object
     *
     * @param data  the string data, something like utm_source=33&utm_campaign=3121 ...
     * @return      a new instance of SAReferralData
     */
    public SAReferral parseReferralResponse (String data) {

        String referrer;

        referrer = data != null ? data : "";
        referrer = referrer.replace("=", " : ");
        referrer = referrer.replace("%3D", " : ");
        referrer = referrer.replace("\\&", ", ");
        referrer = referrer.replace("&", ", ");
        referrer = referrer.replace("%26", ", ");
        referrer = "{ " + referrer + " }";
        referrer = referrer.replace("utm_source", "\"utm_source\"");
        referrer = referrer.replace("utm_campaign", "\"utm_campaign\"");
        referrer = referrer.replace("utm_term", "\"utm_term\"");
        referrer = referrer.replace("utm_content", "\"utm_content\"");
        referrer = referrer.replace("utm_medium", "\"utm_medium\"");

        return new SAReferral (referrer);

    }

    /**
     * Method that forms a new event dictionary from a referral data object
     *
     * @param data  an instance of SAReferralData
     * @return      a new JSONObject that contains that will be used by the ad server to
     *              record the event
     */
    public JSONObject getReferralCustomData (SAReferral data) {

        try {
            return SAJsonParser.newObject(
                    // "sdkVersion", SuperAwesome.getInstance().getSDKVersion(),
                    "rnd", SAAdvUtils.getCacheBuster(),
                    "ct", SAAdvUtils.getNetworkConnectivity(context).ordinal(),
                    "data", SAAdvUtils.encodeDictAsJsonDict(SAJsonParser.newObject(
                            "placement", data.placementId,
                            "line_item", data.lineItemId,
                            "creative", data.creativeId,
                            "type", "custom.referred_install")));
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    /**
     * Method that forms a new session and then sets it either to production or staging based
     * on the referral data configuration
     *
     * @param data  an instance of SAReferralData
     * @return      a new configured session instance
     */
    public SAdvConfiguration getReferralInstallConfiguration (SAReferral data) {
        try {
            return SAdvConfiguration.fromValue(data.configuration);
        } catch (Exception e) {
            return SAdvConfiguration.PRODUCTION;
        }
    }

    /**
     * Method that forms the referral url where the event will be sent to
     *
     * @param data  an instance of SAReferralData
     * @return      a new URL string to send the event to
     */
    public String getReferralUrl (SAReferral data) {

        SAdvConfiguration configuration = getReferralInstallConfiguration(data);
        String baseUrl = configuration == SAdvConfiguration.PRODUCTION ? "https://ads.superawesome.tv/v2" : "https://ads.staging.superawesome.tv/v2";
        JSONObject refEventDict = getReferralCustomData(data);

        return baseUrl + "/event?" + SAAdvUtils.formGetQueryFromDict(refEventDict);
    }

    /**
     * This will always send the referral header needed by the GET request
     *
     * @return  a standard GET request header as a JSONObject
     */
    public JSONObject getReferralHeader () {
        return SAJsonParser.newObject(
                "Content-Type", "application/json",
                "User-Agent", SAAdvUtils.getUserAgent(context));
    }

    /**
     * Main method of the class. This sends an event to the ad server based on referral data
     * sent by the Android Broadcast service through an Intent
     *
     * @param intent    Android Broadcast Service intent containing a "referrer" string
     * @param listener  Listener for a callback
     */
    public void sendReferralEvent (Intent intent, final SAReceiverInterface listener) {

        // get the referral data from the intent
        SAReferral referralData = parseReferralResponse(intent);

        // if it's valid
        if (referralData.isValid()) {

            // get the url
            String url = getReferralUrl(referralData);
            // get the header
            JSONObject header = getReferralHeader();

            // send the network request
            network.sendGET(url, new JSONObject(), header, new SANetworkInterface() {
                @Override
                public void saDidGetResponse(int status, String payload, boolean success) {
                    if (listener != null) {
                        listener.saDidSendReferralData(success);
                    }
                }
            });

        }
        // else callback with false
        else {
            if (listener != null) {
                listener.saDidSendReferralData(false);
            }
        }

    }

    /**
     * Interface for the receiver system
     */
    public interface SAReceiverInterface {

        /**
         * Method to implement
         *
         * @param success callback param to tell if the op was successful or not
         */
        void saDidSendReferralData (boolean success);
    }
}
