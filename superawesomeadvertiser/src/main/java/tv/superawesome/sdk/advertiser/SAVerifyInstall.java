/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.advertiser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import tv.superawesome.sdk.advertiser.install.SAInstall;
import tv.superawesome.sdk.advertiser.install.SAOnce;
import tv.superawesome.sdk.advertiser.pack.SACheck;
import tv.superawesome.sdk.advertiser.pack.SAPackage;
import tv.superawesome.sdk.advertiser.referral.SAReceiver;
import tv.superawesome.sdk.advertiser.utils.SAAdvUtils;
import tv.superawesome.sdk.advertiser.utils.SAdvConfiguration;

/**
 * Class that extends BroadcastReceiver in order to:
 * - if an app is installed via Google Play Store and there *is* referral data, then it can act
 * on it using the SAReferralEvent class
 * - otherwise, define a method called handleInstall that just sends an /install event to the
 * ad server and returns a callback indicating success or failure
 */
public class SAVerifyInstall extends BroadcastReceiver {

    // SAVerifyInstall instance variable that can be setup only once
    private static SAVerifyInstall instance = new SAVerifyInstall();

    /**
     * Private constructor that is only called once
     */
    public SAVerifyInstall() {
        // do nothing
    }

    /**
     * Singleton method to get the only existing instance
     *
     * @return an instance of the SAVerifyInstall class
     */
    public static SAVerifyInstall getInstance () {
        return instance;
    }

    /**
     * Main class method that handles all the aspects of properly sending an /install event
     * - verifying first that I can find a possible "installer" app on the user's device
     * - whatever the answer, send an /install event, with an additional "sourceBundle" parameter
     * attached.
     * This method also assumes a production session, so users won't have to set their own
     * session.
     *
     * @param context   current context (fragment or activity)
     * @param listener  return callback listener
     */
    public void handleInstall(final Context context, final Interface listener) {
        handleInstall(context, SAdvConfiguration.PRODUCTION, listener);
    }

    /**
     * Main class method that handles all the aspects of properly sending an /install event
     * - verifying first that I can find a possible "installer" app on the user's device
     * - whatever the answer, send an /install event, with an additional "sourceBundle" parameter
     * attached.
     *
     * @param context       current context (fragment or activity)
     * @param configuration either staging or production
     * @param listener      return callback listener
     */
    public void handleInstall(final Context context, final SAdvConfiguration configuration, final Interface listener) {
        handleInstall(context, configuration, SAAdvUtils.getPackageName(context), listener);
    }

    /**
     * Main class method that handles all the aspects of properly sending an /install event
     * - verifying first that I can find a possible "installer" app on the user's device
     * - whatever the answer, send an /install event, with an additional "sourceBundle" parameter
     * attached.
     *
     * @param context       current context (fragment or activity)
     * @param configuration either staging or production
     * @param target        the target you want to check & send an install for
     * @param listener      return callback listener
     */
    public void handleInstall(final Context context, final SAdvConfiguration configuration, final String target, final Interface listener) {

        final Interface _listener = listener != null ? listener : new Interface() {
            @Override public void saDidCountAnInstall(boolean success) {}};

        // create the objects I'll need for this method
        final SAOnce    once    = new SAOnce (context);
        final SACheck   check   = new SACheck (context);
        final SAPackage pack    = new SAPackage (context);
        final SAInstall install = new SAInstall (context);

        // find out if I've already sent the CPI event in a previous app session
        boolean isSent = once.isCPISent();

        // if not, go ahead
        if (!isSent) {

            // find out if the AwesomeAds server thinks there are a number of potential
            // apps (package names) that might've generated the install from this
            // device (actually truncated IP range)
            check.askServerForPackagesThatGeneratedThisInstall(target, configuration, new SACheck.SACheckInstallInterface() {
                @Override
                public void saDidGetListOfPackagesToCheck(List<String> packages) {

                    // find out if at least one of the apps the server sent is to be found
                    // on the user's device
                    String source = pack.findFirstPackageOnDeviceFrom(packages);
                    Log.d("SuperAwesome", "Source is " + source);

                    // and whatever the outcome, send an install event to the server
                    // and await for a response
                    install.sendInstallEventToServer(target, source, configuration, new SAInstall.SAInstallInterface() {
                        @Override
                        public void saDidCountAnInstall(boolean success) {
                            once.setCPISent();
                            _listener.saDidCountAnInstall(success);
                        }
                    });

                }
            });
        }
    }

    /**
     * Overridden BroadcastReceiver method that only gets called when an app is installed from
     * the Google Play Store
     *
     * @param context   current context (fragment or activity)
     * @param intent    an intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        SAReceiver referral = new SAReceiver(context);
        referral.sendReferralEvent(intent, null);
    }

    /**
     * Interface defining a callback method to be called when an install was successfully counted
     */
    public interface Interface {

        /**
         * Method to implement. It just "returns" a success callback parameter to indicate all is OK
         *
         * @param success whether the install counting operation was done OK by the ad server
         */
        void saDidCountAnInstall (boolean success);
    }
}