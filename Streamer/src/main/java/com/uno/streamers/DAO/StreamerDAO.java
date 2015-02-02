package com.uno.streamers.DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.persistence.FetchType;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;

import com.uno.streamers.beans.Game;
import com.uno.streamers.beans.Stat;
import com.uno.streamers.beans.Streamer;

public class StreamerDAO {

	@Autowired
	private LegBotDAO leg_botDAO;

	@Autowired
	private TwitchAPIDAO twitchDAO;

	@Autowired
	private GoogleCalendarDAO calDAO;

	@Autowired
	private SessionFactory sessionFactory;

	public StreamerDAO() {

	}

	public StreamerDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public ArrayList<Streamer> getStreamers() {
		Session sesh = sessionFactory.openSession();
		List<Streamer> list = (List<Streamer>) sesh.createCriteria(
				Streamer.class).list();

		sesh.close();
		Collections.sort(list);
		return new ArrayList<Streamer>(list);

		// First From DB
		// Next From Cal

		/*
		 * HashSet<Streamer> streamers = new HashSet<Streamer>();
		 * streamers.addAll(list); for (String username : streamers) {
		 * 
		 * Streamer s = new Streamer(); s.setUsername(username); if
		 * (!streamers.contains(s)) {
		 * s.setLogo(twitchDAO.getLogoByUsername(username)); }
		 * s.setLive(s.getUsername().equalsIgnoreCase("rebelliousuno"));
		 * sesh.saveOrUpdate(s); streamers.add(s); }
		 */

	}

	@Transactional
	public Streamer getStreamerByUserName(String streamer, FetchType loading) {
		Session sesh = sessionFactory.openSession();
		Query q = sesh
				.createQuery("From Streamer s where s.username = :username");
		q.setParameter("username", streamer);
		Streamer st = (Streamer) q.uniqueResult();
		if (loading == FetchType.EAGER) {
			for (Game g : st.getGames()) {
				Hibernate.initialize(g.getStats());
			}

		}
		// st.getGames().size();
		sesh.close();
		return st;

	}

	@Transactional
	public List<String> getStatlistForStreamer(String streamer) {
		Streamer st = getStreamerByUserName(streamer, FetchType.EAGER);
		List<String> statlist = new ArrayList<String>();
		for (Stat s : st.getGames().get(0).getStats()) {
			statlist.add(s.getStatName());
		}
		return statlist;
	}

}
