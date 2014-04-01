package com.involved.utils;

import static com.involved.utils.InvolvedConstants.REPEAT_DAILY;
import static com.involved.utils.InvolvedConstants.REPEAT_MONTHLY;
import static com.involved.utils.InvolvedConstants.REPEAT_NONE;
import static com.involved.utils.InvolvedConstants.REPEAT_WEEKLY;
import static com.involved.utils.InvolvedConstants.REPEAT_YEARLY;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.involved.model.Project;

public class InvolvedUtils {
	public static String getNextAppointmentForProject(Project project) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
					Locale.getDefault());
			Date startDate = sdf.parse(project.getStart_date());
			Date currentDate = new Date(System.currentTimeMillis());
			SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE",
					Locale.getDefault());
			SimpleDateFormat hourFormat = new SimpleDateFormat("hh aa",
					Locale.getDefault());
			switch (project.getRepeat()) {
			case REPEAT_NONE:
				return dayFormat.format(startDate) + ", "
						+ hourFormat.format(project.getStart_hour()) + "-"
						+ hourFormat.format(project.getEnd_hour());
			case REPEAT_DAILY:
				return (startDate.after(currentDate)) ? "Today at "
						+ hourFormat.format(project.getStart_hour()) + "-"
						+ hourFormat.format(project.getEnd_hour())
						: "Tomorrow at "
								+ hourFormat.format(project.getStart_hour())
								+ "-"
								+ hourFormat.format(project.getEnd_hour());
			case REPEAT_WEEKLY:
				return dayFormat.format(startDate) + ", "
						+ hourFormat.format(project.getStart_hour()) + "-"
						+ hourFormat.format(project.getEnd_hour());
			case REPEAT_MONTHLY:
				return dayFormat.format(startDate) + ", "
						+ hourFormat.format(project.getStart_hour()) + "-"
						+ hourFormat.format(project.getEnd_hour());
			case REPEAT_YEARLY:
				return dayFormat.format(startDate) + ", "
						+ hourFormat.format(project.getStart_hour()) + "-"
						+ hourFormat.format(project.getEnd_hour());
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-";
	}

	public static String convertInputStreamToString(InputStream inputStream) {
		BufferedReader r = new BufferedReader(
				new InputStreamReader(inputStream));
		StringBuilder total = new StringBuilder();
		String line;
		try {
			while ((line = r.readLine()) != null) {
				total.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total.toString();
	}
}
