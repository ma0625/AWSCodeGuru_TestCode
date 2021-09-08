package com.PropTech.UserRegist.PropTechAppAPI.PropTechAPI.Logic;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.PropTech.UserRegist.PropTechApp.PropTechAPI.Response.ResponseDetails;
import com.PropTech.UserRegist.PropTechAppAPI.PropTechAPI.EntityDTO.Request;
import com.PropTech.UserRegist.PropTechAppAPI.PropTechAPI.EntityDTO.RequestBody;

public class UserRegistrationTestLogic {

	@Mock
	Connection con;
	
	@Mock
	PreparedStatement ps;
	
	@Mock
	ResultSet rs;
	
	@InjectMocks
	UserRegistrationLogic target = new UserRegistrationLogic();
	
	@Test
	public void testService_OK() throws SQLException {
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(con.prepareStatement(toString())).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);
		Mockito.when(rs.next()).thenReturn(true);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setage("24");
		body.setprofession("アルバイト");
		body.setusername("テスト太郎");
		body.setpassword("Test@1234");
		
		Response response = target.CRUDService(request, body);
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(),201);
		assertEquals(resDetails.getMessage(), "ユーザ情報が作成されました。");
	}
	
	@Test
	public void testService_E400_1() {
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setage("24");
		body.setprofession("アルバイト");
		body.setusername("テスト太郎");
		body.setpassword("Test@1234");
		
		Response response = target.CRUDService(request, body);
		
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 400);
		assertEquals(resDetails.getMessage(), "パラメータ形式に誤りがあります。");
	}
	
	@Test
	public void testService_E400_2() {
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setprofession("アルバイト");
		body.setusername("テスト太郎");
		body.setpassword("Test@1234");
		
		Response response = target.CRUDService(request, body);
		
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 400);
		assertEquals(resDetails.getMessage(), "パラメータ形式に誤りがあります。");
	}
	
	@Test
	public void testService_E400_3() {
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setage("24");
		body.setusername("テスト太郎");
		body.setpassword("Test@1234");
		
		Response response = target.CRUDService(request, body);
		
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 400);
		assertEquals(resDetails.getMessage(), "パラメータ形式に誤りがあります。");
	}
	
	@Test
	public void testService_E400_4() {
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setage("24");
		body.setprofession("アルバイト");
		body.setpassword("Test@1234");
		
		Response response = target.CRUDService(request, body);
		
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 400);
		assertEquals(resDetails.getMessage(), "パラメータ形式に誤りがあります。");
	}
	
	@Test
	public void testService_E400_5() {
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setage("24");
		body.setprofession("アルバイト");
		body.setusername("テスト太郎");
		
		Response response = target.CRUDService(request, body);
		
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 400);
		assertEquals(resDetails.getMessage(), "パラメータ形式に誤りがあります。");
	}

}
