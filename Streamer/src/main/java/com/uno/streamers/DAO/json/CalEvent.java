package com.uno.streamers.DAO.json;

public class CalEvent {

	private String kind;
	private String etag;
	private String id;
	private String status;
	private String htmlLink;
	private String created;
	private String updated;
	private String summary;
	private String location;
	private CalPerson creator;
	private CalPerson organizer;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHtmlLink() {
		return htmlLink;
	}
	public void setHtmlLink(String htmlLink) {
		this.htmlLink = htmlLink;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public CalPerson getCreator() {
		return creator;
	}
	public void setCreator(CalPerson creator) {
		this.creator = creator;
	}
	public CalPerson getOrganizer() {
		return organizer;
	}
	public void setOrganizer(CalPerson organizer) {
		this.organizer = organizer;
	}
	public CalTime getStart() {
		return start;
	}
	public void setStart(CalTime start) {
		this.start = start;
	}
	public CalTime getEnd() {
		return end;
	}
	public void setEnd(CalTime end) {
		this.end = end;
	}
	public String getiCalUID() {
		return iCalUID;
	}
	public void setiCalUID(String iCalUID) {
		this.iCalUID = iCalUID;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	private CalTime start;
	private CalTime end;
	private String iCalUID;
	private int sequence;
	
	
}
