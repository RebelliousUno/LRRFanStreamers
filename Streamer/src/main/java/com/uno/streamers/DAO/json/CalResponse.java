package com.uno.streamers.DAO.json;

import java.util.List;

public class CalResponse {
	private String kind;
	private String etag;
	private String summary;
	private String description;
	private String updated;
	private String timeZone;
	private String accessRole;
	private List<String> defaultReminders;
	private String nextPageToken;
	private List<CalEvent> items;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getAccessRole() {
		return accessRole;
	}

	public void setAccessRole(String accessRole) {
		this.accessRole = accessRole;
	}

	public List<String> getDefaultReminders() {
		return defaultReminders;
	}

	public void setDefaultReminders(List<String> defaultReminders) {
		this.defaultReminders = defaultReminders;
	}

	public String getNextPageToken() {
		return nextPageToken;
	}

	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}

	public List<CalEvent> getItems() {
		return items;
	}

	public void setItems(List<CalEvent> items) {
		this.items = items;
	}
}
