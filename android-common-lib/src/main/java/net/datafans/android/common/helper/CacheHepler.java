package net.datafans.android.common.helper;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by zhonganyun on 15/9/22.
 */
public class CacheHepler {

    public static String getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        return cachePath + File.separator + uniqueName;
    }
}
