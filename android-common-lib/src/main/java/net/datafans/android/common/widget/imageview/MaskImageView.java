package net.datafans.android.common.widget.imageview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import net.datafans.android.common.widget.image.loader.MaskImageViewLoader;

/**
 * Created by zhonganyun on 15/8/29.
 */
public class MaskImageView extends ImageView {

    public MaskImageView(Context context) {
        super(context);
    }

    public MaskImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void load(String url, int mask, String maskAlias, int maxWidth) {
        MaskImageViewLoader.sharedInstance().load(this, url, mask, maskAlias, maxWidth);
    }
}
