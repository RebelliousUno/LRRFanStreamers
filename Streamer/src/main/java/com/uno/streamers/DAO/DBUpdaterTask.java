package com.uno.streamers.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.uno.streamers.DAO.json.TwitchUser;
import com.uno.streamers.beans.Game;
import com.uno.streamers.beans.Stat;
import com.uno.streamers.beans.Streamer;

public class DBUpdaterTask {

	@Autowired
	private GoogleCalendarDAO calDAO;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TwitchAPIDAO twitchDAO;

	@Autowired
	private LegBotDAO legBotDAO;
	
	@Autowired
	private StreamerDAO streamerDAO;

	public ArrayList<String> getUsernamesFromCal() {
		return calDAO.getStreamersFromCal();
	}

	private void updateStreamers(ArrayList<String> usernames) {
		for (String user : usernames) {
			updateStreamer(user);
			//updateStreamer2(user);
		}
	}

	private void updateStreamer2(String user) {
		Session sesh = sessionFactory.openSession();
		Query q = sesh
				.createQuery("from Streamer s where s.username=:username");
		q.setParameter("username", user);
		Streamer st = (Streamer) q.uniqueResult();
		if (null!=st) {
			st.setGames(getGameListForStreamer2(st));
		}
		
		
	}

	private ArrayList<Game> getGameListForStreamer2(Streamer st) {
		//legBotDAO.getGameListForUsername2(st.getUsername());
		
		return null;
	}

	@Transactional
	private void updateStreamer(String user) {
		Session sesh = sessionFactory.openSession();
		Query q = sesh
				.createQuery("from Streamer s where s.username=:username");
		q.setParameter("username", user);
		Streamer st = (Streamer) q.uniqueResult();
		if (null != st) {
			// Streamer exists so update games for streamer			
			st.setGames(getGameListForStreamer(st));
			sesh.saveOrUpdate(st);
		} else {
			// Streamer doesn't exist so new streamer
			TwitchUser tu = twitchDAO.getTwitchUser(user);
			Streamer s = new Streamer();
			s.setUsername(user);
			if (null!=tu) {
				s.setBio(tu.getBio());
				s.setLogo(tu.getLogo());				
			} else {
				s.setBio("Lorem Ipsum");
				s.setLogo("http://twitchdev.wpengine.com/wp-content/uploads/2013/06/Glitch_icon.png");
			}
			
			sesh.saveOrUpdate(s);
		}
		sesh.close();
	}

	private ArrayList<Game> getGameListForStreamer(Streamer st) {
		ArrayList<String> gameList = legBotDAO.getGameListForUsername(st
				.getUsername());
		ArrayList<Game> gameAsGameList = new ArrayList<Game>();
		for (String game : gameList) {
			gameAsGameList.add(getGameForStreamerByGame(st, game));
		}
		return gameAsGameList;
	}

	@Transactional
	private Game getGameForStreamerByGame(Streamer st, String gamename) {
		Session sesh = sessionFactory.openSession();
		Query q = sesh.createQuery("from Game g where g.gameName=:gamename and g.streamer = :streamer");
		q.setParameter("gamename", gamename);
		q.setParameter("streamer", st);
		Game g = (Game) q.uniqueResult();
		if (null!=g) {
			//Game Exists for user
			if (null==g.getStreamer()) {
				g.setStreamer(st);
			}
			g.setStats(getStatsForGameStreamer(st,g));//Get stats for game
			
			sesh.saveOrUpdate(g);
			sesh.close();
			return g;
		} else {
			//Game Does not exist for user
			Game newGame = new Game();
			newGame.setGameName(gamename);
			newGame.setStreamer(st);
			sesh.saveOrUpdate(newGame);
			sesh.close();
			return newGame;
		}
	
	}

	private List<Stat> getStatsForGameStreamer(Streamer st, Game g) {
		// TODO Auto-generated method stub
		List<Stat> statsForUserGame = legBotDAO.getStatsForUserGame(
				st.getUsername(), g.getGameName());
		List<Stat> statReturn = new ArrayList<Stat>();
		for (Stat stat : statsForUserGame) {
			statReturn.add(getStatForGameStreamerStatName(st, g, stat));
		}
		return statReturn;

	}

	@Transactional
	private Stat getStatForGameStreamerStatName(Streamer st, Game g, Stat stat) {

		Session sesh = sessionFactory.openSession();
		Query q = sesh
				.createQuery("from Stat s where s.game = :gamename and s.statName = :statname");
		q.setParameter("gamename", g);
		q.setParameter("statname", stat.getStatName());
		Stat dbStat = (Stat) q.uniqueResult();
		if (null!=dbStat) {
			//Found Stat update count
			dbStat.setCount(stat.getCount());
			dbStat.setGame(g);
			sesh.saveOrUpdate(dbStat);
			sesh.flush();
			sesh.close();
			return dbStat;
		} else {
			Stat newStat = new Stat();
			newStat.setStatName(stat.getStatName());
			newStat.setCount(stat.getCount());
			newStat.setGame(g);
			sesh.saveOrUpdate(newStat);
			sesh.close();
			return newStat;
		}
	}

	@Scheduled(fixedRate = 30000)
	public void updateDBWithCal() {
		ArrayList<String> calStrings = getUsernamesFromCal();
		updateStreamers(calStrings);

	}
	
	@Transactional
	@Scheduled(fixedRate = 30000,initialDelay=30000)
	public void updateLiveStreamers() {
		ArrayList<Streamer> usernames = streamerDAO.getStreamers();
		ArrayList<String> liveChannels = twitchDAO.getLiveChannelsForUsernames(usernames);
		Session sesh = sessionFactory.openSession();
		Query q = sesh.createQuery("From Streamer");
		List<?> streamers = q.list();
		for (Object s : streamers) {
			Streamer st = (Streamer) s;
			if (liveChannels.contains(st.getUsername())) {
				st.setLive(true);
			}
			else {
				st.setLive(false);
			}
			sesh.saveOrUpdate(st);
		}
		sesh.flush();
		sesh.close();
	}
	
	

}
