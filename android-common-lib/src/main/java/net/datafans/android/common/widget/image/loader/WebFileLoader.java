package net.datafans.android.common.widget.image.loader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.LruCache;

import com.jakewharton.disklrucache.DiskLruCache;

import net.datafans.android.common.helper.IOHelper;
import net.datafans.android.common.helper.LogHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhonganyun on 15/8/28.
 */
public class WebFileLoader {

    private static WebFileLoader loader = new WebFileLoader();

    private Context context;

    private LruCache<String, byte[]> memoryCache;

    private DiskLruCache diskCache;


    private final static int DEFAULT_DISK_CACHE_CAPACITY = 10 * 1024 * 1024;

    private WebFileLoader() {
    }

    public static WebFileLoader sharedInstance() {
        return loader;
    }

    public Context getContext() {
        return context;
    }

    public void create(Context context, int capacity) {
        this.context = context;

        initMemoryCache();
        initDiskCache(capacity);
    }

    public void create(Context context) {
        create(context, DEFAULT_DISK_CACHE_CAPACITY);
    }


    private void initMemoryCache() {

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;

        if (memoryCache != null) return;

        memoryCache = new LruCache<String, byte[]>(cacheSize) {
            @Override
            protected int sizeOf(String key, byte[] bytes) {
                return bytes.length;
            }
        };
    }


    private void initDiskCache(int capacity) {

        if (diskCache != null) return;

        try {
            File cacheDir = getDiskCacheDir(context, "web_file");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
                LogHelper.debug("Create Dir: " + cacheDir.getAbsolutePath());
            }

            LogHelper.debug("Disk Cache Dir: " + cacheDir.getAbsolutePath());

            diskCache = DiskLruCache
                    .open(cacheDir, getAppVersion(context), 1, capacity);
        } catch (IOException e) {
            LogHelper.error(e);
        }
    }


    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        return new File(cachePath + File.separator + uniqueName);
    }


    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            LogHelper.error(e);
        }
        return 1;
    }


    public String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    private String getDefaultKey(String url) {
        return md5(url);
    }


    public byte[] getFromMemoryCacheByKey(String key) {

        byte[] bytes = memoryCache.get(key);
        if (bytes != null) {
            LogHelper.debug("Memory Cache Hit");
        } else {
            LogHelper.debug("Memory Cache Miss");
        }

        return bytes;
    }

    public byte[] getFromMemoryCache(String url) {

        String key = getDefaultKey(url);
        return getFromMemoryCacheByKey(key);
    }


    public DiskLruCache.Snapshot getFromDiskCache(String url) throws IOException {
        String key = md5(url);
        return getFromDiskCacheByKey(key);
    }


    public DiskLruCache.Snapshot getFromDiskCacheByKey(String key) throws IOException {
        return diskCache.get(key);
    }


    public void saveToDiskCache(String url, InputStream is) throws IOException {

        saveToDiskCacheByKey(getDefaultKey(url), is);
    }


    public void saveToDiskCacheByKey(String key, InputStream is) throws IOException {

        DiskLruCache.Editor editor = diskCache.edit(key);
        if (editor != null) {
            OutputStream os = editor.newOutputStream(0);
            IOHelper.copy(is, os);
            editor.commit();
            IOHelper.closeQuietly(os);
        }

        LogHelper.debug("Save To Disk Cache");
    }


    public void saveToMemoryCache(String url, byte[] bytes) {
        saveToMemoryCacheByKey(getDefaultKey(url), bytes);
    }


    public void saveToMemoryCacheByKey(String key, byte[] bytes) {
        memoryCache.put(key, bytes);
        LogHelper.debug("Save To Memory Cache");
    }


    public void load(String url, LoaderCallback callback) {
        SimpleLoader.sharedInstance().load(url, callback);
    }


    public static interface LoaderCallback {
        void onSuccess(byte[] bytes);
    }

}
