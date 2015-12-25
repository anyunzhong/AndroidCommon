package net.datafans.android.common.widget.imageview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import net.datafans.android.common.widget.image.loader.PlainImageViewLoader;

/**
 * Created by zhonganyun on 15/8/29.
 */
public class PlainImageView extends ImageView {

    public PlainImageView(Context context) {
        super(context);
    }

    public PlainImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void load(String url) {
        PlainImageViewLoader.sharedInstance().load(this, url);
    }
}
