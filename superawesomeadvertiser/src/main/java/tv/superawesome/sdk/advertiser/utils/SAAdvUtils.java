package tv.superawesome.sdk.advertiser.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import tv.superawesome.lib.sautils.SAUtils;

public class SAAdvUtils {

    public static String getPackageName(Context context) {
        return context != null ? context.getPackageName() : "undefined";
    }

    public static String getUserAgent(Context context) {
        if (Build.VERSION.SDK_INT >= 17) {
            if (context != null) {
                return WebSettings.getDefaultUserAgent(context);
            } else {
                return System.getProperty("http.agent");
            }
        } else {
            if (context != null) {
                return new WebView(context).getSettings().getUserAgentString();
            } else {
                return System.getProperty("http.agent");
            }
        }
    }

    public static int randomNumberBetween(int min, int max){
        Random rand  = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public static int getCacheBuster() {
        return randomNumberBetween(1000000, 1500000);
    }

    public static SAUtils.SAConnectionType getNetworkConnectivity (Context context) {
        // null guard
        if (context == null) return SAUtils.SAConnectionType.unknown;

        // get connectivity manager
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        // unknown case
        if(info == null || !info.isConnected()) {
            return SAUtils.SAConnectionType.unknown;
        }

        // wifi case
        if(info.getType() == ConnectivityManager.TYPE_WIFI) {
            return SAUtils.SAConnectionType.wifi;
        }

        // mobile case
        if(info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                    return SAUtils.SAConnectionType.cellular_2g;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                    return SAUtils.SAConnectionType.cellular_3g;
                case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                    return SAUtils.SAConnectionType.cellular_4g;
                default:
                    return SAUtils.SAConnectionType.unknown;
            }
        }

        // default return
        return SAUtils.SAConnectionType.unknown;
    }

    public static String encodeDictAsJsonDict(JSONObject dict) {
        // check null
        if (dict == null || dict.length() == 0) return "%7B%7D";

        // if all's ok try encoding
        return encodeURL(dict.toString());
    }

    public static String encodeURL(String urlStr) {
        // check null
        if (urlStr == null || urlStr.equals("")) return "";

        // if all's ok try encoding
        try {
            return URLEncoder.encode(urlStr, "UTF-8");
        } catch (UnsupportedEncodingException | NullPointerException e) {
            return "";
        }
    }

    public static String formGetQueryFromDict(JSONObject dict) {
        String queryString = "";

        // null check
        if (dict == null) return queryString;

        ArrayList<String> queryArray = new ArrayList<>();


        Iterator<String> keys = dict.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            Object valObj = dict.opt(key);
            String val = (valObj != null ? valObj.toString().replace("\"","") : "");

            queryArray.add(key + "=" + val + "&");
        }

        for (String queryObj : queryArray) {
            queryString += queryObj;
        }

        if (queryString.length() > 1) {
            return queryString.substring(0, queryString.length() - 1);
        } else {
            return queryString;
        }
    }
}
