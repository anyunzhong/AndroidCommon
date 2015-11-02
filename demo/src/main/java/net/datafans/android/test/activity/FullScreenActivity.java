package net.datafans.android.test.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.datafans.android.common.widget.controller.FragmentController;
import net.datafans.android.test.R;

public class FullScreenActivity extends FragmentController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getStatusBarColor() {
        return Color.TRANSPARENT;
    }

    @Override
    protected boolean enableFullScreen() {
        return true;
    }

    @Override
    protected Fragment getRootFragment() {
        return new FullScreenFragment();
    }

}
