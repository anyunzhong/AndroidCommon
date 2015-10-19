package net.datafans.android.common.helper;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by zhonganyun on 15/9/22.
 */
public class ImageHelper {


    public static Bitmap scale(Bitmap source, int maxSize) {
        int width = source.getWidth();
        int height = source.getHeight();

        if (width >= height) {
            if (width > maxSize) {
                float rate = (float) maxSize / width;
                Matrix matrix = new Matrix();
                matrix.postScale(rate, rate);
                return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
            }
        } else {

            if (height > maxSize) {
                float rate = (float) maxSize / height;
                Matrix matrix = new Matrix();
                matrix.postScale(rate, rate);
                return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
            }
        }

        return source;
    }

}
