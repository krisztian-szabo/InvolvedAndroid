package com.involved.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.involved.R;
import com.involved.interfaces.WebServiceInterface;
import com.involved.model.Project;
import com.involved.network.WebServiceManager;

public class ProjectsFragment extends Fragment implements WebServiceInterface {
	private ListView mListView;

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
		List<Project> projects = (List<Project>) response;
		String[] projectNames = new String[projects.size()];
		for (Project project : projects) {
			projectNames[projects.indexOf(project)] = project.getName();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, projectNames);
		mListView.setAdapter(adapter);
	}

	@Override
	public void onFailure(Object response) {
		// TODO Auto-generated method stub

	}

}
