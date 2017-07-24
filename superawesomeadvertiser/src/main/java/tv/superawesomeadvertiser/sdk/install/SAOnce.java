/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesomeadvertiser.sdk.install;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class contains methods that make sure that the CPI code gets executed just once.
 */
public class SAOnce {

    // constants for the shared preferences file name and key
    private static final String FILE_NAME   = "SA_CPI_File";
    private static final String KEY         = "SA_CPI_Key";

    // preferences object
    private SharedPreferences preferences = null;

    /**
     * Normal constructor with context
     *
     * @param context current context (activity or fragment)
     */
    public SAOnce (Context context) {
        try {
            preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        } catch (Exception ignored) {
            // do nothing
        }
    }

    /**
     * Method that checks if the local preferences objects contains the SA CPI Key.
     *
     * @return true if present, false otherwise
     */
    public boolean isCPISent () {
        try {
            return preferences.contains(KEY);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Method that sets the CPI install event as being sent by putting "true" under the
     * local preferences KEY key.

     * @return true if operation OK, false otherwise
     */
    public boolean setCPISent () {
        try {
            preferences.edit().putBoolean(KEY, true).apply();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Aux method (used mostly for testing) that resets the CPI sent KEY in the shared
     * preferences.
     *
     * @return true if operation OK, false otherwise
     */
    public boolean resetCPISent () {
        try {
            preferences.edit().remove(KEY).apply();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
