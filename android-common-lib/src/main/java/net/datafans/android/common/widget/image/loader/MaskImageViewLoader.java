package net.datafans.android.common.widget.image.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Base64;
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

    public void load(ImageView imageView, String url, String thumbData, int mask, String maskAlias, int maxWidth, boolean withLabel) {

        Bitmap bitmap = null;

        if (url != null && !url.equals("")) {
            String key = getGifKey(url, maskAlias);
            bitmap = getBitmapByKey(key);
        }

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            //在请求大图之前 先加载不清晰的小图 基本是马赛克级别
            if (thumbData != null && !thumbData.equals("")) {
                byte[] bytes = Base64.decode(thumbData, Base64.DEFAULT);
                InputStream inputStream = new ByteArrayInputStream(bytes);
                Bitmap bit = mergeBitmap(WebFileLoader.sharedInstance().getContext(), inputStream, mask, maxWidth, withLabel);
                imageView.setImageBitmap(bit);
            }

            if (url == null || url.equals("")) {
                return;
            }
            MaskLoadTask task = new MaskLoadTask();
            task.execute(url, imageView, mask, maskAlias, maxWidth, withLabel);
        }
    }

    public void load(ImageView imageView, int resId, int mask, String maskAlias, int maxWidth, boolean withLabel) {

        Context context = WebFileLoader.sharedInstance().getContext();

        Bitmap bitmap = mergeBitmap(context, context.getResources().openRawResource(resId), mask, maxWidth, withLabel);
        imageView.setImageBitmap(bitmap);
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
        private boolean withLabel;

        @Override
        protected byte[] doInBackground(Object... objects) {

            WebFileLoader loader = WebFileLoader.sharedInstance();
            url = (String) objects[0];
            view = objects[1];
            mask = (int) objects[2];
            maskAlias = (String) objects[3];
            maxWidth = (int) objects[4];
            withLabel = (boolean) objects[5];

            String key = getGifKey(url, maskAlias);

            InputStream is = null;
            try {
                DiskLruCache.Snapshot snapshot = loader.getFromDiskCacheByKey(key);
                if (snapshot == null) {

                    LogHelper.debug("Disk Cache Miss");

                    is = getStream(url);

                    byte[] bytes = merge(WebFileLoader.sharedInstance().getContext(), is, mask, maxWidth, withLabel);
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


    private Bitmap mergeBitmap(Context context, InputStream stream, int maskImage, int maxSize, boolean withLabel) {

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            if (width >= height) {
                float rate = (float) maxSize / width;
                Matrix matrix = new Matrix();
                matrix.postScale(rate, rate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            } else {
                float rate = (float) maxSize / height;
                Matrix matrix = new Matrix();
                matrix.postScale(rate, rate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }


            width = bitmap.getWidth();
            height = bitmap.getHeight();
            int border = 1;
            int color = 130;

            Bitmap bgBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Bitmap targetBmp = Bitmap.createBitmap(width-2*border, height-2*border, Bitmap.Config.ARGB_8888);

            Bitmap mask = BitmapFactory.decodeResource(context.getResources(), maskImage);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);

            Canvas bgCanvas = new Canvas(bgBmp);
            bgCanvas.drawRGB(color, color, color);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            NinePatch patch = new NinePatch(mask, mask.getNinePatchChunk(), null);
            patch.draw(bgCanvas, new Rect(0, 0, width, height), paint);



            Canvas targetCanvas = new Canvas(targetBmp);
            targetCanvas.drawBitmap(bitmap, 0, 0, null);
            if (withLabel) {
                Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                labelPaint.setColor(Color.DKGRAY);
                labelPaint.setAlpha(200);
                labelPaint.setStyle(Paint.Style.FILL);
                targetCanvas.drawRect(0, bitmap.getHeight() - 110, bitmap.getWidth(), bitmap.getHeight(), labelPaint);
            }
            patch.draw(targetCanvas, new Rect(0, 0, width-2*border, height-2*border), paint);

            bgCanvas.drawBitmap(targetBmp, border, border, null);

            return  bgBmp;

        } catch (Exception e) {
            LogHelper.error(e.toString());
        }
        return null;
    }

    private byte[] merge(Context context, InputStream stream, int maskImage, int maxSize, boolean withLabel) {
        return bitmap2Bytes(mergeBitmap(context, stream, maskImage, maxSize, withLabel));
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
