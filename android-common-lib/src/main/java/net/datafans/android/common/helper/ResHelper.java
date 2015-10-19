package net.datafans.android.common.helper;

import android.util.Log;

import net.datafans.android.common.R;

import java.lang.reflect.Field;

/**
 * Created by zhonganyun on 15/9/15.
 */
public class ResHelper {

    public static int getMipmapResId(String name) {

        try {
            Field localField = R.mipmap.class.getField(name);
            return localField.getInt(name);
        } catch (Exception e) {
            Log.e("Android-Common", e.toString());
        }
        return 0;
    }
}
