package com.uumai.crawer.util;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoUtil
{
	private static final int port = 27017;
	private static final String host = "localhost";
	private static final String default_dbName = "mydb";
//	private  Mongo mongo = null;
//	private  DB db = null;

//	private  Morphia morphia = null;
	private  MongoClient mongoClient = null;
//	private  Datastore ds = null;

//	public static Mongo getMongo()
//	{
//		if (mongo == null) {
//			try {
//				mongo = new Mongo("localhost", 27017);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return mongo;
//	}
	public  DB getDB() {
		return  getMongoClient().getDB(default_dbName);
	}
    public  DB getDB(String dbName) {
        return  getMongoClient().getDB(dbName);
    }
	private  MongoClient getMongoClient() {
		if (mongoClient == null) {
			try {
				//mongoClient = new MongoClient(new MongoClientURI(System.getProperty("uumai.mongodb.MONGO_URI", "mongodb://localhost:27017")));
                mongoClient = new MongoClient(new MongoClientURI(UumaiProperties.readconfig("uumai.mongodb.MONGO_URI", "mongodb://localhost:27017")));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mongoClient;
	}

//	public  Morphia getMorphia() {
//		if (morphia == null) {
//			morphia = new Morphia();
////			morphia.map(new Class[] { AmazonProduct.class }).map(new Class[] { AmazonCategory.class });
////            morphia.map(new Class[] { KeepaHistory.class });
////            morphia.map(new Class[] { JdProduct.class });
//		}
//		return morphia;
//	}
//	public  Datastore getDs() {
//		if (ds == null) {
//			ds = getMorphia().createDatastore(getMongoClient(), "mydb");
//		}
//		return ds;
//	}



    public  void close(){
        if(mongoClient!=null){
            mongoClient.close();
            mongoClient=null;
        }
     }

//	public static String getDbName() {
//		return "mydb";
//	}
}