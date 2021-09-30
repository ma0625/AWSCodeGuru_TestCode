package com.src.Internship.Project.Internship_Project.Entity;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.HeaderParam;

@RequestScoped
public class Request {
	
		//性別
		@HeaderParam("sex")
		private String sex;
		
		//年齢
		@HeaderParam("age")
		private String age;
		
		//ユーザー名
		@HeaderParam("username")
		private String username;
		
		//アンケート項目1
		@HeaderParam("Question1")
		private String question1;
		
		//アンケート項目2
		@HeaderParam("Question2")
		private String question2;
		
		//アンケート項目3
		@HeaderParam("Question3")
		private String question3;
		
		//アンケート項目4
		@HeaderParam("Question4")
		private String question4;
		
		//アンケート項目5
		@HeaderParam("Question5")
		private String question5;
		
		//アンケート項目6
		@HeaderParam("Question6")
		private String question6;
		
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
		
		public void setusername(String username) {
			this.username = username;
		}
		
		public String getusername() {
			return this.username;
		}
		
		public void setquestion1(String question1) {
			this.question1 = question1;
		}
		
		public String getquestion1() {
			return this.question1;
		}
		
		public void setquestion2(String question2) {
			this.question2 = question2;
		}
		
		public String getquestion2() {
			return this.question2;
		}
		
		public void setquestion3(String question3) {
			this.question3 = question3;
		}
		
		public String getquestion3() {
			return this.question3;
		}
		
		public void setquestion4(String question4) {
			this.question4 = question4;
		}
		
		public String getquestion4() {
			return this.question4;
		}
		
		public void setquestion5(String question5) {
			this.question5 = question5;
		}
		
		public String getquestion5() {
			return this.question5;
		}
		
		public void setquestion6(String question6) {
			this.question6 = question6;
		}
		
		public String getquestion6() {
			return this.question6;
		}

}
