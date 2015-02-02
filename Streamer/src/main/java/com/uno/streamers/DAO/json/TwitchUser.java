package com.uno.streamers.DAO.json;

public class TwitchUser {

	private String display_name;
	private int _id;
	private String name;
	private String type;
	private String bio;
	private String created_at;
	private String updated_at;
	private String logo;
	private TwitchLinks _links;
	
	
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getLogo() {

		return logo.replaceAll("300x300", "150x150");
	}
	public void setLogo(String logo) {
		this.logo = logo;
		
	}
	public TwitchLinks get_links() {
		return  _links;
	}
	public void set_links(TwitchLinks _links) {
		this._links = _links;
	}

}
