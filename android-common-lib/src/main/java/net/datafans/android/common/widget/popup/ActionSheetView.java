package net.datafans.android.common.widget.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import net.datafans.android.common.R;
import net.datafans.android.common.widget.popup.PopItem;
import net.datafans.android.common.widget.popup.PopupView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhonganyun on 15/10/19.
 */
public class ActionSheetView extends PopupView {


    public ActionSheetView(Context context, View rootView, boolean dismissOnTouchSpace) {
        super(context, rootView, dismissOnTouchSpace);
    }

    public ActionSheetView(Context context, View rootView) {
        super(context, rootView);
    }

    @Override
    protected int getLayout() {
        return R.layout.actionsheet;
    }

    @Override
    protected boolean isActionSheet() {
        return true;
    }
}
