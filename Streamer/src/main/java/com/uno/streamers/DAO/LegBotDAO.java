package com.uno.streamers.DAO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.uno.streams.beans.Stat;

public class LegBotDAO {

	public ArrayList<String> getGameListForUsername(String username) {
		String u = "http://legbot.ufokraana.ee/api/channel/"
				+ username.toLowerCase() + "/games";
		List<String> asList;
		String json = getJSONFromURL(u);
		if (json.startsWith("[")) {
			Gson gson = new Gson();
			String[] s = gson.fromJson(json, String[].class);
			asList = Arrays.asList(s);
			return new ArrayList<String>(asList);
		} else {
			return new ArrayList<String>();
		}
	}

	private String getJSONFromURL(String u) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		List<String> asList = null;
		String json = "";
		try {
			HttpGet request = new HttpGet(u);
			request.addHeader("content-type", "application/json");

			HttpResponse result = httpClient.execute(request);
			json = EntityUtils.toString(result.getEntity(), "UTF-8");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return json;
	}

	public List<Stat> getStatsForUserGame(String username, String game) {
		String u;
		String json = "";
		try {
			u = "http://legbot.ufokraana.ee/api/channel/"
					+ username.toLowerCase() + "/game/"
					+ URLEncoder.encode(game,"UTF-8").replaceAll("\\+","%20");
			//System.out.println(u);
			//System.out.println(game);
			json = getJSONFromURL(u);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson g = new Gson();
		if (json.startsWith("{")) {
			StatCount stats = g.fromJson(json, StatCount.class);
			if (null != stats) {
				List<Stat> ret = new ArrayList<Stat>();
				for (String statName : stats.getCounts().keySet()) {
					Stat stat = new Stat();
					stat.setStatName(statName);
					stat.setCount(stats.getCounts().get(statName));
					ret.add(stat);
				}
				return ret;
			} else {
				return new ArrayList<Stat>();
			}
		} else {
			return new ArrayList<Stat>();
		}
	}

}
