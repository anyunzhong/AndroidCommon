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

    public void load(String url, String thumbData, int mask, String maskAlias, int maxWidth) {
        MaskImageViewLoader.sharedInstance().load(this, url, thumbData, mask, maskAlias, maxWidth, false);
    }

    public void load(int resId, int mask, String maskAlias, int maxWidth) {
        MaskImageViewLoader.sharedInstance().load(this, resId, mask, maskAlias, maxWidth, false);
    }

    public void load(String url, String thumbData, int mask, String maskAlias, int maxWidth, boolean withLabel) {
        MaskImageViewLoader.sharedInstance().load(this, url, thumbData, mask, maskAlias, maxWidth, withLabel);
    }

    public void load(int resId, int mask, String maskAlias, int maxWidth, boolean withLabel) {
        MaskImageViewLoader.sharedInstance().load(this, resId, mask, maskAlias, maxWidth, withLabel);
    }
}
