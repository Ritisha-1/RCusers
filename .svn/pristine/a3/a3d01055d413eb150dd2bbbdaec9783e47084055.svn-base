package com.mexel.frmk.util;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelper {
	public static Integer getInt(JSONObject ob, String key){
		try {
			return ob.getInt(key);
		} catch (JSONException e) {
			throw new RuntimeException("Object does not have value with key "+key);
		}
	}

	public static String  getString(JSONObject ob, String key){
		try {
			return ob.getString(key);
		} catch (JSONException e) {
			throw new RuntimeException("Object does not have value with key "+key,e);
		}
	}

	public static Boolean  getBoolean(JSONObject ob, String key){
		try {
			return ob.getBoolean(key);
		} catch (JSONException e) {
			throw new RuntimeException("Object does not have value with key "+key,e);
		}
	}
	public static Date  getDate(JSONObject ob, String key){
		try {
			return CommonUtils.toDate(ob.getString(key));
		} catch (JSONException e) {
			throw new RuntimeException("Object does not have value with key "+key,e);
		}
	}


}
