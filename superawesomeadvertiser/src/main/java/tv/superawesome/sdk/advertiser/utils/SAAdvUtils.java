package tv.superawesome.sdk.advertiser.utils;

import android.content.Context;

public class SAAdvUtils {

    public static String getPackageName(Context context) {
        return context != null ? context.getPackageName() : "undefined";
    }

}
