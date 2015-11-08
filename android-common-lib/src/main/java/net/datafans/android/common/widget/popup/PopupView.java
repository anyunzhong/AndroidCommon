package net.datafans.android.common.widget.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhonganyun on 15/10/19.
 */
public class PopupView {

    private Context context;

    private View rootView;

    private boolean dismissOnTouchSpace;

    private PopupWindow popupWindow;

    private TextView titleView;
    private TextView descView;
    private View divider;
    private LinearLayout contentView;

    private View cusDivider;
    private LinearLayout cusContentView;

    public PopupView(Context context, View rootView, boolean dismissOnTouchSpace) {
        this.context = context;
        this.rootView = rootView;
        this.dismissOnTouchSpace = dismissOnTouchSpace;
        init();
    }

    public PopupView(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
        this.dismissOnTouchSpace = true;
        init();
    }


    protected int getLayout() {
        return R.layout.popup;
    }


    private void init() {
        View popview = LayoutInflater.from(context).inflate(getLayout(), null);

        View focusView = popview.findViewById(R.id.focusArea);

        titleView = (TextView) popview.findViewById(R.id.title);
        descView = (TextView) popview.findViewById(R.id.desc);
        divider = popview.findViewById(R.id.divider);
        contentView = (LinearLayout) popview.findViewById(R.id.content);

        cusDivider = popview.findViewById(R.id.cusDivider);
        cusContentView = (LinearLayout) popview.findViewById(R.id.cusContent);

        popupWindow = new PopupWindow(popview,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(90, 0, 0, 0)));


        focusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        popview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dismissOnTouchSpace)
                    popupWindow.dismiss();
            }
        });

    }

    public void setTitle(String title) {
        titleView.setVisibility(View.VISIBLE);
        divider.setVisibility(View.VISIBLE);
        titleView.setText(title);
    }

    public void setTitle(String title, int color) {
        titleView.setTextColor(color);
        setTitle(title);
    }

    public void setDesc(String desc) {
        descView.setVisibility(View.VISIBLE);
        divider.setVisibility(View.VISIBLE);
        descView.setText(desc);
    }


    public void setItems(final List<PopItem> items) {

        setItems(items, false);
    }

    protected boolean isActionSheet(){
        return false;
    }

    public void setItems(final List<PopItem> items, boolean vertical) {
        if (items == null || items.isEmpty()) return;

        int count = items.size();

        LinearLayout layout;
        if (count == 2 && !vertical && !isActionSheet()) {

            layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.popup_two_item, null);
            TextView leftTextView = (TextView) layout.findViewById(R.id.textLeft);
            leftTextView.setText(items.get(0).getTitle());
            leftTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    items.get(0).getListener().onClick();
                }
            });

            TextView rightTextView = (TextView) layout.findViewById(R.id.textRight);
            rightTextView.setText(items.get(1).getTitle());
            rightTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    items.get(1).getListener().onClick();
                }
            });


        } else {

            layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.popup_more_item, null);
            ListView listView = (ListView) layout.findViewById(R.id.listView);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    popupWindow.dismiss();
                    items.get(i).getListener().onClick();
                }
            });

            ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("ItemText", items.get(i).getTitle());
                listItem.add(map);
            }


            SimpleAdapter adapter = new SimpleAdapter(context, listItem,
                    R.layout.popup_one_item, new String[]{"ItemText"},
                    new int[]{R.id.text});
            listView.setAdapter(adapter);

        }


        setView(layout);
    }


    public void setView(View view) {
        contentView.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        contentView.addView(view, params);
    }


    public void show() {
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
    }

    public void setCustomContentView(View view){
        if (view == null) return;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cusContentView.addView(view,params);

        cusDivider.setVisibility(View.VISIBLE);

    }



}
