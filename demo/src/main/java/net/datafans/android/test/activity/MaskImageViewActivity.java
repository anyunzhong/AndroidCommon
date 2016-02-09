package net.datafans.android.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import net.datafans.android.common.widget.imageview.MaskImageView;
import net.datafans.android.test.R;

public class MaskImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mask_image_view);

        FrameLayout container = (FrameLayout) findViewById(R.id.container);

        MaskImageView maskImageView = new MaskImageView(this);
        container.addView(maskImageView);
        //maskImageView.load(R.drawable.image_bg, R.drawable.sender_text_node,"alias",600);

        String url = "http://cdn.appcomeon.com/d/p/447258977400481338.png_400x230.jpeg";
        maskImageView.load(url, null, R.drawable.sender_text_node, "alias", 400, true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mask_image_view, menu);
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
