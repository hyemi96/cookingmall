<%@page import="my.member.MemberDTO"%>
<%@page import="my.member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

loginPro.jsp<br>

<%
	request.setCharacterEncoding("UTF-8") ;
	String id = request.getParameter("id") ;
	String password = request.getParameter("password") ; 
	
	MemberDAO mdao = MemberDAO.getInstance();
	MemberDTO member = mdao.getMemberInfo(id,password);
	
	String viewPage = null;
	
	if(member != null){
		String mid = member.getId();
		session.setAttribute("mid", mid); // mid:hong

		
		if(mid.equals("admin")){ // 관리자
			viewPage = request.getContextPath()+"/myshop/admin/main.jsp";
		}
		else{ // 일반 사용자
			viewPage = request.getContextPath()+"/myshop/display/mall.jsp";
		}
	}
	else{
%>
		<script type="text/javascript">
			alert("가입하지 않은 회원입니다.");
		</script>
<%		
		viewPage = "main.jsp";
	}//
%>
<script type="text/javascript">
	location.href = "<%=viewPage%>";
</script>



