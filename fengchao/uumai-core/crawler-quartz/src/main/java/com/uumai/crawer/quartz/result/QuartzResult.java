package com.uumai.crawer.quartz.result;

import java.util.ArrayList;
import java.util.List;

public class QuartzResult {
	
	private List<QuartzResultItem> itemlist=new ArrayList<QuartzResultItem>();

	public List<QuartzResultItem> getItemlist() {
		return itemlist;
	}

	public void setItemlist(List<QuartzResultItem> itemlist) {
		this.itemlist = itemlist;
	}
	
	
	public QuartzResultItem getItem(String name){
		for(QuartzResultItem item:itemlist){
			if(item.getName().equals(name))
				return item;
		}
		return new QuartzResultItem(name,null);
	}

}
