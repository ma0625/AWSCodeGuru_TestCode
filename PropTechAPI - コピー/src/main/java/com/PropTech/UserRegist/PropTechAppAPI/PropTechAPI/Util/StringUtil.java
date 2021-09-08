package com.PropTech.UserRegist.PropTechAppAPI.PropTechAPI.Util;

public class StringUtil {
	
	private String string;
	
	public StringUtil() {
		
	}
	
	public String getstring() {
		return string;
	}
	
	public void setstring(String string) {
		this.string = string;
	}
	
	public boolean isNullorEmpty(String string) {
		return(string == null || 0 == string.length());
	}

}
