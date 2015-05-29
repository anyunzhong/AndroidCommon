package net.datafans.android.common.widget.base;

import net.datafans.android.common.R;
import net.datafans.android.common.data.service.BaseResponse;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

public abstract class Controller extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		initFragment();
	}

	private void initFragment() {

		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.container, getRootFragment());
		transaction.commit();
	}

	protected abstract Fragment getRootFragment();

	protected void onStatusOk(BaseResponse response, Class<?> type) {

	}

	protected void onStatusError(BaseResponse response) {
		Log.e("statusError", response.toString());
	}

	protected void onRequestError(int errorCode, byte[] errorResponse,
			Throwable throwable) {
		if (errorCode == -2) {
			Log.e("exception", "network exception");
		}
		
		if (errorCode == -1) {
			Log.e("exception", "data_parse_exception");
		}
	}
}
