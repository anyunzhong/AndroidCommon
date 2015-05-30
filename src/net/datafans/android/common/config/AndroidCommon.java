package net.datafans.android.common.config;

import android.content.Context;

public class AndroidCommon {

	private static Context ctx;

	public static Context getContext() {
		return ctx;
	}

	public static void onCreate(Context context) {
		ctx = context;
	}

	public static void onTerminate() {

	}
}
