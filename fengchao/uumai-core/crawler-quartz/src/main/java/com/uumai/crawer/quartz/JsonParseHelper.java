package com.uumai.crawer.quartz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.uumai.crawer.quartz.result.QuartzResultItem;
import com.uumai.crawer.quartz.result.QuartzXpathItem;
import com.uumai.crawer.util.UumaiTime;
import com.uumai.crawer.util.filesystem.UumaiFileUtil;
import com.uumai.crawer2.CrawlerResult;
import com.uumai.crawler.selector.Html;

public class JsonParseHelper {
	
	QuartzCrawlerTasker tasker;
	CrawlerResult result;
	Map<String,String> localtempvariablemap;
	JsonParser jsonParser=new JsonParser();
	
	public JsonParseHelper(QuartzCrawlerTasker quartztasker,CrawlerResult result){
		this.tasker=quartztasker;
		this.result=result;
		
	}
	private void addNewLocalTempVariable(String name,String value){
		if(localtempvariablemap==null){
			localtempvariablemap=new HashMap<String,String>();
		}
		localtempvariablemap.put(name, value);
	}
	
	public List<JsonObject> parse(){
		List<JsonObject> jsonlist=new ArrayList<JsonObject>();
		JsonObject obj = createResultJson();
		
		for (int i = 0; i < tasker.getXpathList().size(); i++) {
			QuartzXpathItem item = tasker.getXpathList().get(i);
			if (item.getType().equals(QuartzXpathItem.XpathType._UUMAI_NEWROW_)) {
				if(i==0||i==tasker.getXpathList().size()-1){
					//first one or last one
				}else{
					jsonlist.add(obj);
					obj = createResultJson();
				}
			} else if (item.getType().equals(QuartzXpathItem.XpathType.Xpath)) {
				try {
					this.parseXpath(item, obj);
				} catch (Exception e) {
					processException(obj,item,e);
				}
			} else if (item.getType().equals(QuartzXpathItem.XpathType.Xpath_ALL)) {
				try {
					this.parseXpath_all(item, obj);
				} catch (Exception e) {
					processException(obj,item,e);
				}
			}else if (item.getType().equals(QuartzXpathItem.XpathType.JsonPath_ALL)) {
				try {
					this.parseJsonpath_all(item, obj);
				} catch (Exception e) {
					processException(obj,item,e);
				}
			}else if (item.getType()
					.equals(QuartzXpathItem.XpathType.JsonPath)) {
				try {
					this.parseJsonpath(item, obj);			
				} catch (Exception e) {
					processException(obj,item,e);
				}
			}else if (item.getType()
					.equals(QuartzXpathItem.XpathType.REGEX_EXPRESS)||item.getType()
					.equals(QuartzXpathItem.XpathType.REGEX_EXPRESS_ALL)) {
				try {
					processRegexObject(item, obj);				
				} catch (Exception e) {
					processException(obj,item,e);
				}
			}
		}
		jsonlist.add(obj);

		return jsonlist;
	}
	private void parseXpath_all(QuartzXpathItem item,JsonObject obj ){
		List<String> xpathvaluelist =null;
	    if(item.getFromsource()!=null){
			String tempvariable=this.localtempvariablemap.get(item.getFromsource());
			if(tempvariable==null) return;
			xpathvaluelist = new Html(tempvariable)
	 				.xpath(item.getXpath()).all();
	    }else{
	    	 xpathvaluelist = result.getHtml()
	 				.xpath(item.getXpath()).all();
	    }
		
		for(int j=0;j<xpathvaluelist.size();j++){
			String xpathvalue=xpathvaluelist.get(j);
			obj.addProperty(item.getXpathName()+j, xpathvalue);
		}
	}
	private void parseXpath(QuartzXpathItem item,JsonObject obj ){
		if("ALL".equalsIgnoreCase(item.getXpath())||"*".equalsIgnoreCase(item.getXpath())){
			obj.addProperty(item.getXpathName(), result.getRawText());
			return;
		} 
		String xpathvalue=null;
		if(item.getFromsource()!=null){
			String tempvariable=this.localtempvariablemap.get(item.getFromsource());
			if(tempvariable==null) return;
			xpathvalue = new Html(tempvariable).xpath(item.getXpath()).toString();
		}else{
			xpathvalue = result.getHtml()
					.xpath(item.getXpath()).toString();
		}
		if(xpathvalue==null) return;
		if(!item.isNotoutput())
			obj.addProperty(item.getXpathName(), xpathvalue);
		
		if(item.getAssource()!=null){
			this.addNewLocalTempVariable(item.getAssource(), xpathvalue);	
		}
			
		 
	}
	
	private void parseJsonpath_all(QuartzXpathItem item,JsonObject obj ){
		List<Object> xpathvaluelist =null;
		if(item.getFromsource()!=null){
			String tempvariable=this.localtempvariablemap.get(item.getFromsource());
			if(tempvariable==null) return;
			xpathvaluelist =JsonPath.read(
					tempvariable, item.getXpath());
		}else{
			xpathvaluelist =JsonPath.read(
					result.getRawText(), item.getXpath());
		}
 
		if(xpathvaluelist==null) return;
		
		for(int j=0;j<xpathvaluelist.size();j++){
			Object xpathvalue=xpathvaluelist.get(j);
			processJsonObject(xpathvalue,item.getXpathName()+j, obj);
		}
	}
	
	private void parseJsonpath(QuartzXpathItem item,JsonObject obj ){
		if("All".equalsIgnoreCase(item.getXpath())||"*".equalsIgnoreCase(item.getXpath())){
//			obj.addProperty(item.getXpathName(), result.getRawText());
			JsonObject temp=jsonParser.parse( result.getRawText()).getAsJsonObject();
 			Set<Entry<String, JsonElement>> entrySet = temp.entrySet();
			for(Map.Entry<String,JsonElement> entry : entrySet){
			  obj.add(entry.getKey(),temp.get(entry.getKey()));
			}	
			return;
		}
		Object xpathvalue=null;
		if(item.getFromsource()!=null){
			String tempvariable=this.localtempvariablemap.get(item.getFromsource());
			if(tempvariable==null) return;
			xpathvalue=JsonPath.read(
					tempvariable, item.getXpath());
		}else{
			xpathvalue=JsonPath.read(
					result.getRawText(), item.getXpath());
		}
		
		if (xpathvalue!=null){
			if(!item.isNotoutput())
				processJsonObject(xpathvalue,item.getXpathName(), obj);
			
			if(item.getAssource()!=null){
				this.addNewLocalTempVariable(item.getAssource(), xpathvalue.toString());
				
			}
		}
		
	}
	private void processRegexObject(QuartzXpathItem item, JsonObject obj){
	    	String xpathvalue=null;
	    	if(item.getFromsource()!=null){
	    		  xpathvalue=this.localtempvariablemap.get(item.getFromsource());				
	    	}else{
	    		xpathvalue=	  result.getRawText().toString();
	    	}
	    	if(xpathvalue==null) return;
	    	
	      // 创建 Pattern 对象
	      Pattern r = Pattern.compile(item.getXpath());

	      // 现在创建 matcher 对象
	      Matcher m = r.matcher(xpathvalue.toString());
	      
	      if(item.getType()
					.equals(QuartzXpathItem.XpathType.REGEX_EXPRESS)){
	    	  
	    	if(m.find()){
				if(!item.isNotoutput())
	 	    	   obj.addProperty(item.getXpathName(), m.group(1));
				
	 	    	 if(item.getAssource()!=null){
	 	    		 this.addNewLocalTempVariable(item.getAssource(), m.group(1));
	 	    	 }
	    	}
	    	  
	      }else{
	    	 int index=1;
	 	      while (m.find( )) {
	 	    	obj.addProperty(item.getXpathName()+index, m.group());
	 	    	index=index+1;
	 	      }  
	      }
	      
	}
	
	
	private JsonObject createResultJson() {
		JsonObject obj = new JsonObject();
        if(tasker.getTaskerOwner()!=null)
		    obj.addProperty("taskerowner", tasker.getTaskerOwner());
        if(tasker.getTaskerName()!=null)
		    obj.addProperty("taskername", tasker.getTaskerName());
        if(tasker.getTaskerSeries()!=null)
		    obj.addProperty("taskerseries", tasker.getTaskerSeries());

        if(tasker.getTaskerOwner()!=null||tasker.getTaskerName()!=null||tasker.getTaskerSeries()!=null)
		    obj.addProperty("createtime", new UumaiTime().getNowString());

//		obj.addProperty("currentcrawlerdeepth",
//				quartztasker.getCurrentCrawlerDeepth());

//		if (quartztasker.getCurrentCrawlerDeepth() != 0) {
//			obj.addProperty("fromurl", quartztasker.getFromUrl());
//		}

		for (QuartzResultItem item : tasker.getResultItems()) {
			Object itemobj=item.getValue();
			if(itemobj instanceof Integer){
				obj.addProperty(item.getName(), (Integer)itemobj);	
			}else if(itemobj instanceof Long){
				obj.addProperty(item.getName(), (Long)itemobj);	
			}else if(itemobj instanceof Double){
				obj.addProperty(item.getName(), (Double)itemobj);	
			}else{
				obj.addProperty(item.getName(), itemobj.toString());	
			}
			
		}
				return obj;
	}
	private void processJsonObject(Object xpathvalue,String xpathname, JsonObject obj){
		Gson gson = new Gson();

		if(xpathvalue  instanceof String){
			obj.addProperty(xpathname, xpathvalue.toString());
		}else if(xpathvalue  instanceof Integer){
			obj.addProperty(xpathname, (Integer)xpathvalue);
		}else if(xpathvalue  instanceof Double){
			obj.addProperty(xpathname, (Double)xpathvalue);
		}else if(xpathvalue  instanceof Boolean){
			obj.addProperty(xpathname, (Boolean)xpathvalue);
		}else if(xpathvalue  instanceof JSONObject){
			JSONObject jSONObject=(JSONObject)xpathvalue;
			JsonElement element= gson.fromJson(jSONObject.toString(), JsonElement.class);
			obj.add(xpathname,element );

		}else if(xpathvalue  instanceof JSONArray){
			JSONArray jSONArray=(JSONArray)xpathvalue;
			JsonElement element= gson.fromJson(jSONArray.toString(), JsonElement.class);
			obj.add(xpathname,element );
		}else{
			obj.addProperty(xpathname, xpathvalue.toString());
		}
	}
	
	private void processException(JsonObject obj, QuartzXpathItem item, Exception e){
		obj.addProperty(item.getXpathName(),"error:"+ e.getMessage()==null?"":e.getMessage());
		
	}
	
	public static void main(String[] a){
		UumaiFileUtil UumaiFileUtil=new UumaiFileUtil();
		String ss=UumaiFileUtil.readfromcache("/home/rock/Downloads/jalan-rajah_10613.html");
		//String ss="callback_product({ \"market\" : false , \"clothes\" : false , \"merchandise\" : false , \"products\" : [ ] , \"pageBar\" : { \"totalCount\" : 0 , \"totalPage\" : 1 , \"pageNumber\" : 1 , \"pageSize\" : 48}})";
		 // 创建 Pattern 对象
//	      Pattern r = Pattern.compile("\\<img src=(.*)\\/\\>");
	      Pattern r = Pattern.compile("class=\"bluelink\"(.*)</a>");

	      // 现在创建 matcher 对象
	      Matcher m = r.matcher(ss);
	      while (m.find( )) {
	    	   System.out.println(m.group());
	 	      }  
	}
}
