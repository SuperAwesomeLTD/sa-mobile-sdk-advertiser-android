package tv.superawesome.plugins.advertiser.air;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class SAdvAIR implements FREExtension {
    /**
     * Overridden "initialize" method of FREExtension that fires up AIR
     *
     */
    @Override
    public void initialize() {
        Log.d("SuperAwesome", "Initialize SAdvAIR");
    }

    /**
     * Overridden "createContext" method of FREExtension that starts up an AIR
     * extension context
     *
     * @param s     the context string
     * @return      a new FREContext object
     */
    @Override
    public FREContext createContext(String s) {
        Log.d("SuperAwesome","Create FREContext for SAdvAIR");
        return new SAdvAIRExtensionContext();
    }

    /**
     * Overridden "dispose" method that destroys an already existing FREExstension
     *
     */
    @Override
    public void dispose() {
        Log.d("SuperAwesome", "Dispose SAdvAIR");
    }
}
