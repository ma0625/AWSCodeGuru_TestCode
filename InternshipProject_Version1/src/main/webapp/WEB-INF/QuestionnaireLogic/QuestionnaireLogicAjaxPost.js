window.onload = function(){
	$("#send").on("click", function(event){
		console.log("Test");
		var data = {
			sex: $("#sex").val(),
			age: $("#age").val(),
			username: $("#username").val(),
			question1: $("#Question1").val(),
			question2: $("#Question2").val(),
			question3: $("#Question3").val(),
			question4: $("#Question4").val(),
			question5: $("#Question5").val(),
			question6: $("#Question6").val()
		};

		$.ajax({
			type: "POST",
			url: "http://localhost:9080/InternshipProject_Version1/OkinawaInternshipProject/UserQuestionnaire",
			data: JSON.stringify(data),
			//headersを削除する。
			headers:{
        		'Content-Type':'application/json'
      		},
			dataType: "json",
			success: function(json_data){
					console.log(json_data);
					
			},
			error: function(){
				alert("Server Error. Please try again later.");
				console.log("失敗");
			},
			complete: function(){
				alert("アンケートを送信しました。");
			}
		});
	});
};