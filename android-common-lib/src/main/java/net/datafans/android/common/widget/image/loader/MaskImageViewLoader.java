package net.datafans.android.common.widget.image.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;

import net.datafans.android.common.helper.IOHelper;
import net.datafans.android.common.helper.LogHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhonganyun on 15/8/28.
 */
public class MaskImageViewLoader extends AbstractLoader {

    private static MaskImageViewLoader loader = new MaskImageViewLoader();

    private MaskImageViewLoader() {
    }

    public static MaskImageViewLoader sharedInstance() {
        return loader;
    }

    public void load(ImageView imageView, String url, int mask, String maskAlias, int maxWidth) {

        String key = getGifKey(url, maskAlias);

        Bitmap bitmap = getBitmapByKey(key);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            MaskLoadTask task = new MaskLoadTask();
            task.execute(url, imageView, mask, maskAlias, maxWidth);
        }
    }


    @Override
    protected void onLoadSuccess(byte[] bytes, Object view) {

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if (bitmap != null) ((ImageView) view).setImageBitmap(bitmap);
    }

    protected class MaskLoadTask extends AsyncTask<Object, Void, byte[]> {

        private String url;
        private Object view;
        private int mask;
        private String maskAlias;
        private int maxWidth;

        @Override
        protected byte[] doInBackground(Object... objects) {

            WebFileLoader loader = WebFileLoader.sharedInstance();
            url = (String) objects[0];
            view = objects[1];
            mask = (int) objects[2];
            maskAlias = (String) objects[3];
            maxWidth = (int) objects[4];

            String key = getGifKey(url, maskAlias);

            InputStream is = null;
            try {
                DiskLruCache.Snapshot snapshot = loader.getFromDiskCacheByKey(key);
                if (snapshot == null) {

                    LogHelper.debug("Disk Cache Miss");

                    is = getStream(url);

                    byte[] bytes = merge(WebFileLoader.sharedInstance().getContext(), is, mask, maxWidth);
                    IOHelper.closeQuietly(is);

                    InputStream mis = new ByteArrayInputStream(bytes);
                    loader.saveToDiskCacheByKey(key, mis);
                    IOHelper.closeQuietly(mis);
                    snapshot = loader.getFromDiskCacheByKey(key);
                } else {
                    LogHelper.debug("Disk Cache Hit");
                }


                if (snapshot != null) {
                    InputStream sis = snapshot.getInputStream(0);
                    byte[] bytes = IOHelper.toByteArray(sis);
                    IOHelper.closeQuietly(sis);
                    loader.saveToMemoryCacheByKey(key, bytes);
                    return bytes;
                }

            } catch (IOException e) {
                LogHelper.error(e);
            } finally {
                IOHelper.closeQuietly(is);
            }
            return null;
        }


        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            onLoadSuccess(bytes, view);
        }
    }


    private byte[] merge(Context context, InputStream stream, int maskImage, int maxSize) {

        try {
            Bitmap original = BitmapFactory.decodeStream(stream);

            int width = original.getWidth();
            int height = original.getHeight();



            if (width >= height){
                if (width > maxSize) {
                    float rate = (float) maxSize / width;
                    Matrix matrix = new Matrix();
                    matrix.postScale(rate, rate);
                    original = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
                }
            }else{

                if (height > maxSize) {
                    float rate = (float) maxSize / height;
                    Matrix matrix = new Matrix();
                    matrix.postScale(rate, rate);
                    original = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
                }
            }

            Bitmap mask = BitmapFactory.decodeResource(context.getResources(), maskImage);
            Bitmap result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(result);
            canvas.drawBitmap(original, 0, 0, null);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            NinePatch patch = new NinePatch(mask, mask.getNinePatchChunk(), null);
            patch.draw(canvas, new Rect(0, 0, original.getWidth(), original.getHeight()), paint);

            return bitmap2Bytes(result);


        } catch (Exception e) {
            LogHelper.error(e);
        }
        return null;
    }


    public byte[] bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, os);
        IOHelper.closeQuietly(os);
        return os.toByteArray();
    }

    private String getGifKey(String url, String maskAlias) {

        return WebFileLoader.sharedInstance().md5(url + "_" + maskAlias);
    }
}
