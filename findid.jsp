<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
findid.jsp<br>
<form action="findidProc.jsp" method="post">
<table border="1" align="center">
	<tr>
		<td bgcolor="FFBC9B" align="center">이름</td>
		<td><input type="text" name="name" value="어드민">    	
	</tr>
	<tr>
		<td bgcolor="FFBC9B" align="center">주민 등록 번호</td>
		<td>
			<input type="text" name="rrn1" maxlength="6" size="6" value="111111">
			- 
			<input type="text" name="rrn2" maxlength="7" size="7" value="2222222">
		</td>				
	</tr>
	<tr>
		<td colspan="2" bgcolor="FF5A5A" align="center">
			<input type="submit" value="아이디 찾기">
			<input type="reset" value="취소" onClick="location.href='./main.jsp'">
		</td>
	</tr>		
</table>	
</form>