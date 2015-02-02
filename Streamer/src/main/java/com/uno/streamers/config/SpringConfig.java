package com.uno.streamers.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.uno.streamers.DAO.DBUpdaterTask;
import com.uno.streamers.DAO.GeoIPDAO;
import com.uno.streamers.DAO.GoogleCalendarDAO;
import com.uno.streamers.DAO.LegBotDAO;
import com.uno.streamers.DAO.StreamerDAO;
import com.uno.streamers.DAO.TwitchAPIDAO;
import com.uno.streams.beans.Game;
import com.uno.streams.beans.Stat;
import com.uno.streams.beans.Streamer;

@Configuration
@ComponentScan("com.uno.streamers")
@EnableWebMvc
@EnableTransactionManagement
@EnableScheduling
@PropertySource("classpath:hibernate.properties")
public class SpringConfig extends WebMvcConfigurerAdapter {

	/*
	 * @Bean public UrlBasedViewResolver setupViewResolver(){
	 * UrlBasedViewResolver resolver = new UrlBasedViewResolver();
	 * resolver.setPrefix("/WEB-INF/views/"); resolver.setSuffix(".jsp");
	 * resolver.setViewClass(JstlView.class); return resolver; }
	 */

	
	@Bean
	public DBProps dbProps() {
		return new DBProps();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	
	
	@Bean
	public DBUpdaterTask getUpdaterTask(){	
		DBUpdaterTask task = new DBUpdaterTask();
		
		return task;
	}
	
	
	
	@Autowired
	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource,DBProps dbprops) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setHibernateProperties(getHibernateProperties(dbprops));
		sessionFactory.setAnnotatedClasses(new Class[] {Streamer.class,Game.class, Stat.class});

		
		return sessionFactory;
		

	}

	@Autowired
	@Bean
	public HibernateTransactionManager getTransactionManager(
	        SessionFactory sessionFactory) {
	    HibernateTransactionManager transactionManager = new HibernateTransactionManager(
	            sessionFactory);
	 
	    return transactionManager;
	}
	
	
	private Properties getHibernateProperties(DBProps dbprops) {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", dbprops.getShow_sql());
		properties.put("hibernate.dialect",
				dbprops.getDialect());
		properties.put("hibernate.hbm2ddl.auto", dbprops.getHbm2ddl());
		return properties;
	}

	@Bean
	@Autowired
	public DataSource getDataSource(DBProps dbprops) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(dbprops.getSqldriver()); // TODO:
																// Properties
																// File
		dataSource.setUrl(dbprops.getDburl());
		dataSource.setUsername(dbprops.getDbuser());
		dataSource.setPassword(dbprops.getDbpass());
		return dataSource;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/");

	}

	@Bean
	public TwitchAPIDAO getTwitchAPIDAO() {
		return new TwitchAPIDAO();
	}

	@Bean
	@Autowired
	public StreamerDAO getStreamerDAO(SessionFactory sessionFactory) {
		return new StreamerDAO(sessionFactory);
	}

	@Bean
	public Stat getStat(){
		return new Stat();
	}
	
	
	@Bean
	public LegBotDAO getLegBotDAO() {
		return new LegBotDAO();
	}

	@Bean
	public String twitchLink() {
		return "http://www.twitch.tv/SnorshTV";
	}

	@Bean
	public String helloWorld() {
		return "SBN: Snorsh Broadcasting Network";
	}

	@Bean
	@Autowired
	public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
		ThymeleafViewResolver tvr = new ThymeleafViewResolver();
		tvr.setTemplateEngine(templateEngine);
		tvr.setOrder(1);
		return tvr;

	}

	@Bean
	public GeoIPDAO getGeoIP() {
		return new GeoIPDAO();
	}

	@Bean
	public GoogleCalendarDAO getCalDAO() {
		GoogleCalendarDAO gc = new GoogleCalendarDAO();
		return gc;
	}

	@Bean
	public ServletContextTemplateResolver setupeContextTemplateResolver() {

		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix("/WEB-INF/templates/");
		resolver.setSuffix(".html");
		return resolver;

	}

	@Bean
	@Autowired
	public SpringTemplateEngine setupTemplateEngine(
			ServletContextTemplateResolver templateResolver) {
		SpringTemplateEngine ste = new SpringTemplateEngine();
		ste.addTemplateResolver(templateResolver);
		return ste;
	}
}
