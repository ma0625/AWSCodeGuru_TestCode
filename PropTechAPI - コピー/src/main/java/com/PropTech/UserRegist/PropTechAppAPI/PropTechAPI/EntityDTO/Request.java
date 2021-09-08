package com.PropTech.UserRegist.PropTechAppAPI.PropTechAPI.EntityDTO;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.HeaderParam;

@RequestScoped
public class Request {
	
	@HeaderParam("sex")
	private String sex;
	
	@HeaderParam("age")
	private String age;
	
	@HeaderParam("profession")
	private String profession;
	
	@HeaderParam("username")
	private String username;
	
	@HeaderParam("password")
	private String password;
	
	@HeaderParam("token")
	private String token;
	
	public void setsex(String sex) {
		this.sex = sex;
	}
	
	public String getsex() {
		return this.sex;
	}
	
	public void setage(String age) {
		this.age = age;
	}
	
	public String getage() {
		return this.age;
	}
	
	public void setprofession(String profession) {
		this.profession = profession;
	}
	
	public String getprofession() {
		return this.profession;
	}
	
	public void setusername(String username) {
		this.username = username;
	}
	
	public String getusername() {
		return this.username;
	}
	
	public void setpassword(String password) {
		this.password = password;
	}
	
	public String getpassword() {
		return this.password;
	}
	
	public void settoken(String token) {
		this.token = token;
	}
	
	public String gettoken() {
		return this.token;
	}

}
