package net.datafans.android.common.widget.image.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * Created by zhonganyun on 15/8/28.
 */
public class PlainImageViewLoader extends AbstractLoader {


    private static PlainImageViewLoader loader = new PlainImageViewLoader();

    private PlainImageViewLoader() {
    }

    public static PlainImageViewLoader sharedInstance() {
        return loader;
    }

    public void load(ImageView imageView, String url) {

        Bitmap bitmap = getBitmap(url);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            LoadTask task = new LoadTask();
            task.execute(url, imageView);
        }
    }

    @Override
    protected void onLoadSuccess(byte[] bytes, Object view) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if (bitmap != null) ((ImageView) view).setImageBitmap(bitmap);
    }
}
