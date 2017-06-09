package com.asiainfo.ocmanager.persistence.mapper;

import java.util.List;

import com.asiainfo.ocmanager.persistence.model.User;

/**
 * 
 * @author zhaoyim
 *
 */

public interface UserMapper {

	/**
	 * 
	 * @return
	 */
	public List<User> selectAllUsers();

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public User selectUserById(String userId);

	/**
	 * 
	 * @param user
	 * @return
	 */
	public void insertUser(User user);

	/**
	 * 
	 * @param user
	 */
	public void updateUser(User user);

	/**
	 * 
	 * @param userId
	 */
	public void deleteUser(String userId);

}
