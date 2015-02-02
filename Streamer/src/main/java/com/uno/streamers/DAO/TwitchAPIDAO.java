package com.uno.streamers.DAO;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.uno.streamers.DAO.json.TwitchUser;
import com.uno.streams.beans.Streamer;

public class TwitchAPIDAO {

	public String getLogoByUsername(String username) {
		
		
		String retVal ="";
		String json = getJSONFromURL("https://api.twitch.tv/kraken/users/"
				+ username);
		
		
		Gson gson = new Gson();
		try {
			TwitchUser g = gson.fromJson(json, TwitchUser.class);
			if (null != g) {
				if (null == g.getLogo()) {
					retVal = "http://twitchdev.wpengine.com/wp-content/uploads/2013/06/Glitch_icon.png";
				} else {
					retVal = g.getLogo();
				}
			} else {
				//System.out.println("Null g from twitch: " + username);
				//System.out.println(json);
				retVal = "http://twitchdev.wpengine.com/wp-content/uploads/2013/06/Glitch_icon.png";
			}
		} catch (Exception e) {
			//System.out.println(json);
			retVal = "http://twitchdev.wpengine.com/wp-content/uploads/2013/06/Glitch_icon.png";

		}

		return retVal;
	}

	private String getJSONFromURL(String url) {

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet ipGet = new HttpGet(url);
		String json = null;
		try {
			StringEntity params = new StringEntity("");
			ipGet.addHeader("content-type", "application/json");
			HttpResponse result = httpClient.execute(ipGet);
			json = EntityUtils.toString(result.getEntity(), "UTF-8");
		} catch (Exception e) {

		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	
	public ArrayList<String> getLiveChannelsForUsernames(ArrayList<Streamer> usernames) {
		ArrayList<String> users = new ArrayList<String>();
		for (Streamer user : usernames) {
			users.add(user.getUsername());
		}
		String u="https://api.twitch.tv/kraken/streams?channel="+StringUtils.join(users.toArray(),",");
		System.out.println(u);
		String json = getJSONFromURL(u);
		ArrayList<String> liveChans = new ArrayList<String>();
		Gson gson = new Gson();
		LiveStream fromJson = gson.fromJson(json,LiveStream.class);
		for (StreamJSON sj : fromJson.getStreams()) {
			System.out.println(sj.getChannel().getName().toLowerCase());
			liveChans.add(sj.getChannel().getName().toLowerCase());
		}
		return liveChans;
	}
	
	
	
	

}
