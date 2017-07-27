package tv.superawesome.plugins.advertiser.air;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

import java.util.HashMap;
import java.util.Map;

public class SAdvAIRExtensionContext extends FREContext {

    /**
     * Overridden FREContext "getFunctions" method that will return a hash-map containing
     * function names and implementation so that AIR knows what to call
     *
     * @return a hash-map of function names-implementations
     */
    @Override
    public Map<String, FREFunction> getFunctions() {

        // create a new map of FREFunction objects
        Map<String, FREFunction> functions = new HashMap<String, FREFunction>();

        // add all functions
        functions.put("SuperAwesomeAdvertiserAIRSAVerifyInstall", new SAdvAIRVerifyInstall.SuperAwesomeAdvertiserAIRSAVerifyInstall());

        // return the result for the context
        return functions;
    }

    /**
     * Method that disposes of the current FREContext
     */
    @Override
    public void dispose() {
        Log.d("AIREXT", "AIR Extension Context disposed");
    }
}
