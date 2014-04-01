package com.involved.session;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.involved.InvolvedApplication;
import com.involved.model.User;

public class SessionManager {
	private static final String CURRENT_USER_KEY = "me";
	private static SessionManager _instance = null;
	private SharedPreferences settings;
	private User currentUser;

	private SessionManager() {
		settings = InvolvedApplication.getContext().getSharedPreferences(
				"involved_pref", Context.MODE_PRIVATE);
		String userJson = settings.getString(CURRENT_USER_KEY, null);
		currentUser = User.fromString(userJson);
	}

	public static SessionManager getInstance() {
		if (_instance == null)
			_instance = new SessionManager();
		return _instance;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User user) {
		currentUser = user;
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(CURRENT_USER_KEY, currentUser.toString());
		editor.commit();
	}

	public void selectAccount(Context context) {
		AccountManager mAccountManager = AccountManager.get(context);
		Account[] accounts = mAccountManager
				.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		String[] names = new String[accounts.length];
		for (int i = 0; i < names.length; i++) {
			names[i] = accounts[i].name;
		}
		
	}

}
