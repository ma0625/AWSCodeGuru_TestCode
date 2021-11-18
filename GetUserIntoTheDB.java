package com.src.proptech.PropTechApp.Logic;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;

import com.src.proptech.PropTechApp.DataSource.DataSourceHolder;
import com.src.proptech.PropTechApp.Entity.Request;
import com.src.proptech.PropTechApp.Entity.RequestBody;
import com.src.proptech.PropTechApp.ResponseCommon.ResponseCommon;
import com.src.proptech.PropTechApp.ResponseCommon.ResponseDetails;
import com.src.proptech.PropTechApp.SendMail.SendClassNotFoundExceptionMailUtilLogic;
import com.src.proptech.PropTechApp.SendMail.SendExceptionMailUtilLogic;
import com.src.proptech.PropTechApp.SendMail.SendSQLExceptionMailUtilLogic;
import com.src.proptech.PropTechApp.ShareJDBCLogic.InsertJDBCNewUserRegistLogic;
import com.src.proptech.PropTechApp.ShareJDBCLogic.InsertJDBCProcedureStatusRegisterLogic;
import com.src.proptech.PropTechApp.Util.StringUtil;
/**
 * ###################################################<br>
 * 
 * 　システム名　　　 ：かんたん新居手続きみんなのアプリ ユーザー存在確認API<br>
 * 　処理概要　　　　 ：パスパラメータリクエストにて受け取った検索対象userid
 * 								がT_USERINFOテーブル並びにT_PROCEDURESTATUS
 * 								テーブルに存在するかどうかを確認する。<br>
 * 　プログラム名　　 ：ユーザー存在確認API<br>
 * 　引数				　　：$1 :userid<br>
 * 　戻り値			　　：201 - ユーザー作成完了時<br>
 * 								 200 - テーブルに既にユーザーが存在した場合のレスポンス<br>
 * 					   　　　400 - リクエストパラメータ不正<br>
 * 　　　　　　　　　　500 - PostgreSQLへのコネクション確立失敗時<br>
 * 　注意事項　　　　 ：WAS Liberty with Java EE 7 Web Profile環境を構築し
 * 　　　　　　　　　　eclipse2020-06環境で動作<br>
 * 　実行環境		　　：JAX-RS(Restful API)<br>
 * 						　　　Maven Group Id : webApp-jee7-liberty<br>
 * 					    　　　Artifact Id           : webapp-jee7-liberty<br>
 * 						　　　version		         : 1<br>
 * 						　　　Liberty Version   : 17.0.0.2<br>
 * 						　　　JDK Version         : 1.8<br>
 * 　サブプログラム　 ：F_RequestBodyCheck<br>
 * 								  F_GeneratedbConnectionSelect<br>
 *　 設定プロファイル ：DataSourceHolder<br>
 *									 SendSQLExceptionMailUtilLogic<br>
 *									 SendClassNotFoundExceptionMailUtilLogic<br>
 *									 InsertJDBCNewUserRegistLogic<br>
 *									 InsertJDBCProcedureStatusRegisterLogic<br>
 * Copyright 🄫 2021-2021 SOLPAC Co.,Ltd. All Rights Reserved <br>
 * 
 *###################################################<br>
 *　変更履歴<br>
 *　日付　　　　　　担当者　　　　　変更理由<br>
 *　__________　__________　__________<br>
 *　2021/11/10	深谷 理幸             新規 <br>
 *	  2021/11/15	深谷 理幸			   [202111P047] 対応<br>
 *	  2021/11/15	深谷 理幸			   [202111P050] 対応<br>
 *	  2021/11/17	深谷 理幸			   [202111P053] 対応<br>
 *###################################################<br>
 */

@RequestScoped
public class GetUserIntoTheDB {
	
	//StringUtilクラスのインスタンス化
	StringUtil stringUtil = new StringUtil();
		
	//Requestクラスのインスタンス化
	Request request = new Request();
	
	//RequestBodyクラスのインスタンス化
	RequestBody body = new RequestBody();
	
	//DataSourceHolderクラスのインスタンス化
	DataSourceHolder datasourceHolder = new DataSourceHolder();
		
	//SendSQLExceptionMailUtilLogicクラスのインスタンス化
	SendSQLExceptionMailUtilLogic sendSQLExceptionMail = new SendSQLExceptionMailUtilLogic();
				
	//SendClassNotFoundExceptionMailUtilLogicクラスのインスタンス化
	SendClassNotFoundExceptionMailUtilLogic sendClassNotFoundExceptionMail = new SendClassNotFoundExceptionMailUtilLogic();
	
	//InsertJDBCLogicクラスのインスタンス化
	InsertJDBCNewUserRegistLogic inserJDBCLogic = new InsertJDBCNewUserRegistLogic();
	
	//InsertJDBCProcedureStatusRegisterLogicクラスのインスタンス化
	InsertJDBCProcedureStatusRegisterLogic insertJDBCProcedureStatusRegisterLogic = new InsertJDBCProcedureStatusRegisterLogic();
		
	private Logger log;
	
	public GetUserIntoTheDB() {
		//ロギングマネージャーの構築
		log = LogManager.getFormatterLogger(GetUserIntoTheDB.this);
	}
	
	public Response F_GenerateResponse(ResponseCommon responseCommon) {
		ResponseDetails responseDetails = new ResponseDetails();
		responseDetails.setCode(responseCommon.getResponseStatus());
		responseDetails.setMessage(responseCommon.getResponseMessage());
		responseDetails.setDate(LocalDateTime.now().toString());
		return Response.status(responseCommon.getStatus()).entity(responseDetails).build();
	}
	
	/**
	 * ###################################################<br>
	 * 　メイン処理<br>
	 * ###################################################<br>
	*/
	
	public Response F_CRUDService(String userId) {
		ResponseDetails responseDetails = new ResponseDetails();
		ResponseCommon responseCommon = ResponseCommon.S201;
		
		Response typeResponse = F_CheckRequestParam(userId);
		try {
			if(typeResponse.getStatus() != Response.Status.CREATED.getStatusCode()){
				return typeResponse;
			}else {
				log.info("F_CheckRequestBody is success.");
			}
		}finally {
			//Not Execute
		}
		
		try {
			if(typeResponse.getStatus() != Response.Status.CREATED.getStatusCode()){
				return typeResponse;
			}else {
				log.info("F_GeneratedbConnectionSelect is success");
			}
		}finally {
			//Not Execute
		}
		
		/**Response typeResponse = F_CheckRequestParam(userId);
		if(typeResponse.getStatus() != Response.Status.CREATED.getStatusCode()){
			return typeResponse;
		}else {
			log.info("F_CheckRequestBody is success.");
		}
		
		typeResponse = F_GeneratedbConnectionSelect(userId);
		if(typeResponse.getStatus() != Response.Status.CREATED.getStatusCode()){
			return typeResponse;
		}else {
			log.info("F_GeneratedbConnectionSelect is success");
		}*/
		
		responseDetails.setCode(responseCommon.getResponseStatus());
		responseDetails.setMessage(responseCommon.getResponseMessage());
		responseDetails.setDate(LocalDateTime.now().toString());
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(responseDetails).build();
	}
	
	/**
	 * ###################################################<br>
	 *　メソッド名		：F_CheckRequestBody<br>
	 *　処理概要　　  ：モバイルクライアントより受け付けたリクエストボディのパラメータ
	 *							   チェックを行う<br>
	 *　使用方法　　  ：パスパラメータにuseridを含め、リクエスト<br>
	 *　引数				 ：userid - 検索対象ユーザー
	 *　戻り値			 ：201 - 正常終了時<br>
	 *　　　　　　　　　400 - リクエストパラメータに不正があった場合<br>
	 * ###################################################<br>
	*/
	
	private Response F_CheckRequestParam(String userId) {
		ResponseDetails responseDetails = new ResponseDetails();
		if(stringUtil.isNullorEmpty(userId)) {
			log.error(userId + "リクエストボディが不正です。");
			return F_GenerateResponse(ResponseCommon.E400);
		}else {
			//Not Execute
		}
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(responseDetails).build();
	}
	
	/**
	 * ###################################################<br>
	 *　メソッド名		：F_GeneratedbConnectionSelect<br>
	 *　処理概要　　  ：useridがT_USERINFOテーブルで同じuseridが利用されていないか重複
	 *							   チェックを行う<br>
	 *　使用方法　　  ：なし<br>
	 *　引数			    ：body - パラメータ形式でのリクエストボディ格納DTOクラス<br>
	 *　戻り値			：201 - 正常終了時<br>
	 *							   200 - ユーザーIDが存在した場合<br>
	 *							   500 - PostgreSQLへのコネクション確立失敗時<br>
	 * ###################################################<br>
	*/
	
	private Response F_GeneratedbConnectionSelect(String userId) {
		log.info(userId + " " + "F_GeneratedbConnectionSelect start.");
		ResponseDetails responseDetails = new ResponseDetails();
		//int deleteFlag = 0;
		int retryMaxCount = 0;
		//int importantStatus = 0;
		//int rentStatus = 0;
		//int managementStatus = 0;
		//int guaranteeStatus = 0;
		int userCount1 = 0;
		int userCount2 = 0;
		int addCount = 0;
		String sexType = "9";
		String ageType = "9";
		String jobType = "9";
		String hashPhoneNumber = "00000000000";
		//[202111P053] 2021/11/17 m.fukaya add start
		String policy = "3";
		//[202111P053] 2021/11/17 m.fukaya add end
		//String token = "initialvalue";
		String exceptionClassNotFound = new String();
		String exceptionSQL = new String();
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		//Date importantEndDate = null;
		//Date rentEndDate = null;
		//Date managementEndDate = null;
		//Date guaranteeEndDate = null;
		//Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		
		//[202111P047] 2021/11/15 m.fukaya delete start
		/**while(retryMaxCount < 3) {
			//DBコネクション処理及びSQL文の発行
			try(Connection connection1 = datasourceHolder.connection(Duration.ofSeconds(12));
					PreparedStatement preparedStatement1 = connection1.prepareStatement("SELECT * FROM T_USERINFO WHERE USERID = ?")){
				preparedStatement1.setString(1,  userid);
				
				//SQLクエリログ
				log.info(userid + preparedStatement1);
				//ユーザーの存在確認処理
				try(ResultSet resultSet = preparedStatement1.executeQuery()){
					if(resultSet.next()) {
						//既にユーザーが存在している場合は、HTTP Status Code 200をクライアントへ返却する。
						return F_GenerateResponse(ResponseCommon.S200);
					}else {
						log.error(userid + " " + "ユーザーが存在しませんでした。");
						//DBコネクション処理及びSQL文の発行
						try(Connection connection2 = datasourceHolder.connection(Duration.ofSeconds(12))){
							connection2.setAutoCommit(false);
							try(PreparedStatement preparedStatement2 = connection2.prepareStatement("INSERT INTO T_USERINFO("
									+ "USERID,"
									+ "SEX_TYPE,"
									+ "AGE_TYPE,"
									+ "JOB_TYPE,"
									+ "MOBILE_NUMBER,"
									+ "LATEST_LOGIN_DATETIME,"
									+ "LATEST_LOGOUT_DATETIME,"
									+ "LAST_OPERATION_HISTORY,"
									+ "REGISTRATION_DATETIME,"
									+ "ACCESS_TOKEN,"
									+ "DELETE_FLG)"
									+ "VALUES(?,?,?,?,?,?,?,?,?, ?, ?)")){
								preparedStatement2.setString(1,  userid);
								preparedStatement2.setInt(2, sexType);
								preparedStatement2.setInt(3, ageType);
								preparedStatement2.setInt(4, jobType);
								preparedStatement2.setString(5, mobileNumber);
								preparedStatement2.setTimestamp(6, currentTime);
								preparedStatement2.setTimestamp(7, currentTime);
								preparedStatement2.setTimestamp(8, currentTime);
								preparedStatement2.setTimestamp(9, currentTime);
								preparedStatement2.setString(10, token);
								preparedStatement2.setInt(11, deleteFlag);
								preparedStatement2.executeUpdate();
								connection2.commit();
							}
							
							//DBコネクション処理及びSQL文の発行
							try(Connection connection3 = datasourceHolder.connection(Duration.ofSeconds(12))){
								connection3.setAutoCommit(false);
								try(PreparedStatement preparedStatement3 = connection3.prepareStatement("INSERT INTO T_PROCEDURESTATUS("
										+ "USERID,"
										+ "IMPORTANT_STATUS,"
										+ "IMPORTANT_ENDDATE,"
										+ "RENT_STATUS,"
										+ "RENT_ENDDATE,"
										+ "MANAGEMENT_STATUS,"
										+ "MANAGEMENT_ENDDATE,"
										+ "GUARANTEE_STATUS,"
										+ "GUARANTEE_ENDDATE)"
										+ "VALUES(?,?,?,?,?,?,?,?,?)")){
									preparedStatement3.setString(1,  userid);
									preparedStatement3.setInt(2, importantStatus);
									preparedStatement3.setDate(3, importantEndDate);
									preparedStatement3.setInt(4, rentStatus);
									preparedStatement3.setDate(5, rentEndDate);
									preparedStatement3.setInt(6,  managementStatus);
									preparedStatement3.setDate(7,  managementEndDate);
									preparedStatement3.setInt(8,  guaranteeStatus);
									preparedStatement3.setDate(9,  guaranteeEndDate);
									preparedStatement3.executeUpdate();
									connection3.commit();
								}
							}catch(ClassNotFoundException classNotFoundException) {
								//Exception情報をString型へ変換
								classNotFoundException.printStackTrace(printWriter);
								printWriter.flush();
								exceptionClassNotFound = stringWriter.toString();
								
								//エラーログ（ClassNotFoundException）
								log.error(userid + " " + "アプリケーションが、クラスの文字列名を使用して次のメソッドでロードしようとしたが、"
										+ "指定された名前のクラス定義が見つかりませんでした。" + LocalDateTime.now());
								log.error(userid + " " + "次の情報を確認してください。 \n"
										+ "クラス Classのfornameメソッド \n"
										+ "クラス ClassLoaderのfindSystemClassメソッド \n"
										+ "クラス ClassLoaderのloadClassメソッド");
								
								//sendClassNotFoundExceptionMailへのパラメータ格納
								String ClassNotFoundException = exceptionClassNotFound;
								String ExceptionClass = "NewUserRegistrationLogic.java";
								String ExceptionMethod = "F_GeneratedbConnectionInsert";
								String ExceptionName = "ClassNotFoundException";
								
								//メール送信クラスの呼び出し
								sendClassNotFoundExceptionMail.F_SendMail(ClassNotFoundException, ExceptionClass, ExceptionMethod, ExceptionName);
								return F_GenerateResponse(ResponseCommon.E500);
							}catch(SQLException sqlException) {
								//Exception情報をString型へ変換
								sqlException.printStackTrace(printWriter);
								printWriter.flush();
								exceptionSQL = stringWriter.toString();
								
								//エラーログ（SQLException）
								log.error(userid + " " +  "SQLStatusCode：" + sqlException.getSQLState());
								log.error(userid + " " + "SQLErrorCode：" + sqlException.getErrorCode());
								log.error(userid + " " + "SQLErrorMessage：" + sqlException.getMessage());
								
								//sendSQLExceptionMailへのパラメータ格納
								String SQLException = exceptionSQL;
								String SQLStatusCode = sqlException.getSQLState();
								int SQLErrorCode = sqlException.getErrorCode();
								String SQLErrorMessage = sqlException.getMessage();
								String ExceptionClass = "NewUserRegistrationLogic.java";
								String ExceptionMethod = "F_GeneratedbConnectionInsert";
								String ExceptionName = "SQLException";
								
								//メール送信クラスの呼び出し
								sendSQLExceptionMail.F_SendMail(SQLException, SQLStatusCode, SQLErrorCode, SQLErrorMessage, ExceptionClass, ExceptionMethod, ExceptionName);
								return F_GenerateResponse(ResponseCommon.E500);
							}
						}catch(ClassNotFoundException classNotFoundException) {
							//Exception情報をString型へ変換
							classNotFoundException.printStackTrace(printWriter);
							printWriter.flush();
							exceptionClassNotFound = stringWriter.toString();
							
							//エラーログ（ClassNotFoundException）
							log.error(userid + " " + "アプリケーションが、クラスの文字列名を使用して次のメソッドでロードしようとしたが、"
									+ "指定された名前のクラス定義が見つかりませんでした。" + LocalDateTime.now());
							log.error(userid + " " + "次の情報を確認してください。 \n"
									+ "クラス Classのfornameメソッド \n"
									+ "クラス ClassLoaderのfindSystemClassメソッド \n"
									+ "クラス ClassLoaderのloadClassメソッド");
							
							//sendClassNotFoundExceptionMailへのパラメータ格納
							String ClassNotFoundException = exceptionClassNotFound;
							String ExceptionClass = "NewUserRegistrationLogic.java";
							String ExceptionMethod = "F_GeneratedbConnectionInsert";
							String ExceptionName = "ClassNotFoundException";
							
							//メール送信クラスの呼び出し
							sendClassNotFoundExceptionMail.F_SendMail(ClassNotFoundException, ExceptionClass, ExceptionMethod, ExceptionName);
							return F_GenerateResponse(ResponseCommon.E500);
						}catch(SQLException sqlException) {
							//Exception情報をString型へ変換
							sqlException.printStackTrace(printWriter);
							printWriter.flush();
							exceptionSQL = stringWriter.toString();
							
							//エラーログ（SQLException）
							log.error(userid + " " +  "SQLStatusCode：" + sqlException.getSQLState());
							log.error(userid + " " + "SQLErrorCode：" + sqlException.getErrorCode());
							log.error(userid + " " + "SQLErrorMessage：" + sqlException.getMessage());
							
							//sendSQLExceptionMailへのパラメータ格納
							String SQLException = exceptionSQL;
							String SQLStatusCode = sqlException.getSQLState();
							int SQLErrorCode = sqlException.getErrorCode();
							String SQLErrorMessage = sqlException.getMessage();
							String ExceptionClass = "NewUserRegistrationLogic.java";
							String ExceptionMethod = "F_GeneratedbConnectionInsert";
							String ExceptionName = "SQLException";
							
							//メール送信クラスの呼び出し
							sendSQLExceptionMail.F_SendMail(SQLException, SQLStatusCode, SQLErrorCode, SQLErrorMessage, ExceptionClass, ExceptionMethod, ExceptionName);
							return F_GenerateResponse(ResponseCommon.E500);
						}
					}
				}
			} catch (ClassNotFoundException classNotFoundException) {
				//Exception情報をString型へ変換
				classNotFoundException.printStackTrace(printWriter);
				printWriter.flush();
				exceptionClassNotFound = stringWriter.toString();
				
				//エラーログ（ClassNotFoundException）
				log.error(userid + " " + "アプリケーションが、クラスの文字列名を使用して次のメソッドでロードしようとしたが、"
						+ "指定された名前のクラス定義が見つかりませんでした。" + LocalDateTime.now());
				log.error(userid + " " + "次の情報を確認してください。 \n"
						+ "クラス Classのfornameメソッド \n"
						+ "クラス ClassLoaderのfindSystemClassメソッド \n"
						+ "クラス ClassLoaderのloadClassメソッド");
				
				//sendClassNotFoundExceptionMailへのパラメータ格納
				String ClassNotFoundException = exceptionClassNotFound;
				String ExceptionClass = "NewUserRegistrationLogic.java";
				String ExceptionMethod = "F_GeneratedbConnectionInsert";
				String ExceptionName = "ClassNotFoundException";
				
				//メール送信クラスの呼び出し
				sendClassNotFoundExceptionMail.F_SendMail(ClassNotFoundException, ExceptionClass, ExceptionMethod, ExceptionName);
				
				//リトライカウントの加算
				retryMaxCount ++;
				//リトライカウントが3以下の場合、リトライ処理を続ける
				if(retryMaxCount < 3) {
					continue;
				}else {
					//リトライカウントが3になった場合、クライアントへHTTP Status Code 500 を返却する
					return F_GenerateResponse(ResponseCommon.E500);
				}
			} catch (SQLException sqlException) {
				//Exception情報をString型へ変換
				sqlException.printStackTrace(printWriter);
				printWriter.flush();
				exceptionSQL = stringWriter.toString();
				
				//エラーログ（SQLException）
				log.error(userid + " " +  "SQLStatusCode：" + sqlException.getSQLState());
				log.error(userid + " " + "SQLErrorCode：" + sqlException.getErrorCode());
				log.error(userid + " " + "SQLErrorMessage：" + sqlException.getMessage());
				
				//sendSQLExceptionMailへのパラメータ格納
				String SQLException = exceptionSQL;
				String SQLStatusCode = sqlException.getSQLState();
				int SQLErrorCode = sqlException.getErrorCode();
				String SQLErrorMessage = sqlException.getMessage();
				String ExceptionClass = "NewUserRegistrationLogic.java";
				String ExceptionMethod = "F_GeneratedbConnectionInsert";
				String ExceptionName = "SQLException";
				
				//メール送信クラスの呼び出し
				sendSQLExceptionMail.F_SendMail(SQLException, SQLStatusCode, SQLErrorCode, SQLErrorMessage, ExceptionClass, ExceptionMethod, ExceptionName);
				
				//リトライカウントの加算
				retryMaxCount ++;
				//リトライカウントが3以下の場合、リトライ処理を続ける
				if(retryMaxCount < 3) {
					continue;
				}else {
					//リトライカウントが3になった場合、クライアントへHTTP Status Code 500 を返却する
					return F_GenerateResponse(ResponseCommon.E500);
				}
			}
			break;
		}*/
		//[202111P047] 2021/11/15 m.fukaya delete end
		//[202111P047] 2021/11/15 m.fukaya add start
		while(retryMaxCount < 3) {
			//DBコネクション処理及びSQL文の発行
			try(Connection connection1 = datasourceHolder.connection(Duration.ofSeconds(12));
					PreparedStatement preparedStatement1 = connection1.prepareStatement("SELECT (SELECT COUNT (*) FROM T_USERINFO userinfo WHERE USERID = ?)"
																																								+ ",(SELECT COUNT (*) FROM T_PROCEDURESTATUS procedurestatus WHERE USERID = ?)")){
				preparedStatement1.setString(1,  userId);
					preparedStatement1.setString(2, userId);
				
				log.info(userId + preparedStatement1);
				//ユーザーの存在確認
				try(ResultSet resultSet = preparedStatement1.executeQuery()){
					if(resultSet.next()) {
						userCount1 = resultSet.getInt(1);
							userCount2 = resultSet.getInt(2);
					}else {
						//Not Execute
					}
					addCount = userCount1 + userCount2;
					if(addCount == 0) {
						//[202111P050] 2021/11/15 m.fukaya delete start
						//DBコネクション処理及びSQL文の発行
						/**try(Connection connection2 = datasourceHolder.connection(Duration.ofSeconds(12))){
							connection2.setAutoCommit(false);
							try(PreparedStatement preparedStatement2 = connection2.prepareStatement("INSERT INTO T_USERINFO("
									+ "USERID,"
									+ "SEX_TYPE,"
									+ "AGE_TYPE,"
									+ "JOB_TYPE,"
									+ "MOBILE_NUMBER,"
									+ "LATEST_LOGIN_DATETIME,"
									+ "LATEST_LOGOUT_DATETIME,"
									+ "LAST_OPERATION_HISTORY,"
									+ "REGISTRATION_DATETIME,"
									+ "ACCESS_TOKEN,"
									+ "DELETE_FLG)"
									+ "VALUES(?,?,?,?,?,?,?,?,?, ?, ?)")){
								preparedStatement2.setString(1,  userid);
								preparedStatement2.setInt(2, sexType);
								preparedStatement2.setInt(3, ageType);
								preparedStatement2.setInt(4, jobType);
								preparedStatement2.setString(5, mobileNumber);
								preparedStatement2.setTimestamp(6, currentTime);
								preparedStatement2.setTimestamp(7, currentTime);
								preparedStatement2.setTimestamp(8, currentTime);
								preparedStatement2.setTimestamp(9, currentTime);
								preparedStatement2.setString(10, token);
								preparedStatement2.setInt(11, deleteFlag);
								preparedStatement2.executeUpdate();
								connection2.commit();
							}
							
							//DBコネクション処理及びSQL文の発行
							try(Connection connection3 = datasourceHolder.connection(Duration.ofSeconds(12))){
								connection3.setAutoCommit(false);
								try(PreparedStatement preparedStatement3 = connection3.prepareStatement("INSERT INTO T_PROCEDURESTATUS("
										+ "USERID,"
										+ "IMPORTANT_STATUS,"
										+ "IMPORTANT_ENDDATE,"
										+ "RENT_STATUS,"
										+ "RENT_ENDDATE,"
										+ "MANAGEMENT_STATUS,"
										+ "MANAGEMENT_ENDDATE,"
										+ "GUARANTEE_STATUS,"
										+ "GUARANTEE_ENDDATE)"
										+ "VALUES(?,?,?,?,?,?,?,?,?)")){
									preparedStatement3.setString(1,  userid);
									preparedStatement3.setInt(2, importantStatus);
									preparedStatement3.setDate(3, importantEndDate);
									preparedStatement3.setInt(4, rentStatus);
									preparedStatement3.setDate(5, rentEndDate);
									preparedStatement3.setInt(6,  managementStatus);
									preparedStatement3.setDate(7,  managementEndDate);
									preparedStatement3.setInt(8,  guaranteeStatus);
									preparedStatement3.setDate(9,  guaranteeEndDate);
									preparedStatement3.executeUpdate();
									connection3.commit();
								}
							}catch(ClassNotFoundException classNotFoundException) {
								//Exception情報をString型へ変換
								classNotFoundException.printStackTrace(printWriter);
								printWriter.flush();
								exceptionClassNotFound = stringWriter.toString();
								
								//エラーログ（ClassNotFoundException）
								log.error(userid + " " + "アプリケーションが、クラスの文字列名を使用して次のメソッドでロードしようとしたが、"
										+ "指定された名前のクラス定義が見つかりませんでした。" + LocalDateTime.now());
								log.error(userid + " " + "次の情報を確認してください。 \n"
										+ "クラス Classのfornameメソッド \n"
										+ "クラス ClassLoaderのfindSystemClassメソッド \n"
										+ "クラス ClassLoaderのloadClassメソッド");
								
								//sendClassNotFoundExceptionMailへのパラメータ格納
								String ClassNotFoundException = exceptionClassNotFound;
								String ExceptionClass = "NewUserRegistrationLogic.java";
								String ExceptionMethod = "F_GeneratedbConnectionInsert";
								String ExceptionName = "ClassNotFoundException";
								
								//メール送信クラスの呼び出し
								sendClassNotFoundExceptionMail.F_SendMail(ClassNotFoundException, ExceptionClass, ExceptionMethod, ExceptionName);
								return F_GenerateResponse(ResponseCommon.E500);
							}catch(SQLException sqlException) {
								//Exception情報をString型へ変換
								sqlException.printStackTrace(printWriter);
								printWriter.flush();
								exceptionSQL = stringWriter.toString();
								
								//エラーログ（SQLException）
								log.error(userid + " " +  "SQLStatusCode：" + sqlException.getSQLState());
								log.error(userid + " " + "SQLErrorCode：" + sqlException.getErrorCode());
								log.error(userid + " " + "SQLErrorMessage：" + sqlException.getMessage());
								
								//sendSQLExceptionMailへのパラメータ格納
								String SQLException = exceptionSQL;
								String SQLStatusCode = sqlException.getSQLState();
								int SQLErrorCode = sqlException.getErrorCode();
								String SQLErrorMessage = sqlException.getMessage();
								String ExceptionClass = "NewUserRegistrationLogic.java";
								String ExceptionMethod = "F_GeneratedbConnectionInsert";
								String ExceptionName = "SQLException";
								
								//メール送信クラスの呼び出し
								sendSQLExceptionMail.F_SendMail(SQLException, SQLStatusCode, SQLErrorCode, SQLErrorMessage, ExceptionClass, ExceptionMethod, ExceptionName);
								return F_GenerateResponse(ResponseCommon.E500);
							}
						}catch(ClassNotFoundException classNotFoundException) {
							//Exception情報をString型へ変換
							classNotFoundException.printStackTrace(printWriter);
							printWriter.flush();
							exceptionClassNotFound = stringWriter.toString();
							
							//エラーログ（ClassNotFoundException）
							log.error(userid + " " + "アプリケーションが、クラスの文字列名を使用して次のメソッドでロードしようとしたが、"
									+ "指定された名前のクラス定義が見つかりませんでした。" + LocalDateTime.now());
							log.error(userid + " " + "次の情報を確認してください。 \n"
									+ "クラス Classのfornameメソッド \n"
									+ "クラス ClassLoaderのfindSystemClassメソッド \n"
									+ "クラス ClassLoaderのloadClassメソッド");
							
							//sendClassNotFoundExceptionMailへのパラメータ格納
							String ClassNotFoundException = exceptionClassNotFound;
							String ExceptionClass = "NewUserRegistrationLogic.java";
							String ExceptionMethod = "F_GeneratedbConnectionInsert";
							String ExceptionName = "ClassNotFoundException";
							
							//メール送信クラスの呼び出し
							sendClassNotFoundExceptionMail.F_SendMail(ClassNotFoundException, ExceptionClass, ExceptionMethod, ExceptionName);
							return F_GenerateResponse(ResponseCommon.E500);
						}catch(SQLException sqlException) {
							//Exception情報をString型へ変換
							sqlException.printStackTrace(printWriter);
							printWriter.flush();
							exceptionSQL = stringWriter.toString();
							
							//エラーログ（SQLException）
							log.error(userid + " " +  "SQLStatusCode：" + sqlException.getSQLState());
							log.error(userid + " " + "SQLErrorCode：" + sqlException.getErrorCode());
							log.error(userid + " " + "SQLErrorMessage：" + sqlException.getMessage());
							
							//sendSQLExceptionMailへのパラメータ格納
							String SQLException = exceptionSQL;
							String SQLStatusCode = sqlException.getSQLState();
							int SQLErrorCode = sqlException.getErrorCode();
							String SQLErrorMessage = sqlException.getMessage();
							String ExceptionClass = "NewUserRegistrationLogic.java";
							String ExceptionMethod = "F_GeneratedbConnectionInsert";
							String ExceptionName = "SQLException";
							
							//メール送信クラスの呼び出し
							sendSQLExceptionMail.F_SendMail(SQLException, SQLStatusCode, SQLErrorCode, SQLErrorMessage, ExceptionClass, ExceptionMethod, ExceptionName);
							return F_GenerateResponse(ResponseCommon.E500);
						}*/
						//[202111P050] 2021/11/15 m.fukaya delete end
						//[202111P050] 2021/11/15 m.fukaya add start
						inserJDBCLogic.F_CRUDService(userId, sexType, ageType, jobType, hashPhoneNumber, policy);
						insertJDBCProcedureStatusRegisterLogic.F_CRUDService(userId);
						//[202111P050] 2021/11/15 m.fukaya add end
					}else if(addCount == 1) {
						return F_GenerateResponse(ResponseCommon.E500);
					}else{
						return F_GenerateResponse(ResponseCommon.S200);
					}
				}
			} catch (ClassNotFoundException classNotFoundException) {
				//Exception情報をString型へ変換
				classNotFoundException.printStackTrace(printWriter);
				printWriter.flush();
				exceptionClassNotFound = stringWriter.toString();
				
				//エラーログ（ClassNotFoundException）
				log.error(userId + " " + "アプリケーションが、クラスの文字列名を使用して次のメソッドでロードしようとしたが、"
						+ "指定された名前のクラス定義が見つかりませんでした。" + LocalDateTime.now());
				log.error(userId + " " + "次の情報を確認してください。 \n"
						+ "クラス Classのfornameメソッド \n"
						+ "クラス ClassLoaderのfindSystemClassメソッド \n"
						+ "クラス ClassLoaderのloadClassメソッド");
				
				//sendClassNotFoundExceptionMailへのパラメータ格納
				String ClassNotFoundException = exceptionClassNotFound;
				String ExceptionClass = "NewUserRegistrationLogic.java";
				String ExceptionMethod = "F_GeneratedbConnectionInsert";
				String ExceptionName = "ClassNotFoundException";
				
				//メール送信クラスの呼び出し
				sendClassNotFoundExceptionMail.F_SendMail(ClassNotFoundException, ExceptionClass, ExceptionMethod, ExceptionName);
				//リトライカウントの加算
				retryMaxCount ++;
				//リトライカウントが3以下の場合、リトライ処理を続ける
				if(retryMaxCount < 3) {
					continue;
				}else {
					//リトライカウントが3になった場合、クライアントへHTTP Status Code 500 を返却する
					return F_GenerateResponse(ResponseCommon.E500);
				}
			} catch (SQLException sqlException) {
				//Exception情報をString型へ変換
				sqlException.printStackTrace(printWriter);
				printWriter.flush();
				exceptionSQL = stringWriter.toString();
				
				//エラーログ（SQLException）
				log.error(userId + " " +  "SQLStatusCode：" + sqlException.getSQLState());
				log.error(userId + " " + "SQLErrorCode：" + sqlException.getErrorCode());
				log.error(userId + " " + "SQLErrorMessage：" + sqlException.getMessage());
				
				//sendSQLExceptionMailへのパラメータ格納
				String SQLException = exceptionSQL;
				String SQLStatusCode = sqlException.getSQLState();
				int SQLErrorCode = sqlException.getErrorCode();
				String SQLErrorMessage = sqlException.getMessage();
				String ExceptionClass = "NewUserRegistrationLogic.java";
				String ExceptionMethod = "F_GeneratedbConnectionInsert";
				String ExceptionName = "SQLException";
				
				//メール送信クラスの呼び出し
				sendSQLExceptionMail.F_SendMail(SQLException, SQLStatusCode, SQLErrorCode, SQLErrorMessage, ExceptionClass, ExceptionMethod, ExceptionName);
				//リトライカウントの加算
				retryMaxCount ++;
				//リトライカウントが3以下の場合、リトライ処理を続ける
				if(retryMaxCount < 3) {
					continue;
				}else {
					//リトライカウントが3になった場合、クライアントへHTTP Status Code 500 を返却する
					return F_GenerateResponse(ResponseCommon.E500);
				}
			}
			break;
		}
		//[202111P047] 2021/11/15 m.fukaya add end
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(responseDetails).build();
	}

}
