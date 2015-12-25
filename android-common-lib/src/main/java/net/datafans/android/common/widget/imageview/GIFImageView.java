package net.datafans.android.common.widget.imageview;

import android.content.Context;
import android.util.AttributeSet;

import net.datafans.android.common.widget.image.loader.GifImageViewLoader;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by zhonganyun on 15/8/29.
 */
public class GIFImageView extends GifImageView {

    public GIFImageView(Context context) {
        super(context);
    }

    public GIFImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void load(String url) {
        GifImageViewLoader.sharedInstance().load(this, url);
    }
}
