package net.datafans.android.test.util;

import net.datafans.android.common.config.AndroidCommon;
import net.datafans.android.common.widget.imageview.ImageViewType;
import android.app.Application;
import android.graphics.Color;

public class ContextUtil extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		AndroidCommon.onCreate(this);
		AndroidCommon.getAppearence().setStatusBarTintColor(
				Color.rgb(24, 30, 43));
		AndroidCommon.setImageViewType(ImageViewType.Cube);
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
