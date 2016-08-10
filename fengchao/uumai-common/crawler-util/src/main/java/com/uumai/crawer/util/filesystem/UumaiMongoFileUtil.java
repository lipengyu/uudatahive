package com.uumai.crawer.util.filesystem;

import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by rock on 5/30/16.
 */
public class UumaiMongoFileUtil {
    private static final String default_dbName = "mydb";
    private MongoClient  mongoClient;
    private DB db;
    private GridFS myFS;

    public void connect(String uri)  throws  Exception{
        mongoClient = new MongoClient(new MongoClientURI(uri));
        db = mongoClient.getDB(default_dbName);
        myFS = new GridFS(db);
    }
    public void close(){
        mongoClient.close();
    }

    public void save2mongodb(String uri, String filename,String html) {
        try {
            connect(uri);
            if(getByFileName(filename)!=null){
                deleteByName(filename);
            }
            save(html,filename);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
    public String readfrommongodb(String uri, String filename) {
        try {
            connect(uri);
            String html=read(filename);
             return html;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }
    public  void delete(String uri, String fileName){
        try {
             connect(uri);
             this.deleteByName(fileName);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }


    }
    public boolean exist(String uri, String filename) {
        try {
            connect(uri);
            GridFSDBFile gridFSDBFile= this.getByFileName(filename);
             if(gridFSDBFile==null)
                 return false;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return true;
    }
        private String read( String filename) throws  Exception{
        GridFSDBFile gridFSDBFile=getByFileName(filename);
        if(gridFSDBFile!=null){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            gridFSDBFile.writeTo(out);
            byte[] toReturn = out.toByteArray();
            out.close();
            return out.toString();
        }
        return null;
    }
    private void save(String html, String filename){
        InputStream streamToUploadFrom = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));

        GridFSInputFile gridFSInputFile = myFS.createFile(streamToUploadFrom,filename);
        gridFSInputFile.save();
    }
    public  void deleteById(Object id){
        BasicDBObject  query  = new BasicDBObject("_id", id);
        myFS.remove(query);
    }

    private  void deleteByName( String fileName){

          DBObject query  = new BasicDBObject("filename", fileName);
           myFS.remove(query);

    }
    private GridFSDBFile getById(Object id){
        DBObject query  = new BasicDBObject("_id", id);
        GridFSDBFile gridFSDBFile = myFS.findOne(query);
        return gridFSDBFile;
    }
    private GridFSDBFile getByFileName(String fileName){
        DBObject query  = new BasicDBObject("filename", fileName);
        GridFSDBFile gridFSDBFile = myFS.findOne(query);
        return gridFSDBFile;
    }

    public List<GridFSDBFile> findByFileName(String fileNameQuery){
        DBObject query  = new BasicDBObject();
        Pattern regex = Pattern.compile("^"+fileNameQuery); // should be m in your case
        query.put("filename", regex);
         return  myFS.find(query);
    }

    private DBCursor findDBCursorByFileName(String fileNameQuery){
        DBObject query  = new BasicDBObject();
        Pattern regex = Pattern.compile("^"+fileNameQuery); // should be m in your case
        query.put("filename", regex);
        return  myFS.getFileList(query);
    }
}

