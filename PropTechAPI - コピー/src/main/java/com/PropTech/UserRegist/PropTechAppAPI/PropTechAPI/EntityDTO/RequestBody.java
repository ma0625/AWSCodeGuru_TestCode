package com.PropTech.UserRegist.PropTechAppAPI.PropTechAPI.EntityDTO;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestBody {
	
	private String sex;
	
	private String age;
	
	private String profession;
	
	private String username;
	
	private String password;
	
	private String token;
	
	public String getsex() {
		return this.sex;
	}
	
	public void setsex(final String sex) {
		this.sex = sex;
	}
	
	public String getage() {
		return this.age;
	}
	
	public void setage(final String age) {
		this.age = age;
	}
	
	public String getprofession() {
		return this.profession;
	}
	
	public void setprofession(final String profession) {
		this.profession = profession;
	}
	
	public String getusername() {
		return this.username;
	}
	
	public void setusername(final String username) {
		this.username = username;
	}
	
	public String getpassword() {
		return this.password;
	}
	
	public void setpassword(final String password) {
		this.password = password;
	}
	
	public String gettoken() {
		return this.token;
	}
	
	public void settoken(final String token) {
		this.token = token;
	}

}
