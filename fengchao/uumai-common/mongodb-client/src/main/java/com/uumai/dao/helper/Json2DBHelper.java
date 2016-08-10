package com.uumai.dao.helper;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;
import com.uumai.crawer.util.MongoUtil;
import java.io.IOException;

public class Json2DBHelper
{
    MongoUtil mongoUtil=new MongoUtil();

//    public void store(String json, String collectionname) throws IOException {
//        try {
//
////			Mongo mongo = new Mongo("localhost", 27017);
////			DB db = mongo.getDB("mydb");
//            DB db = MongoUtil.getDb();
//            DBCollection collection = db.getCollection(collectionname);
//            // InputStream is = ConvertXMLtoJSON.class
//            // .getResourceAsStream("sample.xml");
//            // String xml = IOUtils.toString(is);
//            // System.out.println(xml);
//            // XMLSerializer xmlSerializer = new XMLSerializer();
//            // JSON json = xmlSerializer.read(xml);
//
//            // convert JSON to DBObject directly
//            DBObject object = (DBObject) com.mongodb.util.JSON.parse(json);
//            collection.insert(object);
//            // DBCursor cursorDoc = collection.find();
//            // while (cursorDoc.hasNext()) {
//            // System.out.println(cursorDoc.next());
//            // }
//            System.out.println("Done");
//        } catch (MongoException e) {
//            e.printStackTrace();
//        }
//    }
//
    public void store(String json, String collectionname)
            throws IOException
    {
        try
        {
            DB db = mongoUtil.getDB();
            DBCollection collection = db.getCollection(collectionname);

            DBObject object = (DBObject)JSON.parse(json);
            collection.insert(new DBObject[] { object });

            //System.out.println("Done");
            mongoUtil.close();
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Json2DBHelper helper = new Json2DBHelper();
        helper.store("{A:111}", "c1");
    }
}