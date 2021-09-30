package com.src.Internship.Project_Version1.Internship_Project_Version1.Logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;

import com.src.Internship.Project.Internship_Project.Entity.Request;
import com.src.Internship.Project.Internship_Project.Entity.RequestBody;
import com.src.Internship.Project_Version1.InternshipProject_Version1.ResponseCommon.ResponseCommon;
import com.src.Internship.Project_Version1.InternshipProject_Version1.ResponseCommon.ResponseDetails;
import com.src.Internship.Project_Version1.Internship_Project_Version1.Util.StringUtil;

@RequestScoped
public class UserQuestionnaireLogic {
	
	StringUtil stringutil = new StringUtil();
	
	Request request = new Request();
	
	RequestBody body = new RequestBody();
	
	public UserQuestionnaireLogic() {
		
	}
	/**
	 * データベース接続設定
	 * RDBMS：PostgreSQL
	 * 
	 */
	private final static String URL = "jdbc:postgresql://localhost:5432/UserRegistrationAPI";
	private final static String USER = "postgres";
	private final static String PASSWORD = "password";
	/**
	 * Enumクラスに定義したHTTPステータスコードの構築
	 * setTime:処理完了時刻
	 * setCode:HTTP Status Code
	 * setMessage:ステータスコードに応じたメッセージ
	 * 
	 * @param responsecommon
	 * @return responsecommon.getStatus
	 */
	public Response GenerateResponse(ResponseCommon responsecommon) {
		ResponseDetails responsedetails = new ResponseDetails();
		responsedetails.setTime(LocalDateTime.now().toString());
		responsedetails.setCode(responsecommon.getStatus());
		responsedetails.setMessage(responsecommon.getResponseMessage());
		return Response.status(responsecommon.getStatus()).entity(responsedetails).build();
	}
	//引数全削除
	public Response CRUDService(Request request, RequestBody body) {
		ResponseDetails responsedetails = new ResponseDetails();
		ResponseCommon responsecommon = ResponseCommon.S201;
		StringBuilder seqId = new StringBuilder();
		
		Response TypeUserResponse = RequestBodyCheck(request, body);
		if(TypeUserResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeUserResponse;
		}else {
			//Not Execute
			System.out.println("RequestBodyCheck:正常終了");
		}
		
		TypeUserResponse = GenerateddbConnectionGetseqId(seqId);
		if(TypeUserResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeUserResponse;
		}else {
			//Not Execute
			System.out.println("GenerateddbConnectionGetseqId:正常終了");
		}
		
		TypeUserResponse = GeneratedbConnectionInsert(body, seqId);
		if(TypeUserResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeUserResponse;
		}else {
			//Not Execute
			System.out.println("GeneratedbConnectionInsert:正常終了");
		}
		
		responsedetails.setCode(responsecommon.getStatus());
		responsedetails.setTime(LocalDateTime.now().toString());
		responsedetails.setMessage(responsecommon.getResponseMessage());
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(responsedetails).build();
	}
	/**
	 * リクエストボディのnullチェック
	 * nullチェック対象パラメーター
	 *・性別
	 *・年齢
	 *・ユーザー名
	 *・アンケート１
	 *・アンケート２
	 *・アンケート３
	 *・アンケート４
	 *・アンケート５
	 *・アンケート6
	 * @param request
	 * @param body
	 * @return
	 */
	private Response RequestBodyCheck(Request request, RequestBody body) {
		ResponseDetails responsedetails = new ResponseDetails();
		if(stringutil.isNullorEmpty(body.getsex()) ||
		   stringutil.isNullorEmpty(body.getage()) ||
		   stringutil.isNullorEmpty(body.getusername()) ||
		   stringutil.isNullorEmpty(body.getquestion1()) ||
		   stringutil.isNullorEmpty(body.getquestion2()) ||
		   stringutil.isNullorEmpty(body.getquestion3()) ||
		   stringutil.isNullorEmpty(body.getquestion4()) ||
		   stringutil.isNullorEmpty(body.getquestion5()) ||
		   stringutil.isNullorEmpty(body.getquestion6())) {
			//Nullチェックもスケルトン化
			return GenerateResponse(ResponseCommon.E400);
		}else {
			//Not Execute
		}
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(responsedetails).build();
	}
	/**
	 * PostgreSQLよりシーケンスを取得するSelect文を発行
	 * Primary Keyの構成
	 * 日付 +　7桁の数字　+　取得したシーケンスNo
	 * @param seqId
	 * @return
	 */
	private Response GenerateddbConnectionGetseqId(StringBuilder seqId) {
		ResponseDetails responsedetails = new ResponseDetails();
		String Format = new String();
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement("SELECT NEXTVAL('questionnaireseqno')");
					ResultSet resultset = preparedStatement.executeQuery();){
			boolean br = false;
			br = resultset.next();
			
			Date date = new Date();
			TimeZone timeZoneJP = TimeZone.getTimeZone("Asia/Tokyo");
			SimpleDateFormat dateformat = new SimpleDateFormat();
			dateformat.setTimeZone(timeZoneJP);
			String stringDate = dateformat.format(date);
			Format = stringDate + String.format("%07d", resultset.getInt("nextval"));
			seqId.append(Format);
			//SimpleDateFormatのみで構成し、タイムゾーンは含めないコードを用意
		}catch(SQLException sqlexception) {
			sqlexception.printStackTrace();
			return GenerateResponse(ResponseCommon.E500);
		}finally {
			//Not Execute
		}
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(responsedetails).build();
	}
	
	private Response GeneratedbConnectionInsert(RequestBody body, StringBuilder seqId) {
		//メソッド内ロジックを削除
		ResponseDetails responsedetails = new ResponseDetails();
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
			connection.setAutoCommit(false);
			try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO questionnairetable("
					+ "questionnaire_seqno,"
					+ "sex,"
					+ "age,"
					+ "username,"
					+ "question1,"
					+ "question2,"
					+ "question3,"
					+ "question4,"
					+ "question5,"
					+ "question6)"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?)")){
				//Insert文をスケルトン化
				preparedStatement.setString(1, seqId.toString());
				preparedStatement.setString(2, body.getsex());
				preparedStatement.setString(3, body.getage());
				preparedStatement.setString(4, body.getusername());
				preparedStatement.setString(5, body.getquestion1());
				preparedStatement.setString(6, body.getquestion2());
				preparedStatement.setString(7, body.getquestion3());
				preparedStatement.setString(8, body.getquestion4());
				preparedStatement.setString(9, body.getquestion5());
				preparedStatement.setString(10, body.getquestion6());
				preparedStatement.executeUpdate();
				//preparedStatementをスケルトン化
				connection.commit();
			}catch(Exception exception) {
				connection.rollback();
				System.out.println(exception);
				return GenerateResponse(ResponseCommon.E500);
			}finally {
				//Not Execute
			}
		}catch(SQLException sqlexception) {
			sqlexception.printStackTrace();
			System.out.println(sqlexception);
			return GenerateResponse(ResponseCommon.E500);
		}finally {
			//Not Execute
		}
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(responsedetails).build();
	}

}
