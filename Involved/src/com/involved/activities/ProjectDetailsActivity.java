package com.involved.activities;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.involved.R;
import com.involved.interfaces.WebServiceInterface;
import com.involved.model.Project;
import com.involved.model.User;
import com.involved.network.WebServiceManager;
import com.involved.session.SessionManager;
import com.involved.utils.InvolvedUtils;
import com.involved.views.InvolvedTextView;
import com.loopj.android.image.SmartImageView;

public class ProjectDetailsActivity extends ActionBarActivity implements
		WebServiceInterface {
	private final static String G_PLUS_SCOPE = "oauth2:https://www.googleapis.com/auth/plus.me";
	private final static String USERINFO_SCOPE = "https://www.googleapis.com/auth/userinfo.profile";
	private final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE;
	private static final int PICK_ACCOUNT_REQUEST = 111;
	private Project mProject;
	private SmartImageView mImage;
	private InvolvedTextView mName, mDescription, mNextAppointment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_details);

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		this.mProject = Project.fromString(getIntent()
				.getStringExtra("project"));
		findIds();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.project, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.action_join:
			if (SessionManager.getInstance().getCurrentUser() == null) {
				Intent googlePicker = AccountPicker.newChooseAccountIntent(
						null, null,
						new String[] { GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE },
						true, null, null, null, null);
				startActivityForResult(googlePicker, PICK_ACCOUNT_REQUEST);
			} else {
				WebServiceManager.getInstance().joinProject(mProject, this);
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		if (requestCode == PICK_ACCOUNT_REQUEST && resultCode == RESULT_OK) {
			new AsyncTask<Void, Void, Void>() {
				protected Void doInBackground(Void... params) {
					String accountName = data
							.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
					try {
						String token = GoogleAuthUtil.getToken(
								ProjectDetailsActivity.this, accountName,
								SCOPES);

						URL url = new URL(
								"https://www.googleapis.com/oauth2/v1/userinfo?access_token="
										+ token);
						HttpURLConnection con = (HttpURLConnection) url
								.openConnection();
						int serverCode = con.getResponseCode();
						// successful query
						if (serverCode == 200) {
							InputStream is = con.getInputStream();
							String userJson = InvolvedUtils
									.convertInputStreamToString(is);
							JSONObject json = new JSONObject(userJson);
							is.close();
							WebServiceManager.getInstance().login(accountName,
									json.getString("id"),
									json.getString("given_name"),
									json.getString("family_name"),
									json.getString("picture"),
									ProjectDetailsActivity.this);
							return null;
							// bad token, invalidate and get a new one
						} else if (serverCode == 401) {
							GoogleAuthUtil.invalidateToken(
									ProjectDetailsActivity.this, token);
							return null;
						} else {
							Log.e("Server returned the following error code: "
									+ serverCode, null);
							return null;
						}
					} catch (UserRecoverableAuthException e) {
						Intent intent = e.getIntent();
						startActivityForResult(intent, PICK_ACCOUNT_REQUEST);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				};
			}.execute((Void) null);
		}
	}

	private void findIds() {
		mImage = (SmartImageView) findViewById(R.id.project_image);
		mName = (InvolvedTextView) findViewById(R.id.project_name);
		mDescription = (InvolvedTextView) findViewById(R.id.project_details);
		mNextAppointment = (InvolvedTextView) findViewById(R.id.project_next);

		mImage.setImageUrl(mProject.getImage_url());
		mName.setText(mProject.getName());
		mDescription.setText("Details: " + mProject.getDescription());

		String nextApp = InvolvedUtils.getNextAppointmentForProject(mProject);
		mNextAppointment.setText("Next meeting: " + nextApp);
	}

	@Override
	public void onSuccess(Object response) {
		if (response instanceof User) {
			WebServiceManager.getInstance().joinProject(mProject, this);
		} else if (response instanceof Project) {
			Toast.makeText(this, "You have successfully joined this project!",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onFailure(Object response) {
		Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT)
				.show();
	}
}
