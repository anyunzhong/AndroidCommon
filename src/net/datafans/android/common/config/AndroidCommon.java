package net.datafans.android.common.config;

import android.content.Context;

public class AndroidCommon {

	private static Context ctx;

	private static GlobalAppearence appearence = new GlobalAppearence();

	public final static AndroidCommon common = new AndroidCommon();

	public static AndroidCommon sharedInstance() {
		return common;
	}

	public static Context getContext() {
		return ctx;
	}

	public static void onCreate(Context context) {
		ctx = context;
	}

	public static void onTerminate() {

	}

	public static GlobalAppearence getAppearence() {
		return appearence;
	}
}
