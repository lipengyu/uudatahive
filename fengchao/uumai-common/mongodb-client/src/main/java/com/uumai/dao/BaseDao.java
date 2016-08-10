package com.uumai.dao;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.uumai.crawer.util.UumaiProperties;
//import org.mongodb.morphia.Datastore;
//import org.mongodb.morphia.Morphia;

@Deprecated
public class BaseDao
{
	private final MongoClient mongoClient;
	private DB db;
//	private Datastore ds;
	private final String dbname = "mydb";

//	private final Morphia morphia = new Morphia();

	protected BaseDao() {
		try {
			this.mongoClient = new MongoClient(new MongoClientURI(UumaiProperties.readconfig("MONGO_URI", "mongodb://localhost:27017")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public DB getDb()
	{
		return this.db;
	}

//	public Datastore getDs() {
//		return this.ds;
//	}

	public MongoClient getMongoClient() {
		return this.mongoClient;
	}

//	public Morphia getMorphia() {
//		return this.morphia;
//	}

	public void setDb(DB db) {
		this.db = db;
	}

//	public void setDs(Datastore ds) {
//		this.ds = ds;
//	}

	public String getDbname() {
		return "mydb";
	}
}