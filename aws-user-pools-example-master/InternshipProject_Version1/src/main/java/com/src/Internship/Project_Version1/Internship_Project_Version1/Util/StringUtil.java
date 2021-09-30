package com.src.Internship.Project_Version1.Internship_Project_Version1.Util;

public class StringUtil {
	
private String string;
	
	public StringUtil() {
		
	}
	
	public String getString() {
		return string;
	}
	
	public void setstring(String string) {
		this.string = string;
	}
	
	public boolean isNullorEmpty(String string) {
		return(string == null || 0 == string.length());
	}

}
