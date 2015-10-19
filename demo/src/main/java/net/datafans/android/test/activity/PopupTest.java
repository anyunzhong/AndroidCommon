package net.datafans.android.test.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.datafans.android.common.widget.popup.ActionSheetView;
import net.datafans.android.common.widget.popup.PopItem;
import net.datafans.android.common.widget.popup.PopupView;
import net.datafans.android.test.R;

import java.util.ArrayList;
import java.util.List;

public class PopupTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = getLayoutInflater().inflate(R.layout.activity_popup_test, null);
        setContentView(rootView);


        TextView alertTextView = (TextView) findViewById(R.id.alertMode);
        alertTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupView popupView = new PopupView(PopupTest.this, rootView, false);
                popupView.setTitle("确定删除吗?", Color.RED);
                popupView.setDesc("删除后将不能恢复 请慎重操作");

                List<PopItem> items = new ArrayList<PopItem>();
                items.add(new PopItem("确定", new PopItem.Listener() {
                    @Override
                    public void onClick() {
                        Log.d("Pop", "Click");
                    }
                }));

                items.add(new PopItem("取消", new PopItem.Listener() {
                    @Override
                    public void onClick() {
                        Log.d("Pop", "Click");
                    }
                }));


//                items.add(new PopItem("其它选项", new PopItem.Listener() {
//                    @Override
//                    public void onClick() {
//                        Log.d("Pop", "Click");
//                    }
//                }));

                popupView.setItems(items);


                popupView.show();
            }
        });





        TextView sheetTextView = (TextView) findViewById(R.id.sheetMode);
        sheetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupView popupView = new ActionSheetView(PopupTest.this, rootView, true);
                //popupView.setTitle("选择照片", Color.RED);

                List<PopItem> items = new ArrayList<PopItem>();
                items.add(new PopItem("相册", new PopItem.Listener() {
                    @Override
                    public void onClick() {
                        Log.d("Pop", "Click");
                    }
                }));

                items.add(new PopItem("拍摄", new PopItem.Listener() {
                    @Override
                    public void onClick() {
                        Log.d("Pop", "Click");
                    }
                }));


                items.add(new PopItem("历史照片", new PopItem.Listener() {
                    @Override
                    public void onClick() {
                        Log.d("Pop", "Click");
                    }
                }));

                popupView.setItems(items);


                popupView.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popup_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
