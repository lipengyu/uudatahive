package com.uumai.crawer.quartz.util;

import java.util.ArrayList;
import java.util.List;

import com.uumai.crawer.quartz.result.QuartzResult;
import com.uumai.crawer.quartz.result.QuartzResultItem;
import com.uumai.crawer.util.filesystem.ExcelFileUtil;

public class UumaiExportExcel {
	
	public void exportExcel(String filename,List<QuartzResult> results){
		 try {
	            ExcelFileUtil util=new ExcelFileUtil(filename);
	            for(int i=0;i<results.size();i++){
	            	QuartzResult quartzResult=results.get(i);
	            	 
	            	
	            	if(i==0){
	            		List<String> headlist=new ArrayList<String>();
	            		for(QuartzResultItem item:quartzResult.getItemlist()){
	            			headlist.add(item.getName());
		            	}
	            		util.writeLine(headlist);
	            	}
	            	
	            	List<String> datalist=new ArrayList<String>();
            		for(QuartzResultItem item:quartzResult.getItemlist()){
            			datalist.add(item.getValue().toString());
	            	}
            		util.writeLine(datalist);
	            	
	            }
	            util.createWorkBook();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

	public static void main(String[] args) {
// 	        UumaiResultUtil util=new UumaiResultUtil();
	        List<QuartzResult> results= null; //util.getresult("eastmoney", null);
	        
	        UumaiExportExcel eutil=new UumaiExportExcel();
	        eutil.exportExcel("/home/rock/tmp/eastmoney.xls", results);
	        
	        
	}

}
