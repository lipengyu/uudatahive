package com.uumai.crawer.quartz.core.test;

import java.io.File;
import java.io.IOException;

import com.jayway.jsonpath.JsonPath;

public class JsonTest {

	public static void main(String[] args) throws IOException {
 		File text=new File("/tmp/jd.json");
 		
		Object value=JsonPath.read(text, "jingdong_service_promotion_getcode_responce.queryjs_result.resultCode");
 	
		System.out.println(value);
		
	}

}
