package com.capgemini.eventgridfs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class MongoConfig extends AbstractMongoConfiguration {

	@Value("${spring.data.mongodb.host}")
	private String host;
	
	@Value("${spring.data.mongodb.database}")
	private String database;
	
//	public MongoClient mongoClient() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	protected String getDatabaseName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Bean
	public GridFsTemplate gridFsTemplate( )throws Exception {
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
		
	}
	@Override
	public Mongo mongo() throws Exception {
		
		return new MongoClient(host);
	}
}
