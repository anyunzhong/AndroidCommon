package net.datafans.android.common.widget.image.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.jakewharton.disklrucache.DiskLruCache;

import net.datafans.android.common.helper.IOHelper;
import net.datafans.android.common.helper.LogHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by zhonganyun on 15/8/28.
 */
public abstract class AbstractLoader {


    protected Bitmap getBitmap(String url) {
        byte[] bytes = WebFileLoader.sharedInstance().getFromMemoryCache(url);

        if (bytes != null)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        return null;
    }


    protected byte[] getBytes(String url) {
        return WebFileLoader.sharedInstance().getFromMemoryCache(url);
    }


    protected Bitmap getBitmapByKey(String key) {
        byte[] bytes = WebFileLoader.sharedInstance().getFromMemoryCacheByKey(key);

        if (bytes != null)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        return null;
    }


    protected abstract void onLoadSuccess(byte[] bytes, Object view);

    protected class LoadTask extends AsyncTask<Object, Void, byte[]> {

        private String url;
        private Object view;

        @Override
        protected byte[] doInBackground(Object... objects) {

            WebFileLoader loader = WebFileLoader.sharedInstance();
            url = (String) objects[0];
            view = objects[1];

            InputStream is = null;
            try {
                DiskLruCache.Snapshot snapshot = loader.getFromDiskCache(url);
                if (snapshot == null) {

                    LogHelper.debug("Disk Cache Miss");

                    is = getStream(url);
                    if (is != null) loader.saveToDiskCache(url, is);
                    snapshot = loader.getFromDiskCache(url);
                } else {
                    LogHelper.debug("Disk Cache Hit");
                }


                if (snapshot != null) {
                    InputStream sis = snapshot.getInputStream(0);
                    byte[] bytes = IOHelper.toByteArray(sis);
                    IOHelper.closeQuietly(sis);
                    loader.saveToMemoryCache(url, bytes);
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

    protected InputStream getStream(String url) {

        try {

            URL localURL = new URL(url);
            URLConnection connection = localURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            return httpURLConnection.getInputStream();

        } catch (Exception e) {
            LogHelper.error(e);
            return null;
        }

    }

}
