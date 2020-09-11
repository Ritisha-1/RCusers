package com.mexel.frmk.session;


public class SessionHandler {
	private final SharedPreferences preference;

	public SessionHandler(SharedPreferences preference) {
		this.preference = preference;
	}

	public SharedPreferences getPreference() {
		return preference;
	}

	public void putValue(String key, String value) {
		SharedPreferences.Editor prefsEditor = preference.edit();

		prefsEditor.putString(key, value);
		prefsEditor.commit();
	}

	public void putValue(String key, boolean value) {
		SharedPreferences.Editor prefsEditor = preference.edit();

		prefsEditor.putBoolean(key, value);
		prefsEditor.commit();
	}
	
	public void putDefaultValue(String key, String value){
		if(preference.contains(key)){
			return ;
		}
		SharedPreferences.Editor prefsEditor = preference.edit();

		prefsEditor.putString(key, value);
		prefsEditor.commit();
	}

	public void putDefaultValue(String key, int value){
		if(preference.contains(key)){
			return ;
		}
		SharedPreferences.Editor prefsEditor = preference.edit();

		prefsEditor.putInt(key, value);
		prefsEditor.commit();
	}

	public void putDefaultValue(String key, boolean value){
		if(preference.contains(key)){
			return ;
		}
		SharedPreferences.Editor prefsEditor = preference.edit();

		prefsEditor.putBoolean(key, value);
		prefsEditor.commit();
	}
	
	public void putDefaultValue(String key, Long value){
		if(preference.contains(key)){
			return ;
		}
		SharedPreferences.Editor prefsEditor = preference.edit();

		prefsEditor.putLong(key, value);
		prefsEditor.commit();
	}


	public void putValue(String key, Long value) {
		SharedPreferences.Editor prefsEditor = preference.edit();
		prefsEditor.putLong(key, value);
		prefsEditor.commit();
	}

	public void putValue(String key, Integer value) {
		SharedPreferences.Editor prefsEditor = preference.edit();
		prefsEditor.putInt(key, value);
		prefsEditor.commit();
	}

	public String getLastFolder() {
		return preference.getString("file_folder", null);
	}

	public void setLastFolder(String folderPath) {
		SharedPreferences.Editor prefsEditor = preference.edit();
		prefsEditor.putString("file_folder", folderPath);
		prefsEditor.commit();

	}


	public String getStringValue(String key) {
		return preference.getString(key, null);
	}


	public long getLongValue(String key) {
		return preference.getLong(key,-1);
	}

	public int getIntValue(String key) {
		try{
			return preference.getInt(key,-1);	
		}catch(Exception ex){
			return (int)preference.getLong(key,-1);
		}
		
	}

	public boolean getBooleanValue(String key) {
		return preference.getBoolean(key,false);
	}
}
