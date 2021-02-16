package my.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CategoryDao {

	private static CategoryDao instance;
	private Connection conn = null;
	
	private CategoryDao(){
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/OracleDB"); 
			conn = ds.getConnection();
			System.out.println("conn:" + conn);
			
		} catch (NamingException e) {
			e.printStackTrace();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //
	
	public static CategoryDao getInstance() {
		
		if(instance == null) {
			instance = new CategoryDao();
		}
		return instance;
	} //
	
	public int insertCategory(CategoryBean cbean){
		PreparedStatement pstmt = null ;
		String sql="insert into category values(catseq.nextval,?,?)";
		int cnt = -1 ;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, cbean.getCode());
			pstmt.setString(2, cbean.getCname());
			cnt = pstmt.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			} catch(SQLException e) {
				
			}
		}
		return cnt;
	}//
	
	public ArrayList<CategoryBean> getAllCategory() throws SQLException{
		
		ArrayList<CategoryBean> lists = new ArrayList<CategoryBean>();
		
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String sql = "select * from category order by cnum asc";
		pstmt=conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		while(rs.next()){
			CategoryBean cbean = new CategoryBean();
			cbean.setCnum(rs.getInt("cnum"));
			cbean.setCode(rs.getString("code")) ;
			cbean.setCname(rs.getString("cname")) ;
			lists.add(cbean);			
		}
		
		rs.close();
		pstmt.close();
		
		return lists;
	}//
	
	public int deleteCategory(String cnum){  
		PreparedStatement pstmt = null ;
		int cnt = -1 ;
		String sql = "delete from category where cnum=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(cnum));
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
			}catch(SQLException e) {
				
			}
		} 
		return cnt;
	}
}










