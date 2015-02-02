package com.uno.streamers.DAO;

public class StreamJSON {

	private long _id;
	private String game;
	private int viewers;
	private Object _links;
	private Object preview;
	private ChannelJSON channel;
	public long get_id() {
		return _id;
	}
	public void set_id(long _id) {
		this._id = _id;
	}
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
	public int getViewers() {
		return viewers;
	}
	public void setViewers(int viewers) {
		this.viewers = viewers;
	}
	public Object get_links() {
		return _links;
	}
	public void set_links(Object _links) {
		this._links = _links;
	}
	public Object getPreview() {
		return preview;
	}
	public void setPreview(Object preview) {
		this.preview = preview;
	}
	public ChannelJSON getChannel() {
		return channel;
	}
	public void setChannel(ChannelJSON channel) {
		this.channel = channel;
	}
	
}
