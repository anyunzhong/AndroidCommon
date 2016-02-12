package net.datafans.android.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.datafans.android.common.AndroidCommon;
import net.datafans.android.test.R;

import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class ImagePickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);


        AndroidCommon.configImagePicker(getTheme());

        //https://github.com/pengjianbo/GalleryFinal

        TextView textView = (TextView) findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //FunctionConfig config = new FunctionConfig.Builder().setMutiSelectMaxSize(9).build();
                GalleryFinal.openGalleryMuti(1, 9, new GalleryFinal.OnHanlderResultCallback() {
                    public void onHanlderSuccess(int i, List<PhotoInfo> list) {
                        Log.e("success", list + "");
                    }

                    @Override
                    public void onHanlderFailure(int i, String s) {
                        Log.e("fail", s + "");
                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_picker, menu);
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
