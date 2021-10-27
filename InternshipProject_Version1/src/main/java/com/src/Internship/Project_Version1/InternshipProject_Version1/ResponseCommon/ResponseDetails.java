package com.src.Internship.Project_Version1.InternshipProject_Version1.ResponseCommon;

public class ResponseDetails {
	
private int code;
	
	private String time;
	
	private String message;

	public ResponseDetails() {
		this.setCode(new Integer(code));
		this.setTime(new String());
		this.setMessage(new String());
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

}
