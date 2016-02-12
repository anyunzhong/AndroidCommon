package net.datafans.android.common.widget.photobrowser;

import android.graphics.Bitmap;

/**
 * Created by zhonganyun on 16/2/12.
 */
public class Photo {
    private String url;
    private Bitmap bitmap;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
