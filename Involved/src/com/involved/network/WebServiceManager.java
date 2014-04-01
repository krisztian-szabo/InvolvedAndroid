package com.involved.network;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.involved.interfaces.WebServiceInterface;
import com.involved.model.Project;
import com.involved.model.User;
import com.involved.session.SessionManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class WebServiceManager {
	private static WebServiceManager _instance = null;
	// private static final String BASE_URL =
	// "http://stark-crag-7677.herokuapp.com/";
	private static final String BASE_URL = "http://10.10.10.40:3000/";
	private static final String USERS_URL = BASE_URL + "users.json";
	private static final String ACTIONS_URL = BASE_URL + "actions.json";
	private static final String PROJECTS_URL = BASE_URL + "projects.json";
	private static final String USERS_PROJECTS_URL = BASE_URL
			+ "users_projects.json";
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
				delegate.onFailure(content);
			}
		});
	}

	public void login(String email, String uid, String firstName,
			String lastName, String pictureUrl,
			final WebServiceInterface delegate) {
		RequestParams params = new RequestParams();
		params.put("user[email]", email);
		params.put("user[uid]", uid);
		params.put("user[first_name]", firstName);
		params.put("user[last_name]", lastName);
		params.put("user[image_url]", pictureUrl);

		client.post(USERS_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				User user = User.fromString(content);
				SessionManager.getInstance().setCurrentUser(user);
				delegate.onSuccess(user);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				delegate.onFailure(content);
			}
		});
	}

	public void joinProject(final Project project,
			final WebServiceInterface delegate) {
		RequestParams params = new RequestParams();
		params.put("users_projects[user_id]", ""
				+ SessionManager.getInstance().getCurrentUser().getId());
		params.put("users_projects[project_id]", "" + project.getId());

		client.post(USERS_PROJECTS_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				try {
					JSONObject json = new JSONObject(content);
					if (json.getString("project_id").equalsIgnoreCase(
							project.getId() + "")) {
						delegate.onSuccess(project);
					} else {
						delegate.onFailure("Something went wrong!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				delegate.onFailure(content);
			}
		});
	}
}
