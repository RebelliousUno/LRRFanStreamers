package com.uno.streamers.DAO;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.uno.streamers.beans.GeoIPJSON;

public class GeoIPDAO {

	public String getTimezoneFromIP(String IP) {		
		if (IP.equals("0:0:0:0:0:0:0:1")|| IP.startsWith("192.168")){
			IP = "145.255.241.206";
		}
		String getIP = "http://ip-api.com/json/" + IP;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet ipGet = new HttpGet(getIP);
		String json = null;
		try {
			StringEntity params = new StringEntity("");
			ipGet.addHeader("content-type", "application/json");
			CloseableHttpResponse result = httpClient.execute(ipGet);
			json = EntityUtils.toString(result.getEntity(), "UTF-8");
		} catch (Exception e) {
	
		}
		Gson gson = new Gson();
		
		GeoIPJSON g = gson.fromJson(json, GeoIPJSON.class);
		return g.getTimezone();
	}

}
