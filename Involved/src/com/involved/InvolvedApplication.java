package com.involved;

import android.app.Application;
import android.content.Context;

public class InvolvedApplication extends Application {
	private static InvolvedApplication _instance;

	public InvolvedApplication() {
		_instance = this;
	}

	public static Context getContext() {
		return _instance;
	}
}
