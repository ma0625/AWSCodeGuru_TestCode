package com.src.Internship.Project_Version1.InternshipProject_Versaion1.Logic;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.core.Response;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.src.Internship.Project.Internship_Project.Entity.Request;
import com.src.Internship.Project.Internship_Project.Entity.RequestBody;
import com.src.Internship.Project_Version1.InternshipProject_Version1.ResponseCommon.ResponseDetails;
import com.src.Internship.Project_Version1.Internship_Project_Version1.Logic.UserQuestionnaireLogic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UserQuestionnaireLogic.class})
public class UserQuestionnaireLogicTest {

	@Mock
	Connection con;
	
	@Mock
	PreparedStatement ps;
	
	@Mock
	ResultSet rs;
	
	@InjectMocks
	UserQuestionnaireLogic UserQuestionnaire = new UserQuestionnaireLogic();
	
	@Test
	public void testService_OK() throws SQLException{
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(con.prepareStatement(toString())).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);
		Mockito.when(rs.next()).thenReturn(true);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setage("24");
		body.setusername("テスト太郎");
		body.setquestion1("テスト");
		body.setquestion2("テスト");
		body.setquestion3("テスト");
		body.setquestion4("テスト");
		body.setquestion5("テスト");
		body.setquestion6("テスト");
		
		Response response = UserQuestionnaire.CRUDService(request, body);
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 201);
		assertEquals(resDetails.getMessage(), "アンケートが登録されました。");
	}
	//所々虫食いにする。
	@Test
	public void testService_E400_1() {
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setage("24");
		body.setusername("テスト太郎");
		body.setquestion1("テスト");
		body.setquestion2("テスト");
		body.setquestion3("テスト");
		body.setquestion4("テスト");
		body.setquestion5("テスト");
		body.setquestion6("テスト");
		
		Response response = UserQuestionnaire.CRUDService(request, body);
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 400);
		assertEquals(resDetails.getMessage(), "パラメータ形式が異なります。");
	}
	//ここからメソッド名のみ記述
	@Test
	public void testService_E400_2() {
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setusername("テスト太郎");
		body.setquestion1("テスト");
		body.setquestion2("テスト");
		body.setquestion3("テスト");
		body.setquestion4("テスト");
		body.setquestion5("テスト");
		body.setquestion6("テスト");
		
		Response response = UserQuestionnaire.CRUDService(request, body);
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 400);
		assertEquals(resDetails.getMessage(), "パラメータ形式が異なります。");
	}
	
	@Test
	public void testService_E400_3() {
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setage("24");
		body.setquestion1("テスト");
		body.setquestion2("テスト");
		body.setquestion3("テスト");
		body.setquestion4("テスト");
		body.setquestion5("テスト");
		body.setquestion6("テスト");
		
		Response response = UserQuestionnaire.CRUDService(request, body);
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 400);
		assertEquals(resDetails.getMessage(), "パラメータ形式が異なります。");
	}
	
	@Test
	public void testService_E400_4() {
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setage("24");
		body.setusername("テスト太郎");
		body.setquestion2("テスト");
		body.setquestion3("テスト");
		body.setquestion4("テスト");
		body.setquestion5("テスト");
		body.setquestion6("テスト");
		
		Response response = UserQuestionnaire.CRUDService(request, body);
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 400);
		assertEquals(resDetails.getMessage(), "パラメータ形式が異なります。");
	}
	
	@Test
	public void testService_E400_5() {
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setage("24");
		body.setusername("テスト太郎");
		body.setquestion1("テスト");
		body.setquestion3("テスト");
		body.setquestion4("テスト");
		body.setquestion5("テスト");
		body.setquestion6("テスト");
		
		Response response = UserQuestionnaire.CRUDService(request, body);
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 400);
		assertEquals(resDetails.getMessage(), "パラメータ形式が異なります。");
	}
	
	@Test
	public void testService_E400_6() {
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setage("24");
		body.setusername("テスト太郎");
		body.setquestion1("テスト");
		body.setquestion2("テスト");
		body.setquestion4("テスト");
		body.setquestion5("テスト");
		body.setquestion6("テスト");
		
		Response response = UserQuestionnaire.CRUDService(request, body);
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 400);
		assertEquals(resDetails.getMessage(), "パラメータ形式が異なります。");
	}
	
	@Test
	public void testService_E400_7() {
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setage("24");
		body.setusername("テスト太郎");
		body.setquestion1("テスト");
		body.setquestion2("テスト");
		body.setquestion3("テスト");
		body.setquestion5("テスト");
		body.setquestion6("テスト");
		
		Response response = UserQuestionnaire.CRUDService(request, body);
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 400);
		assertEquals(resDetails.getMessage(), "パラメータ形式が異なります。");
	}
	
	@Test
	public void testService_E400_8() {
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setage("24");
		body.setusername("テスト太郎");
		body.setquestion1("テスト");
		body.setquestion2("テスト");
		body.setquestion3("テスト");
		body.setquestion4("テスト");
		body.setquestion6("テスト");
		
		Response response = UserQuestionnaire.CRUDService(request, body);
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 400);
		assertEquals(resDetails.getMessage(), "パラメータ形式が異なります。");
	}
	
	@Test
	public void testService_E400_9() {
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setage("24");
		body.setusername("テスト太郎");
		body.setquestion1("テスト");
		body.setquestion2("テスト");
		body.setquestion3("テスト");
		body.setquestion4("テスト");
		body.setquestion5("テスト");
		
		Response response = UserQuestionnaire.CRUDService(request, body);
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 400);
		assertEquals(resDetails.getMessage(), "パラメータ形式が異なります。");
	}
	
	@Test
	public void testService_E500_1() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		Request request = new Request();
		RequestBody body = new RequestBody();
		
		body.setsex("男");
		body.setage("24");
		body.setusername("テスト太郎");
		body.setquestion1("テスト");
		body.setquestion2("テスト");
		body.setquestion3("テスト");
		body.setquestion4("テスト");
		body.setquestion5("テスト");
		
		String seqId = "2021/09/10:12000000001";
		Exception expected_exception = new Exception("error!");
		
		UserQuestionnaireLogic spy = PowerMockito.spy(new UserQuestionnaireLogic());
		PowerMockito.whenNew(UserQuestionnaireLogic.class).withNoArguments().thenReturn(spy);
		PowerMockito.when(spy, "GenerateddbConnectionGetseqId", toString()).thenThrow(expected_exception);
		
		Response response = UserQuestionnaire.CRUDService(request, body);
		ResponseDetails resDetails = (ResponseDetails) response.getEntity();
		assertEquals(resDetails.getCode(), 500);
		assertEquals(resDetails.getMessage(), "データベースへの接続に問題があります。");
	}
}
