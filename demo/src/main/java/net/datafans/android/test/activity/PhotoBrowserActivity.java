package net.datafans.android.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.datafans.android.common.widget.photobrowser.Photo;
import net.datafans.android.common.widget.photobrowser.PhotoBrowser;
import net.datafans.android.test.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoBrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = getLayoutInflater().inflate(R.layout.activity_photo_browser, null);
        setContentView(rootView);

        TextView textView = (TextView) findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoBrowser browser = new PhotoBrowser(PhotoBrowserActivity.this, rootView);
                Photo photo = new Photo();
                photo.setUrl("http://b.hiphotos.baidu.com/image/pic/item/7a899e510fb30f2493c8cbedcc95d143ac4b0389.jpg");

                Photo photo2 = new Photo();
                photo2.setUrl("http://e.hiphotos.baidu.com/image/pic/item/241f95cad1c8a7866dcb6de06309c93d71cf507e.jpg");

                Photo photo3 = new Photo();
                photo3.setUrl("http://d.hiphotos.baidu.com/image/pic/item/42a98226cffc1e176110abdf4e90f603728de9b5.jpg");


                List<Photo> photos = new ArrayList<>();
                photos.add(photo);
                photos.add(photo2);
                photos.add(photo3);
                browser.addPhoto(photos);
                browser.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_browser, menu);
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
