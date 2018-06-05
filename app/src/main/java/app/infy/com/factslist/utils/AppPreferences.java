package app.infy.com.factslist.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class AppPreferences {
    private static final String TAG = "AppPreferences";
    private static SharedPreferences sharedPreferences = null;


    public static SharedPreferences getsharedprefernces(Context context) {
        if (context != null) {
            if (sharedPreferences == null) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            }
        }
        return sharedPreferences;
    }

    public static String getString(String key, Context context) {
        return getsharedprefernces(context).getString(key, "");

    }

    public static void putString(String key, String value, Context context) {
        getsharedprefernces(context).edit().putString(key, value).apply();

    }

    public static void removeData(Context context, String key) {
        getsharedprefernces(context).edit().remove(key);
    }


    public static void clearData(Context context) {
        Log.d(TAG, "Clearing all preference data");
        getsharedprefernces(context).edit().clear().apply();
    }

    public static void clearStringData(String key, Context context) {
        getsharedprefernces(context).edit().remove(key).apply();
    }
}
