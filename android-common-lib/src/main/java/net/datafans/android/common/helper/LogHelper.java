package net.datafans.android.common.helper;

import android.util.Log;

/**
 * Created by zhonganyun on 15/11/20.
 */
public class LogHelper {

    private static String TAG = "### android-common ####";

    private static boolean ENABLE = true;

    public static void init(String tag, boolean enable) {
        TAG = tag;
        ENABLE = enable;
    }

    public static void error(String log) {
        if (ENABLE)
            Log.e(TAG, log);
    }

    public static void error(Throwable throwable) {
        if (ENABLE)
            Log.e(TAG, throwable.getMessage());
    }

    public static void info(String log) {
        if (ENABLE)
            Log.i(TAG, log);
    }

    public static void debug(String log) {
        if (ENABLE)
            Log.d(TAG, log);
    }

    public static void warn(String log) {
        if (ENABLE)
            Log.w(TAG, log);
    }
}
