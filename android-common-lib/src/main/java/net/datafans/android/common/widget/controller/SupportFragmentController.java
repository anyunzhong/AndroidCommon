package net.datafans.android.common.widget.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.datafans.android.common.R;
import net.datafans.android.common.config.AndroidCommon;
import net.datafans.android.common.data.service.BaseResponse;

public abstract class SupportFragmentController extends SupportController {

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

        int color = AndroidCommon.getAppearence().getStatusBarTintColor();
        LinearLayout navbar = (LinearLayout) findViewById(R.id.navbar);
        navbar.setBackgroundColor(color);


        if (getFragmentPadding() > 0) {
            LinearLayout containerParent = (LinearLayout) findViewById(R.id.container_parent);
            int padding = getFragmentPadding();
            containerParent.setPadding(padding, padding, padding, padding);
        }

        LinearLayout returnButton = (LinearLayout) findViewById(R.id.navbar_return_button);
        returnButton.setOnClickListener(returnButtonClickListener);
        View divider = findViewById(R.id.navbar_divider);
        if (!enableReturnButton()) {
            returnButton.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        }


        TextView titleView = (TextView) findViewById(R.id.navbar_title);
        titleView.setText(getNavTitle());


    }

    private void initFragment() {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, getRootFragment());
        transaction.commit();
    }


    private View.OnClickListener returnButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onClickReturnButton();
        }
    };


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


    protected void onStatusOk(BaseResponse response, Class<?> type) {

    }

    protected void onStatusError(BaseResponse response) {
        Log.e("statusError", response.toString());
        Toast toast = Toast.makeText(this, response.getErrorMsg(),
                Toast.LENGTH_SHORT);
        toast.show();
    }

    protected void onRequestError(int errorCode, byte[] errorResponse,
                                  Throwable throwable) {
        if (errorCode == -2) {
            Log.e("exception", "network exception");
            Toast toast = Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (errorCode == -1) {
            Log.e("exception", "data_parse_exception");
            Toast toast = Toast.makeText(this, "数据解析错误", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
