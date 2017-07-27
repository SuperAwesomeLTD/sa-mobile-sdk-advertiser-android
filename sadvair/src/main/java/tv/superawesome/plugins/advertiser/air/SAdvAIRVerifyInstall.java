package tv.superawesome.plugins.advertiser.air;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import tv.superawesome.sdk.advertiser.SAVerifyInstall;

public class SAdvAIRVerifyInstall {

    // air CPI name
    private static final String airName = "SAVerifyInstall";

    /**
     * Inner class that implements a method to send back a callback to Adobe AIR after a
     * CPI operation on production
     */
    public static class SuperAwesomeAdvertiserAIRSAVerifyInstall implements FREFunction {
        /**
         * Overridden FREFunction "call" method;
         * This needs to be implemented if this class is to correctly implement the FREFunction
         * interface.
         * This is the way AIR communicates with native Android code.
         *
         * @param freContext    current FREContext
         * @param freObjects    a list of parameters that might have been sent by adobe AIR
         * @return              a FREObject that sends back data to Adobe AIR
         */
        @Override
        public FREObject call(final FREContext freContext, FREObject[] freObjects) {

            SAVerifyInstall.getInstance().handleInstall(freContext.getActivity(), new SAVerifyInstall.Interface() {
                @Override
                public void saDidCountAnInstall(boolean success) {
                    SAdvAIRCallback.sendCPICallback(freContext, airName, success, "HandleInstall");
                }
            });

            return null;
        }
    }
}
