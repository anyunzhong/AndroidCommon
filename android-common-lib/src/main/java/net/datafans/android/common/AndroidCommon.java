package net.datafans.android.common;

import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.image.loader.WebFileLoader;
import net.datafans.android.common.widget.imagepicker.UILImageLoader;
import net.datafans.android.common.widget.imageview.ImageViewType;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

public class AndroidCommon {

    private static Context ctx;

    private static boolean imagePickerSet = false;

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


    public synchronized static void configImagePicker(Resources.Theme theme){

        if (imagePickerSet) return;

        imagePickerSet = true;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
                .build();
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);

        TypedArray array = theme.obtainStyledAttributes(new int[]{
                R.attr.colorPrimary,
                android.R.attr.textColorPrimary,
                R.attr.colorAccent,
        });
        int primaryColor = array.getColor(0, 0xFF11FF);
        LogHelper.error("primarycolor:" + primaryColor);
        int primaryTextColor = array.getColor(1, 0xFF00FF);
        int accentColor = array.getColor(2, 0xFF11FF);
        array.recycle();

        ThemeConfig themeConfig = new ThemeConfig.Builder()
                .setTitleBarBgColor(primaryColor)
                .setTitleBarTextColor(primaryTextColor)
                .setFabNornalColor(accentColor)
                .setFabPressedColor(Color.DKGRAY)
                .setCheckNornalColor(Color.WHITE)
                .setCheckSelectedColor(accentColor).build();

        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(false)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(false).build();

        ImageLoader imageloader = new UILImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(getContext(), imageloader, themeConfig)
                .setDebug(BuildConfig.DEBUG)
                .setFunctionConfig(functionConfig).build();
        GalleryFinal.init(coreConfig);
    }

}
