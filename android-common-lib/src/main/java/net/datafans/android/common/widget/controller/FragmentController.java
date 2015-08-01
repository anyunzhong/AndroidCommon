package net.datafans.android.common.widget.controller;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import net.datafans.android.common.R;
import net.datafans.android.common.data.service.BaseResponse;

public abstract class FragmentController extends Controller {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initNavbar();

        if (getRootFragment() != null)
            initFragment();
    }

    private void initNavbar() {

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (!enableReturnButton())
            mToolbar.setNavigationIcon(null);
        mToolbar.setTitle(getNavTitle());
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setBackgroundColor(getStatusBarColor());
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickReturnButton();
            }
        });
    }

    private void initFragment() {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, getRootFragment());
        transaction.commit();
    }


    protected abstract Fragment getRootFragment();

    protected int getFragmentPadding() {
        return 0;
    }

    protected boolean enableReturnButton() {
        return true;
    }

    protected String getNavTitle() {
        return "";
    }

    protected void onClickReturnButton() {
        this.finish();
    }

}
