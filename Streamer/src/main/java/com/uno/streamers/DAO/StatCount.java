package com.uno.streamers.DAO;

import java.util.Map;

public class StatCount {

	private String name;
	private String game;
	private String[] statistics;
	private Map<String,Integer> counts;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
	public String[] getStatistics() {
		return statistics;
	}
	public void setStatistics(String[] statistics) {
		this.statistics = statistics;
	}
	public Map<String, Integer> getCounts() {
		return counts;
	}
	public void setCounts(Map<String, Integer> counts) {
		this.counts = counts;
	}
	
	
}
