package com.uumai.crawer.quartz.localdebug;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.uumai.crawer.quartz.JsonParseHelper;
import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.result.QuartzXpathItem;
import com.uumai.crawer.util.filesystem.ExcelFileUtil;
import com.uumai.crawer2.CrawlerTasker;
import com.uumai.crawer2.localdebug.LocalDebugCrawlerWorker;
import com.uumai.dao.helper.Json2DBHelper;

public class QuartzLocalDebugCrawlerWorker extends LocalDebugCrawlerWorker{
	
	public QuartzLocalDebugCrawlerWorker(CrawlerTasker tasker) {
		super(tasker);
 	}

	@Override
    protected void download() throws Exception {
		super.download();
	}
	
	@Override
    protected void pipeline() throws Exception {
 
			QuartzCrawlerTasker quartztasker = (QuartzCrawlerTasker) tasker;
			
			JsonParseHelper jsonParseHelper=new JsonParseHelper(quartztasker,result);

 			List<JsonObject> list=  jsonParseHelper.parse();
 			
 			if(quartztasker.getStoreTableName()!=null){
                if(quartztasker.getStoreTableName().endsWith(".xls")){
                    ExcelFileUtil util=new ExcelFileUtil(quartztasker.getStoreTableName());
                    for(JsonObject obj:list){
                        List<String> columnvalues=new ArrayList<String>();
                        Iterator i$ = obj.entrySet().iterator();
                        while(i$.hasNext()) {
                            Map.Entry entry = (Map.Entry)i$.next();
                            String value= ((JsonElement)entry.getValue()).toString();
                            if(value!=null){
                                value=value.substring(1, value.length()-1);
                                columnvalues.add(value);
                            }

                        }
                        util.writeLine(columnvalues);

                    }
                    util.createWorkBook();
                } else if(quartztasker.getStoreTableName().endsWith(".txt")){
                    BufferedWriter  out=new BufferedWriter(new FileWriter(quartztasker.getStoreTableName(),false));
                    for(JsonObject obj:list){
                        out.write(obj.toString());
                        out.newLine();
                    }
                    out.close();
                }else{
                    for(JsonObject obj:list){
                        System.out.println(obj.toString());
                    }
                }

 			}else{
 				for(JsonObject obj:list){
 					System.out.println(obj.toString());
 	 			}
 			}
			
 
    }

}
