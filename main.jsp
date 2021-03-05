<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


<style>


html { 
  background: url(main5.png) no-repeat center center fixed; 
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;


}
</style>


    
    
	<form action="loginPro.jsp" method="post">	

		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
       <table align="center" >  
		<tr>
			<td bgcolor="FF5A5A" align="center">ID</td>
			<td align="center"><input type="text" name="id" value="" size="25"></td>
		</tr>
		<tr>
			<td bgcolor="FF5A5A" align="center">PW</td>
			<td align="center"><input type="password" name="password" value="" size="25"></td>
		</tr>
		<tr>
			<td colspan="2" align="center"> 
				<input type="submit" class="btn" value="로그인">
				<input type="button" class="btn" value="회원 가입" onClick="location.href='myshop/member/register.jsp'">
				<input type="button" class="btn" value="아이디 찾기" onClick="location.href='findid.jsp'">
				<input type="button" class="btn" value="비번 찾기" onClick="location.href='findpwd.jsp'">
			</td>
		</tr>
	</table>
	
</form>




</html>
 
