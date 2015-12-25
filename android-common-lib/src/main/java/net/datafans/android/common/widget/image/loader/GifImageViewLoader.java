package net.datafans.android.common.widget.image.loader;

import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.imageview.GIFImageView;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by zhonganyun on 15/8/28.
 */
public class GifImageViewLoader extends AbstractLoader {


    private static GifImageViewLoader loader = new GifImageViewLoader();

    private GifImageViewLoader() {
    }

    public static GifImageViewLoader sharedInstance() {
        return loader;
    }

    public void load(GIFImageView imageView, String url) {

        byte[] bytes = getBytes(url);

        if (bytes != null) {
            try {
                GifDrawable drawable = new GifDrawable(bytes);
                imageView.setImageDrawable(drawable);
            } catch (IOException e) {
                LogHelper.error(e);
            }
        } else {
            LoadTask task = new LoadTask();
            task.execute(url, imageView);
        }
    }

    @Override
    protected void onLoadSuccess(byte[] bytes, Object view) {
        try {

            GifDrawable drawable = new GifDrawable(bytes);
            ((GIFImageView) view).setImageDrawable(drawable);
        } catch (IOException e) {
            LogHelper.error(e);
        }

    }
}
