package net.datafans.android.common.widget.popup;

import android.view.View;

/**
 * Created by zhonganyun on 15/10/19.
 */
public class PopItem {

    private String title;
    private Listener listener;

    public PopItem(String title, Listener listener) {
        this.title = title;
        this.listener = listener;
    }

    public String getTitle() {
        return title;
    }

    public Listener getListener() {
        return listener;
    }


    public interface Listener {
        void onClick();
    }

}
