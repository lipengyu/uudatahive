package com.uumai.crawer.quartz.util;

public class QuartzQueryItem {
	private String name;
	private Object value;
	public String getName() {
		return name;
	}
	public QuartzQueryItem(String name,Object value){
		this.name=name;
		this.value=value;
		
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
