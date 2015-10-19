package net.datafans.android.test.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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


        TextView textView = (TextView) findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
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
