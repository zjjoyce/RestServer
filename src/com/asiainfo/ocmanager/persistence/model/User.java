package com.asiainfo.ocmanager.persistence.model;

/**
 * 
 * @author zhaoyim
 *
 */
public class User {
	private String id;
	private String username;
	private String password;
	private String email;
	private String description;

	public User() {

	}

	public User(String id, String username, String password, String email, String description) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
