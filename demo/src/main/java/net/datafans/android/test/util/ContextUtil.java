package net.datafans.android.test.util;

import net.datafans.android.common.AndroidCommon;
import net.datafans.android.common.widget.imageview.ImageViewType;
import android.app.Application;
import android.graphics.Color;

public class ContextUtil extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		AndroidCommon.onCreate(this);
		AndroidCommon.getAppearance().setStatusBarTintColor(
				Color.rgb(24, 30, 43));
		AndroidCommon.setImageViewType(ImageViewType.Smart);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

		AndroidCommon.onTerminate();
	}
}
