package com.asiainfo.ocmanager.rest.utils;

import java.util.UUID;

/**
 * 
 * @author zhaoyim
 *
 */
public class UUIDFactory {

	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
}
