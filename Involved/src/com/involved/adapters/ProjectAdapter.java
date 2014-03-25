package com.involved.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.involved.R;
import com.involved.model.Project;
import com.loopj.android.image.SmartImageView;

public class ProjectAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private List<Project> mItems;

	public ProjectAdapter(Context context, List<Project> projects) {
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mItems = projects;
	}

	@Override
	public int getCount() {
		return this.mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return this.mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = this.mLayoutInflater.inflate(R.layout.row_project,
					parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView
					.findViewById(R.id.row_project_name);
			holder.image = (SmartImageView) convertView
					.findViewById(R.id.row_project_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Project project = this.mItems.get(position);
		holder.title.setText(project.getName());
		holder.image.setImageUrl(project.getImage_url());
		return convertView;
	}

	private class ViewHolder {
		TextView title;
		SmartImageView image;
	}

}
