<%@page import="my.member.MemberDTO"%>
<%@page import="my.member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

findidProc.jsp <br>
<% 
	request.setCharacterEncoding("UTF-8") ;
	String name = request.getParameter( "name" ) ;
	String rrn1 = request.getParameter( "rrn1" ) ;
	String rrn2 = request.getParameter( "rrn2" ) ;
	
	MemberDAO mdao = MemberDAO.getInstance();
	MemberDTO dto = mdao.getMemberByRrn(name,rrn1,rrn2);
	String msg="",url="";
	if(dto == null){
		msg = "없는 회원";
	}
	else{
		msg = dto.getId();
	}
	url = request.getContextPath() + "/main.jsp";
%>
<script type="text/javascript">
	alert("<%=msg%>" + "입니다.");
	location.href = "<%=url%>";
</script>



<!-- 12시10분 -->




