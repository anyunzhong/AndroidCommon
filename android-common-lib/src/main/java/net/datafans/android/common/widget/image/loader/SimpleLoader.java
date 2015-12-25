package net.datafans.android.common.widget.image.loader;

/**
 * Created by zhonganyun on 15/8/28.
 */
public class SimpleLoader extends AbstractLoader {


    private static SimpleLoader loader = new SimpleLoader();

    private SimpleLoader() {
    }

    public static SimpleLoader sharedInstance() {
        return loader;
    }

    public void load(String url, WebFileLoader.LoaderCallback callback) {

        byte[] bytes = getBytes(url);

        if (bytes != null) {
            callback.onSuccess(bytes);
        } else {
            LoadTask task = new LoadTask();
            task.execute(url, callback);
        }
    }

    @Override
    protected void onLoadSuccess(byte[] bytes, Object callback) {
        ((WebFileLoader.LoaderCallback) callback).onSuccess(bytes);
    }
}
