package net.datafans.android.common;

import net.datafans.android.common.config.GlobalAppearance;
import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.image.loader.WebFileLoader;
import net.datafans.android.common.widget.imageview.ImageViewType;

import android.content.Context;

public class AndroidCommon {

    private static Context ctx;

    private static GlobalAppearance appearance = new GlobalAppearance();

    public final static AndroidCommon common = new AndroidCommon();

    public static AndroidCommon sharedInstance() {
        return common;
    }

    public static Context getContext() {
        return ctx;
    }

    public static void onCreate(Context context) {
        ctx = context;
        WebFileLoader.sharedInstance().create(context);
    }

    public static void onCreate(Context context, int capacity) {
        ctx = context;
        WebFileLoader.sharedInstance().create(context, capacity);
    }

    public static void onTerminate() {

    }

    public static GlobalAppearance getAppearance() {
        return appearance;
    }

    public static void configLog(String tag, boolean enable){
        LogHelper.init(tag, enable);
    }

    private static ImageViewType imageViewType;

    public static void setImageViewType(ImageViewType type) {
        imageViewType = type;
    }

    public static ImageViewType getImageViewType() {
        return imageViewType;
    }
}
