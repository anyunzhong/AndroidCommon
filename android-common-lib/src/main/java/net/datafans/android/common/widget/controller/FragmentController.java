package net.datafans.android.common.widget.controller;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import net.datafans.android.common.R;
import net.datafans.android.common.data.service.BaseResponse;
import net.datafans.android.common.data.service.DataService;
import net.datafans.android.common.helper.DipHelper;

public abstract class FragmentController extends Controller {

    protected Toolbar toolbar;

    protected RelativeLayout containerParent;

    private View container;

    private ProgressBar loadingView;

    private LinearLayout loadFailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);


        View rootView;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {


            if (enableFullScreen()) {
                rootView = getLayoutInflater().inflate(R.layout.activity_base_full, null);
            } else {
                rootView = getLayoutInflater().inflate(R.layout.activity_base, null);
            }
        } else {
            rootView = getLayoutInflater().inflate(R.layout.activity_base_comp, null);
        }
        setContentView(rootView);

        containerParent = (RelativeLayout) rootView.findViewById(R.id.container_parent);

        container = containerParent.findViewById(R.id.container);

        initNavbar();

        if (getRootFragment() != null)
            initFragment();

    }

    private void initNavbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (!enableReturnButton())
            toolbar.setNavigationIcon(null);
        toolbar.setTitle(getNavTitle());
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(getStatusBarColor());
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickReturnButton();
            }
        });

        View cusView = getCusToolbarView();
        if (cusView != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.rightMargin = DipHelper.dip2px(this, 15);
            cusView.setBackgroundColor(Color.TRANSPARENT);
            toolbar.addView(cusView, params);
        }

    }

    protected void changeTitle(String title) {
        toolbar.setTitle(title);
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

    public void showLoadingView() {
        showLoading(true);
    }


    public void hideLoadingView() {
        showLoading(false);
    }


    public void showLoadFailView() {
        showLoadFail(true);
    }


    public void hideLoadFailView() {
        showLoadFail(false);
    }


    protected boolean enableAutoLoadStateView() {
        return true;
    }

    @Override
    public void onStatusOk(BaseResponse response, DataService service) {
        super.onStatusOk(response, service);
        if (enableAutoLoadStateView()) {
            hideLoadingView();
            hideLoadFailView();
        }
    }

    @Override
    public void onStatusError(BaseResponse response, DataService service) {
        super.onStatusError(response, service);
        if (enableAutoLoadStateView()) {
            hideLoadingView();
            showLoadFailView();
        }

    }

    @Override
    public void onRequestError(int errorCode, byte[] errorResponse,
                               Throwable throwable, DataService service) {
        super.onRequestError(errorCode, errorResponse, throwable, service);

        if (enableAutoLoadStateView()) {
            hideLoadingView();
            showLoadFailView();
        }
    }


    protected abstract Fragment getRootFragment();

    protected int getFragmentPadding() {
        return 0;
    }

    protected View getCusToolbarView() {
        return null;
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
