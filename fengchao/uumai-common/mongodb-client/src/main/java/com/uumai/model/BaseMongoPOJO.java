package com.uumai.model;

import java.util.Date;

public class BaseMongoPOJO
{
	public String version;
	public Date createtime;

	public Date getCreatetime()
	{
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}