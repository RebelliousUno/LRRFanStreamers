package com.uno.streamers.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="games")
public class Game {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="game_id")
	private int ID;
	
	private String gameName;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Streamer streamer;
	
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="game")
	private List<Stat> stats;

	public Game(){
		stats = new ArrayList<Stat>();
	}
	
	
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public List<Stat> getStats() {
		return stats;
	}

	public void setStats(List<Stat> stats) {
				this.stats = stats;
	}


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public Streamer getStreamer() {
		return streamer;
	}


	public void setStreamer(Streamer streamer) {
		this.streamer = streamer;
	}
	
}
