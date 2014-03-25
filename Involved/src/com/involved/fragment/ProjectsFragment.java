package com.involved.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.involved.R;
import com.involved.activities.ProjectDetailsActivity;
import com.involved.adapters.ProjectAdapter;
import com.involved.interfaces.WebServiceInterface;
import com.involved.model.Project;
import com.involved.network.WebServiceManager;

public class ProjectsFragment extends Fragment implements WebServiceInterface,
		OnItemClickListener {
	private ListView mListView;
	private ProjectAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_default_list, null);
		mListView = (ListView) view.findViewById(R.id.news_list);

		WebServiceManager.getInstance().getProjects(this);

		return view;
	}

	@Override
	public void onSuccess(Object response) {
		mAdapter = new ProjectAdapter(getActivity(), (List<Project>) response);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onFailure(Object response) {
		Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Project project = (Project) mAdapter.getItem(position);
		Intent intent = new Intent(getActivity(), ProjectDetailsActivity.class);
		intent.putExtra("project", project.toString());
		startActivity(intent);
	}

}
