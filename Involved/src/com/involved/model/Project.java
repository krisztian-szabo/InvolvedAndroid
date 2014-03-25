package com.involved.model;

import java.util.Date;

import com.google.gson.Gson;

public class Project {
	private int id;
	private String name;
	private String image_url;
	private double latitude;
	private double longitude;
	private String description;
	private String start_date;
	private String end_date;
	private Date start_hour;
	private Date end_hour;
	private int repeat;
	private Date created_at;
	private Date updated_at;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public Date getStart_hour() {
		return start_hour;
	}

	public void setStart_hour(Date start_hour) {
		this.start_hour = start_hour;
	}

	public Date getEnd_hour() {
		return end_hour;
	}

	public void setEnd_hour(Date end_hour) {
		this.end_hour = end_hour;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public static Project fromString(String string) {
		Gson gson = new Gson();
		return gson.fromJson(string, Project.class);
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
