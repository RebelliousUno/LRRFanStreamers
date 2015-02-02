package com.uno.streamers.DAO;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.api.services.calendar.Calendar;
import com.google.gson.Gson;
import com.uno.streamers.DAO.json.CalEvent;
import com.uno.streamers.DAO.json.CalResponse;

public class GoogleCalendarDAO {

	private String apiKey = "AIzaSyDS040rReDciFvegcNEeg_j2nF0tqJocrY";
	private String emailAddress = "659171672799-vc9q3cppuut09a3bmp2nbfaajbbgb7fe@developer.gserviceaccount.com";
	private String clientID = "659171672799-vc9q3cppuut09a3bmp2nbfaajbbgb7fe.apps.googleusercontent.com";
	private String appName = "LRRFanStream";
	private Calendar service;

	// https://www.googleapis.com/calendar/v3/calendars/caffeinatedlemur%40gmail.com/events?key={YOUR_API_KEY}
	// notasecret

	public void GoogleCalendarDAO() {
		try {
			setUp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setUp() throws IOException, GeneralSecurityException {

	}

	public ArrayList<String> getStreamersFromCal() {
		Set<String> userNames = new HashSet<String>();
		try {
			String email = "caffeinatedlemur@gmail.com";
			String u = "https://www.googleapis.com/calendar/v3/calendars/"
					+ URLEncoder.encode(email, "UTF-8") + "/events?";

			String key = "key=" + apiKey;
			String timeMinParam = "timeMin=";
			String timeMaxParam = "timeMax=";
			String singleEventsParam = "singleEvents=";

			java.util.Calendar localCal = java.util.Calendar.getInstance();
			
			int dayOfWeek = localCal.get(java.util.Calendar.DAY_OF_WEEK);
			
			localCal.add(java.util.Calendar.DAY_OF_MONTH, -dayOfWeek+2);
			
			int year = localCal.get(java.util.Calendar.YEAR);
			int month = localCal.get(java.util.Calendar.MONTH)+1;
			int dayOfMonth = localCal.get(java.util.Calendar.DAY_OF_MONTH);

			String timeMin = String.format("%04d-%02d-%02dT00:00:00+00:00",year,month,dayOfMonth);
			
			localCal.add(java.util.Calendar.DAY_OF_MONTH,7);
		
			int futureYear = localCal.get(java.util.Calendar.YEAR);
			int futureMonth = localCal.get(java.util.Calendar.MONTH)+1;
			int futureDayOfMonth = localCal.get(java.util.Calendar.DAY_OF_MONTH);

			String timeMax = String.format("%04d-%02d-%02dT00:00:00+00:00",futureYear,futureMonth,futureDayOfMonth);
					
			String singleEvents = "true";

			String concatURL = u + singleEventsParam + singleEvents + "&"
					+ timeMinParam + URLEncoder.encode(timeMin, "UTF-8") + "&"
					+ timeMaxParam + URLEncoder.encode(timeMax, "UTF-8") + "&"
					+ key;

//			System.out.println(concatURL);
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(concatURL.toString());
			request.addHeader("content-type", "application/json");
			HttpResponse result = httpClient.execute(request);
			String json = EntityUtils.toString(result.getEntity(), "UTF-8");

			Gson gson = new Gson();
			CalResponse e = gson.fromJson(json, CalResponse.class);

			for (CalEvent ce : e.getItems()) {
				String twitchLink = ce.getLocation();
				if (null != twitchLink) {
					Pattern p = Pattern.compile(".*\\.tv/(.*)");
					Matcher m = p.matcher(twitchLink);
					if (m.matches()) {
						int slash = m.group(1).lastIndexOf('/');
						String username;
						if (slash > 0) {
							username = m.group(1).substring(0, slash)
									.toLowerCase();
						} else {
							username = m.group(1).trim().toLowerCase();
						}
						userNames.add(username);
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("getStreamersFromCal Exception Caught");
			e.printStackTrace();
		}

		return new ArrayList<String>(userNames);

	}
}
