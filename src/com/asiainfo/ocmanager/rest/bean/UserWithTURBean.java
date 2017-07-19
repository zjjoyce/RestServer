package com.asiainfo.ocmanager.rest.bean;

import java.util.List;

import com.asiainfo.ocmanager.persistence.model.User;
import com.asiainfo.ocmanager.persistence.model.UserRoleView;

public class UserWithTURBean extends User {

	private List<UserRoleView> urv;

	public UserWithTURBean() {

	}

	public UserWithTURBean(User user) {
		super(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone(),
				user.getDescription(), user.getCreatedUser());

	}

	public List<UserRoleView> getUrv() {
		return urv;
	}

	public void setUrv(List<UserRoleView> urv) {
		this.urv = urv;
	}

}
