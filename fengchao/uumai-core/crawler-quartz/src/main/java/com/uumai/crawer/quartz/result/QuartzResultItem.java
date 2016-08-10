package com.uumai.crawer.quartz.result;

import java.io.Serializable;

public class QuartzResultItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Object value;
	
	public QuartzResultItem(){
		
	}

	public QuartzResultItem(String name,String value){
		this.name=name;
		this.value=value;
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		if(value==null){
			return "";
		}
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	

}
