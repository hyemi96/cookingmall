<%@page import="my.member.MemberDTO"%>
<%@page import="my.member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

findpwdProc.jsp<br>
<% 
	request.setCharacterEncoding("UTF-8") ;
	String id = request.getParameter( "id" ) ;
	String name = request.getParameter( "name" ) ;
	String rrn1 = request.getParameter( "rrn1" ) ;
	String rrn2 = request.getParameter( "rrn2" ) ;
	
	MemberDAO mdao = MemberDAO.getInstance();
	MemberDTO dto = mdao.getMemberByIdAndName(id, name, rrn1, rrn1 ) ;
	String msg="",url="";
	if(dto == null){  
		msg = "없는 회원";
	}
	else{
		msg = dto.getPassword();
	}
	url = request.getContextPath() + "/main.jsp";
%>
<script type="text/javascript">
	alert("<%=msg%>" + "입니다.");
	location.href = "<%=url%>";
</script>	



