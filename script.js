/**
 * script.js
 */

var isCheck = false;
var use;

$(function(){
	$('input[name="id"]').keydown(function(){
		//alert(1);
		$('#idmessage').css('display','none');
		isCheck = false; // 
	});

});

function writeSave(){ // 가입하기(submit)
	//alert(1);
	
	if($('input[name="id"]').val() == ""){
		alert("id를 입력하세요");
		$('input[name="id"]').focus();
		return false;
	}
	
	if(isCheck == false){
		alert("중복체크 하세요");
		return false;
	}
	
	if(use == "impossible"){
		alert("이미 사용중인 아이디입니다.");
		return false;
	}

}//
 
function duplicate(){ // 중복체크 
	
	isCheck = true;
	
	$.ajax({
		url: "id_check_proc.jsp",
		data:({
			userid : $('input[name="id"]').val() // userid=hong
		}),
		success:function(data){
			if($.trim(data) == 'YES'){
				$('#idmessage').html("<font color=red>사용 가능합니다.</font>");
				//$('#idmessage').show();
				$('#idmessage').css('display','inline');
				use = "possible";
			}
			else{
				$('#idmessage').html("<font color=red>이미 사용중인 아이디입니다.</font>");
				$('#idmessage').show();
				use = "impossible";
			}
		}// success
	}); // ajax
}//duplicate




