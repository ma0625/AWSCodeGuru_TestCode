package com.PropTech.UserRegist.PropTechAppAPI.PropTechAPI.Logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.PropTech.UserRegist.PropTechApp.PropTechAPI.PasswordHashUtil.PasswordHashUtil;
import com.PropTech.UserRegist.PropTechApp.PropTechAPI.Response.ResponseCommon;
import com.PropTech.UserRegist.PropTechApp.PropTechAPI.Response.ResponseDetails;
import com.PropTech.UserRegist.PropTechAppAPI.PropTechAPI.EntityDTO.Request;
import com.PropTech.UserRegist.PropTechAppAPI.PropTechAPI.EntityDTO.RequestBody;
import com.PropTech.UserRegist.PropTechAppAPI.PropTechAPI.Util.StringUtil;

@RequestScoped
public class UserRegistrationLogic {
	
	//@Inject
	StringUtil stringutil = new StringUtil();
	
	Request request = new Request();
	
	RequestBody body = new RequestBody();
	
	public UserRegistrationLogic() {
		
	}
	
	private final static String URL = "jdbc:postgresql://localhost:5432/UserRegistrationAPI";
	private final static String USER = "postgres";
	private final static String PASSWORD = "password";
	
	public Response GenerateResponse(ResponseCommon responsecommon) {
		ResponseDetails responsedetails = new ResponseDetails();
		responsedetails.setTime(LocalDateTime.now().toString());
		responsedetails.setCode(responsecommon.getStatus());
		responsedetails.setMessage(responsecommon.getResponseMessage());
		return Response.status(responsecommon.getStatus()).entity(responsedetails).build();
	}
	
	public Response CRUDService(Request request, RequestBody body) {
		ResponseDetails responsedetails = new ResponseDetails();
		ResponseCommon responsecommon = ResponseCommon.S201;
		StringBuilder seqID = new StringBuilder();
		StringBuilder HashPassword = new StringBuilder();
		
		Response TypeUserResponse = RequestBodyCheck(request, body);
		if(TypeUserResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeUserResponse;
		}else {
			System.out.println("RequestBodyCheck:正常終了");
		}
		
		TypeUserResponse = GenerateddbConnectionGetseqId(seqID);
		if(TypeUserResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeUserResponse;
		}else {
			System.out.println("GenerateddbConnectionGetseqId:正常終了");
		}
		
		TypeUserResponse = GeneratedHashPassword(body, HashPassword);
		if(TypeUserResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeUserResponse;
		}else {
			System.out.println("GeneratedHashPassword:正常終了");
		}
		
		TypeUserResponse = GeneratedbConnectionSelect(HashPassword);
		if(TypeUserResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeUserResponse;
		}else {
			System.out.println("GeneratedbConnectionSelect:正常終了");
		}
		
		TypeUserResponse = GenerateddbConnectionInsert(body, seqID, HashPassword);
		if(TypeUserResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeUserResponse;
		}else {
			System.out.println("GenerateddbConnectionInsert:正常終了");
		}
		responsedetails.setCode(responsecommon.getStatus());
		responsedetails.setTime(LocalDateTime.now().toString());
		responsedetails.setMessage(responsecommon.getResponseMessage());
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(responsedetails).build();
	}
	
	private Response RequestBodyCheck(Request request, RequestBody body) {
		ResponseDetails responsedetails = new ResponseDetails();
		if(stringutil.isNullorEmpty(body.getsex()) ||
		   stringutil.isNullorEmpty(body.getage()) ||
		   stringutil.isNullorEmpty(body.getprofession()) ||
		   stringutil.isNullorEmpty(body.getusername()) ||
		   stringutil.isNullorEmpty(body.getpassword()) ||
		   stringutil.isNullorEmpty(body.gettoken())) {
		   return GenerateResponse(ResponseCommon.E400);
		}else {
			//System.out.println("Nullはありませんでした。");
		}
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(responsedetails).build();
	}
	
	private Response GenerateddbConnectionGetseqId(StringBuilder seqID) {
		ResponseDetails responsedetails = new ResponseDetails();
		String Format = new String();
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement("SELECT NEXTVAL('apiseqno')");
					ResultSet resultset = preparedStatement.executeQuery();){
			boolean br = false;
			br = resultset.next();
			
			Date date = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
			String stringDate = dateformat.format(date);
			Format = stringDate + String.format("%07d", resultset.getInt("nextval"));
			seqID.append(Format);
		}catch(SQLException exception) {
			exception.printStackTrace();
			//System.out.println(exception);
			return GenerateResponse(ResponseCommon.E500);
		}finally {
			//System.out.println("完了");
		}
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(responsedetails).build();
	}
	
	private Response GeneratedHashPassword(RequestBody body, StringBuilder HashPassword) {
		ResponseDetails responsedetails = new ResponseDetails();
		String SHA256Password = PasswordHashUtil.getSaftyPassword("password", body.getpassword());
		HashPassword.append(SHA256Password);
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(responsedetails).build();
	}
	
	private Response GeneratedbConnectionSelect(StringBuilder HashPassword) {
		ResponseDetails responsedetails = new ResponseDetails();
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM userregistrationapi WHERE password = ?")){
			preparedStatement.setString(1, HashPassword.toString());
			
			try (ResultSet resultset = preparedStatement.executeQuery()){
				while(resultset.next()) {
					System.out.println(resultset.getString("password"));
					if(resultset.getString("password").equals(null)) {
						//Not Execute
					}else {
						return GenerateResponse(ResponseCommon.E500);
					}
				}
			}catch(SQLException sqlexception) {
				sqlexception.printStackTrace();
				return GenerateResponse(ResponseCommon.E500);
			}
		}catch(SQLException exception) {
			exception.printStackTrace();
			return GenerateResponse(ResponseCommon.E500);
		}finally {

		}
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(responsedetails).build();
	}
	
	private Response GenerateddbConnectionInsert(RequestBody body, StringBuilder seqID, StringBuilder HashPassword) {
		ResponseDetails responsedetails = new ResponseDetails();
		
		int age = Integer.parseInt(body.getage());
		//System.out.println(age);
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
			connection.setAutoCommit(false);
			try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO userregistrationapi("
						+ "api_seq_no,"
						+ "sex,"
						+ "age,"
						+ "profession,"
						+ "username,"
						+ "password,"
						+ "token)"
						+ "VALUES(?,?,?,?,?,?,?)")){
				preparedStatement.setString(1, seqID.toString());
				preparedStatement.setString(2, body.getsex());
				preparedStatement.setInt(3, age);
				preparedStatement.setString(4,  body.getprofession());
				preparedStatement.setString(5,  body.getusername());
				preparedStatement.setString(6,  HashPassword.toString());
				preparedStatement.setString(7,  body.gettoken());
				preparedStatement.executeUpdate();
				connection.commit();
				
			}catch(Exception exception) {
				connection.rollback();
				return GenerateResponse(ResponseCommon.E500);
				//System.out.println(exception);
			}finally {
			//System.out.println("完了");
			}
			
		}catch(SQLException exception) {
			exception.printStackTrace();
			return GenerateResponse(ResponseCommon.E500);
		}finally {
			//System.out.println("DBへのInsertが完了しました。");
		}
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(responsedetails).build();
	}
}
