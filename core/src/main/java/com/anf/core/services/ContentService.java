package com.anf.core.services;

import org.json.JSONObject;

/**
 * Begin Code - Barkatulla Khan
 */
public interface ContentService {
	boolean validateUserAge(int age);
	
	String commitUserDetails(JSONObject userDetails);
}
/**
 * END Code
 */
