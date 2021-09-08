package com.PropTech.UserRegist.PropTechApp.PropTechAPI.Response;

import javax.ws.rs.core.Response;
public enum ResponseCommon {
	//ErrorResponse
	E400(Response.Status.BAD_REQUEST.getStatusCode(), "E400", "パラメータ形式に誤りがあります。"),
	E500(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "E500", "データベースへの接続に問題があります。"),
	//SuccessResponse
	S201(Response.Status.CREATED.getStatusCode(), "S201", "ユーザ情報が作成されました。");
	
	private int Status;
	
	private String ResponseStatus;
	
	private String ResponseMessage;
	
	private ResponseCommon(int Status, String ResponseStatus, String ResponseMessage) {
		this.Status = Status;
		this.ResponseStatus = ResponseStatus;
		this.ResponseMessage = ResponseMessage;
	}
	
	public int getStatus() {
		return Status;
	}
	
	public String getResponseStatus() {
		return ResponseStatus;
	}
	
	public String getResponseMessage() {
		return ResponseMessage;
	}
}
