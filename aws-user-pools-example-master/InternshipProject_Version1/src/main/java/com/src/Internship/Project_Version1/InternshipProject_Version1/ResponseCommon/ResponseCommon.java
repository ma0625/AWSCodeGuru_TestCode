package com.src.Internship.Project_Version1.InternshipProject_Version1.ResponseCommon;

import javax.ws.rs.core.Response;

public enum ResponseCommon {

		//ErrorResponse
		E400(Response.Status.BAD_REQUEST.getStatusCode(), "E400", "パラメータ形式が異なります。"),
		E409(Response.Status.CONFLICT.getStatusCode(),"E409", "アンケートは一度のみの回答になります。"),
		E500(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "E500", "データベースへの接続に問題があります。"),
		//SuccessResponse
		S201(Response.Status.CREATED.getStatusCode(), "S201", "アンケートが登録されました。");
		
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
