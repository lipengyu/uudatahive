package com.uumai.crawer.quartz.result;

import java.io.Serializable;

public class QuartzXpathItem implements Serializable {
	public enum XpathType {
		Xpath,Xpath_ALL, JsonPath   ,JsonPath_ALL, REGEX_EXPRESS,REGEX_EXPRESS_ALL, _UUMAI_NEWROW_
	}

	private XpathType type;
	private String xpathName;
	private String xpath;
	private String xpathvalue;
	private String fromsource;
	private String assource;
	private boolean notoutput=false;
	
	public QuartzXpathItem asSource(String sourcename){
		this.assource=sourcename;
		return this;
	}
	public void fromsource(String sourcename){
		this.fromsource=sourcename;
	}

	public void setNotOutput(){
		this.notoutput=true;
	}
	public String getXpathName() {
		return xpathName;
	}

	public void setXpathName(String xpathName) {
		this.xpathName = xpathName;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public XpathType getType() {
		return type;
	}

	public void setType(XpathType type) {
		this.type = type;
	}

	public String getXpathvalue() {
		return xpathvalue;
	}

	public void setXpathvalue(String xpathvalue) {
		this.xpathvalue = xpathvalue;
	}
	public String getFromsource() {
		return fromsource;
	}
	public void setFromsource(String fromsource) {
		this.fromsource = fromsource;
	}
	public String getAssource() {
		return assource;
	}
	public void setAssource(String assource) {
		this.assource = assource;
	}
	public boolean isNotoutput() {
		return notoutput;
	}
	
	

}
