package com.uno.streamers.DAO;

import java.util.List;

public class LiveStream {

	private List<StreamJSON> streams;
	private int total;
	private Object links_;
	public List<StreamJSON> getStreams() {
		return streams;
	}
	public void setStreams(List<StreamJSON> streams) {
		this.streams = streams;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Object getLinks_() {
		return links_;
	}
	public void setLinks_(Object links_) {
		this.links_ = links_;
	}
	
}
