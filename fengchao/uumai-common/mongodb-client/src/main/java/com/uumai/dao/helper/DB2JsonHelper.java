package com.uumai.dao.helper;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.uumai.crawer.util.MongoUtil;

import java.util.ArrayList;
import java.util.List;

public class DB2JsonHelper
{

    MongoUtil mongoUtil=new MongoUtil();
    public String query(String collectionname, List<String> key, List<String> value)
    {
        StringBuffer sb = new StringBuffer();
        DB db = mongoUtil.getDB();
        DBCollection collection = db.getCollection(collectionname);
        BasicDBObject query = new BasicDBObject();
        for (int i = 0; i < key.size(); i++) {
            query.append((String)key.get(i), value.get(i));
        }
        DBCursor cursorDoc = collection.find(query);
        long count = collection.count(query);
        System.out.println("count:" + count);
        while (cursorDoc.hasNext())
        {
            sb.append(cursorDoc.next());
        }
        mongoUtil.close();
        return sb.toString();
    }
    public static void main(String[] args) throws Exception {
        List key = new ArrayList();
        key.add("ASIN");
        List value = new ArrayList();
        value.add("B00LZS5EEI");

        String json = new DB2JsonHelper().query("AmazonProduct", key, value);
        System.out.println("result:" + json);
    }
}