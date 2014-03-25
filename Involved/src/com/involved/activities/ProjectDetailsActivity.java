package com.involved.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.involved.R;
import com.involved.model.Project;
import com.involved.utils.InvolvedUtils;
import com.involved.views.InvolvedTextView;
import com.loopj.android.image.SmartImageView;

public class ProjectDetailsActivity extends ActionBarActivity {
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

		// Inflate the menu; this adds items to the action bar if it is present.
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
			break;
		}
		return super.onOptionsItemSelected(item);
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
}
