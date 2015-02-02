package com.uno.streamers.config;

import org.springframework.beans.factory.annotation.Value;



public class DBProps {

	@Value("${show_sql}")
	private String show_sql;
	@Value("${dialect}")
	private String dialect;
	@Value("${hbm2ddl}")
	private String hbm2ddl;
	@Value("${sqldriver}")
	private String sqldriver;
	@Value("${dburl}")
	private String dburl;
	@Value("${dbuser}")
	private String dbuser;
	@Value("${dbpass}")
	private String dbpass;
	
	
	public DBProps() {
		
	}
	
	public String getShow_sql() {
		return show_sql;
	}
	public String getDialect() {
		return dialect;
	}
	public String getHbm2ddl() {
		return hbm2ddl;
	}
	public String getSqldriver() {
		return sqldriver;
	}
	public String getDburl() {
		return dburl;
	}
	public String getDbuser() {
		return dbuser;
	}
	public String getDbpass() {
		return dbpass;
	}

}
