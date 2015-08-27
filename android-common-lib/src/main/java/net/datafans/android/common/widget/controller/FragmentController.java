package net.datafans.android.common.widget.controller;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.datafans.android.common.R;
import net.datafans.android.common.data.service.BaseResponse;

public abstract class FragmentController extends Controller {

    private Toolbar mToolbar;

    private RelativeLayout containerParent;

    private View container;

    private ProgressBar loadingView;

    private LinearLayout loadFailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        View rootView = getLayoutInflater().inflate(R.layout.activity_base, null);
        setContentView(rootView);

        containerParent = (RelativeLayout) rootView.findViewById(R.id.container_parent);

        container = containerParent.findViewById(R.id.container);

        initNavbar();

        if (getRootFragment() != null)
            initFragment();

    }

    private void initNavbar() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

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

    protected void changeTitle(String title) {
        mToolbar.setTitle(title);
    }

    private void initFragment() {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, getRootFragment());
        transaction.commit();
    }


    private void showLoading(boolean show) {


        if (show) {
            if (loadingView == null) {
                loadingView = new ProgressBar(this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                containerParent.addView(loadingView, params);

            }
            loadingView.setVisibility(View.VISIBLE);
            container.setVisibility(View.GONE);
        } else {
            if (loadingView != null)
                loadingView.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);
        }

    }


    private void showLoadFail(boolean show) {


        if (show) {

            if (loadFailView == null) {
                loadFailView = (LinearLayout) getLayoutInflater().inflate(R.layout.load_fail_view, null);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                containerParent.addView(loadFailView, params);
                loadFailView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickLoadFailView();
                    }
                });
            }
            loadFailView.setVisibility(View.VISIBLE);
            container.setVisibility(View.GONE);
        } else {
            if (loadFailView != null)
                loadFailView.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);
        }

    }

    protected void showLoadingView() {
        showLoading(true);
    }


    protected void hideLoadingView() {
        showLoading(false);
    }


    protected void showLoadFailView() {
        showLoadFail(true);
    }


    protected void hideLoadFailView() {
        showLoadFail(false);
    }

    @Override
    public void onStatusOk(BaseResponse response, Class<?> type) {
        super.onStatusOk(response, type);
        hideLoadingView();
        hideLoadFailView();
    }

    @Override
    public void onStatusError(BaseResponse response) {
        super.onStatusError(response);
        hideLoadingView();
        showLoadFailView();

    }

    @Override
    public void onRequestError(int errorCode, byte[] errorResponse,
                               Throwable throwable) {
        super.onRequestError(errorCode, errorResponse, throwable);

        hideLoadingView();
        showLoadFailView();
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

    protected void onClickLoadFailView() {
    }

}
