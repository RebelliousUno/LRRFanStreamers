package com.uno.streamers;

import java.util.Locale;
import java.util.Random;

import javax.persistence.FetchType;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.uno.streamers.DAO.GeoIPDAO;
import com.uno.streamers.DAO.GoogleCalendarDAO;
import com.uno.streamers.DAO.StreamerDAO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	StreamerDAO dao;
	
	@Autowired
	GeoIPDAO geoIP;
	
	@Autowired
	GoogleCalendarDAO calDAO;

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Locale locale, Model model) {
		model.addAttribute("streamers", dao.getStreamers());
		
		String IP = request.getRemoteAddr();

		String timeZone = geoIP.getTimezoneFromIP(IP);
		String calLink = "https://www.google.com/calendar/embed?mode=WEEK&height=600&wkst=2&bgcolor=%23FFFFFF&src=caffeinatedlemur%40gmail.com&color=%23AB8B00&ctz="+timeZone;
		model.addAttribute("calLink", calLink);
		Random r = new Random();
		String logoLink = String.format("logo_top_%02d.png",r.nextInt(12)+1);
		model.addAttribute("logoLink",logoLink);
		return "index";
	}

	@RequestMapping(value = "/{streamer}", method = RequestMethod.GET)
	public ModelAndView streamer(@PathVariable String streamer, Model model) {

		ModelAndView mav = new ModelAndView();
		mav.addAllObjects(model.asMap());
		mav.setViewName("streamer");
		mav.addObject("statlist", dao.getStatlistForStreamer(streamer));
		mav.addObject("streamer", dao.getStreamerByUserName(streamer,FetchType.EAGER));
	
		return mav;
	}

}
