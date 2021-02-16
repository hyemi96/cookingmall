<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
   <link href="./css/bootstrap.min.css" rel="stylesheet">
    <link href="./css/kfonts2.css" rel="stylesheet">

CookingMall main.jsp [로그인 화면]<br> 
<img src="./myshop/images/letcook.jpg" width='1700px' height='400px' align='center'/> 


	<br>  
	<br>
		
<form action="loginPro.jsp" method="post">	
	<!-- 테이블 화면의 중앙에 띄우기 -->
	<!-- 테이블 배경을 이미지로 해놓기 -->
	
	<table border=1 height=250 background='./myshop/images/cook.jpg'>
	
		<tr>
		<tr>
			<td bgcolor="red" align="center">ID</td>
			<td><input type="text" name="id" value=""></td>
		</tr>
		
		<tr>
			<td bgcolor="red" align="center">PW</td>
			<td><input type="password" name="password" value=""></td>
		</tr>
		
		<tr>
		<!-- bgcolor="#FFCC00" -->
			<td colspan=2>
				<input type="submit" value="로그인">
				<input type="button" value="회원 가입" onClick="location.href='myshop/member/register.jsp'">
				<input type="button" value="아이디 찾기" onClick="location.href='findid.jsp'">
				<input type="button" value="비번 찾기" onClick="location.href='findpwd.jsp'">
			</td>
		</tr>
	</table>
	
</form>
