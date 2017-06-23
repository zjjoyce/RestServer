package com.asiainfo.ocmanager.persistence.model;

/**
 *
 * @author yujing2
 *
 */
public class Quota {

	private String name;
	private String size;
	private String used;
	private String available;
	private String desc;

	public Quota() {

	}
	public Quota(String name,String size,String used,String available,String desc) {
    this.name = name;
    this.size = size;
    this.used = used;
    this.available = available;
    this.desc = desc;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getSize(){ return size; }
	public void setSize(String size){this.size = size; }

	public String getUsed(){ return used; }
	public void setUsed(String used){ this.used = used; }

	public String getAvailable(){ return available;}
	public void setAvailable(String available){ this.available = available;}

	public String getDesc(){ return desc;  }
	public void setDesc(String desc){ this.desc = desc;}
}
