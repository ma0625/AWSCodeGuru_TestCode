package com.src.Internship.Project.Internship_Project.Entity;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestBody {
	
		//性別
		private String sex;
		//年齢
		private String age;
		//ユーザ名
		private String username;
		//アンケート項目1
		private String question1;
		//アンケート項目2
		private String question2;
		//アンケート項目3
		private String question3;
		//アンケート項目4
		private String question4;
		//アンケート項目5
		private String question5;
		//アンケート項目6
		private String question6;
		
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
		
		public String getusername() {
			return this.username;
		}
		
		public void setusername(final String username) {
			this.username = username;
		}
		
		public String getquestion1() {
			return this.question1;
		}
		
		public void setquestion1(final String question1) {
			this.question1 = question1;
		}
		
		public String getquestion2() {
			return this.question2;
		}
		
		public void setquestion2(final String question2) {
			this.question2 = question2;
		}
		
		public String getquestion3() {
			return this.question3;
		}
		
		public void setquestion3(final String question3) {
			this.question3 = question3;
		}
		
		public String getquestion4() {
			return this.question4;
		}
		
		public void setquestion4(final String question4) {
			this.question4 = question4;
		}
		
		public String getquestion5() {
			return this.question5;
		}
		
		public void setquestion5(final String question5) {
			this.question5 = question5;
		}
		
		public String getquestion6() {
			return this.question6;
		}
		
		public void setquestion6(final String question6) {
			this.question6 = question6;
		}

}
