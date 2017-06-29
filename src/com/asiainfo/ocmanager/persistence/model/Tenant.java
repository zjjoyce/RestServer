package com.asiainfo.ocmanager.persistence.model;

/**
 *
 * @author zhaoyim
 *
 */
public class  Tenant {

	private String id;
	private String name;
	private String description;
	private String parentId;
	private int level;
	private int dacpTeamCode;

	public Tenant() {

	}

	public Tenant(String id, String name, String description, String parentId, int level) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.parentId = parentId;
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getDacpTeamCode() {
		return dacpTeamCode;
	}

	public void setDacpTeamCode(int dacpTeamCode) {
		this.dacpTeamCode = dacpTeamCode;
	}

}
