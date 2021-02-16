package my.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberDAO {
	
	private Connection conn = null;
	
	private static MemberDAO instance = null;
	
	private MemberDAO() throws Exception{
		System.out.println("MemberDAO()");
		
		Context initContext = new InitialContext();
		Context envContext = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/OracleDB"); 
		conn = ds.getConnection();
		System.out.println("conn:" + conn);
	}
	
	public static MemberDAO getInstance() throws Exception {
		if(instance == null) {
			instance = new MemberDAO();
		}
		System.out.println("instance:" + instance);
		return instance;
	}//
	
	public int insertData(MemberDTO member) throws SQLException{
		
		PreparedStatement pstmt = null ;
		
		String sql = 
				"insert into members(no, name, id, password, rrn1, rrn2, email, hp1, hp2, hp3, joindate) ";
		sql += "values(memseq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " ;
		
		int cnt = -1 ;
		pstmt = conn.prepareStatement(sql) ;
		
		pstmt.setString(1, member.getName()) ;
		pstmt.setString(2, member.getId()) ;
		pstmt.setString(3, member.getPassword()) ;
		pstmt.setString(4, member.getRrn1()) ; 
		pstmt.setString(5, member.getRrn2()) ; 
		pstmt.setString(6, member.getEmail()) ; 
		pstmt.setString(7, member.getHp1()) ;
		pstmt.setString(8, member.getHp2()) ;
		pstmt.setString(9, member.getHp3()) ;
		pstmt.setString(10, member.getJoindate()) ; 
		
		cnt = pstmt.executeUpdate() ;

		//pstmt.close();
		
		return cnt;
	}//
	public MemberDTO getMemberByRrn(String name,String rrn1,String rrn2) throws SQLException{
		
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		MemberDTO member = null;
		String sql = "select * from members where name=? and rrn1=? and rrn2=?"; 
		pstmt = conn.prepareStatement(sql) ;
		pstmt.setString(1, name) ;
		pstmt.setString(2, rrn1) ;
		pstmt.setString(3, rrn2) ;
		
		rs = pstmt.executeQuery() ;
		if( rs.next() ){
			member = getMemberDTO(rs);
		}
		
		System.out.println();
		return member;
	}//
	
	public MemberDTO getMemberByIdAndName(String id, String name, String rrn1, String rrn2) throws SQLException{
		
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		MemberDTO member = null;
		String sql = "select * from members where name=? and rrn1=? and rrn2=?"; 
		pstmt = conn.prepareStatement(sql) ;
		pstmt.setString(1, name) ;
		pstmt.setString(2, rrn1) ;
		pstmt.setString(3, rrn2) ;
		
		rs = pstmt.executeQuery() ;
		//System.out.println(rs.next());
		if( rs.next() ){
			member = getMemberDTO(rs);
		}
		
		System.out.println();
		return member;
	}
	
	public MemberDTO getMemberDTO(ResultSet rs) throws SQLException{
		
		MemberDTO member = new MemberDTO() ;
		
		member.setNo(rs.getInt("no")) ;
		member.setId(rs.getString("id"));
		member.setPassword(rs.getString("password"));
		member.setEmail(rs.getString("email")) ;
		member.setHp1(rs.getString("hp1")) ;
		member.setHp2(rs.getString("hp2"));
		member.setHp3(rs.getString("hp3"));
		member.setName(rs.getString("name")) ;
		member.setRrn1(rs.getString("rrn1")) ;
		member.setRrn2(rs.getString("rrn2")) ;
		member.setJoindate(rs.getString("joindate")) ;
		
		return member;
	}//
	
	public boolean searchId(String userid) throws SQLException{
		System.out.println("userid:" + userid);
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		boolean flag = false;
		String sql = "select id from members where id=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,userid);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			flag = true;
		}
		
		return flag;
	}//
	
	public MemberDTO getMemberInfo(String id,String pw) throws SQLException{
		
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String sql = "select * from members where id=? and password=? "; 
		pstmt = conn.prepareStatement(sql) ;
		pstmt.setString(1, id) ;
		pstmt.setString(2, pw) ;
		
		rs = pstmt.executeQuery() ;
		
		MemberDTO member = null;
		
		if(rs.next()) {
			member = getMemberDTO(rs);
		}
		return member;
	}//
}




















