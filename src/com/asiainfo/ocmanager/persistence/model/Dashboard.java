package com.asiainfo.ocmanager.persistence.model;

/**
 * 
 * @author zhaoyim
 *
 */
public class Dashboard {

	private int id;
	private String name;
	private String description;
	private String imageUrl;
	private String href;
	private boolean blank;

	public Dashboard() {

	}

	public Dashboard(String name, String description, String imageUrll, String href, boolean blank) {
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrll;
		this.href = href;
		this.blank = blank;
	}

	public Dashboard(int id, String name, String description, String imageUrll, String href, boolean blank) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrll;
		this.href = href;
		this.blank = blank;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isBlank() {
		return blank;
	}

	public void setBlank(boolean blank) {
		this.blank = blank;
	}

}
