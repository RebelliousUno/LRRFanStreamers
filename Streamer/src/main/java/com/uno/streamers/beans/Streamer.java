package com.uno.streamers.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="streamers")
public class Streamer implements Comparable<Streamer>{

	
	public Streamer(){
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="streamer_id")
	private int id;
	
	@Column(name="live")
	private boolean live;
	
	@Column(name="bio", length=10000)
	private String bio;
	
	@Column(name="username", unique=true, nullable=false)
	private String username;
	
	@Column(name="youtube")
	private String youtube;
	
	@Column(name="twitter")
	private String twitter;
	
	@Column(name="logo")
	private String logo;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="streamer")
	private List<Game> games;	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public List<Game> getGames() {
		return games;
	}
	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getYoutube() {
		return youtube;
	}
	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	@Override
	public int compareTo(Streamer o) {
		
		if (isLive()==o.isLive()){
		return getUsername().compareTo(o.getUsername());
		} else {
			if (isLive() && !o.isLive()){
				return -1;
			} else {
				return 1;
			}
		}
	}
	
	public boolean equals(Streamer s){
		return getUsername().equals(s.getUsername());
		
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	
	
	
}
