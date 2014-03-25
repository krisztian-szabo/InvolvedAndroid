package com.involved.network;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.involved.interfaces.WebServiceInterface;
import com.involved.model.Project;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class WebServiceManager {
	private static WebServiceManager _instance = null;
	private static final String BASE_URL = "http://stark-crag-7677.herokuapp.com/";
	private static final String ACTIONS_URL = BASE_URL + "actions.json";
	private static final String PROJECTS_URL = BASE_URL + "projects.json";
	private static AsyncHttpClient client;

	private WebServiceManager() {
		client = new AsyncHttpClient();
	}

	public static WebServiceManager getInstance() {
		if (_instance == null)
			_instance = new WebServiceManager();
		return _instance;
	}

	public void getActions(final WebServiceInterface delegate) {
		client.get(ACTIONS_URL, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				System.out.println(response);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				System.out.println(content);
			}
		});
	}

	public void getProjects(final WebServiceInterface delegate) {
		client.get(PROJECTS_URL, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				Gson gson = new GsonBuilder().setDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss").create();
				Type projectType = new TypeToken<List<Project>>() {
				}.getType();
				List<Project> projects = gson.fromJson(response, projectType);
				delegate.onSuccess(projects);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				System.out.println(content);
			}
		});
	}

}
