package my.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.oreilly.servlet.MultipartRequest;

public class ProductDao {

	private static ProductDao instance;
	private Connection conn = null;

	private ProductDao() { 
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/OracleDB");
			conn = ds.getConnection() ;

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ProductDao getInstance() {
		if(instance == null){
			instance = new ProductDao();
		}

		return instance;
	}  //  

	public int insertProduct( MultipartRequest mr) throws SQLException{

		PreparedStatement pstmt = null ;	
		String sql = "insert into product values(catprod.nextval,?,?,?,?,?,?,?,?,?,?)";
		pstmt=conn.prepareStatement(sql);
		String pname = mr.getParameter("pname");

		String pcategory_fk = mr.getParameter("pcategory_fk"); // pcategory_fk:카테고리코드
		pcategory_fk += mr.getParameter("pcode"); // pcode:상품코드
		// pcategory_fk = 카테고리코드 + 상품코드
		
		String pcompany = mr.getParameter("pcompany");
		String pimage = mr.getFilesystemName("pimage");
		String pqty = mr.getParameter("pqty");
		String price = mr.getParameter("price");
		String pspec = mr.getParameter("pspec");
		String pcontents = mr.getParameter("pcontents");
		String point = mr.getParameter("point");


		pstmt.setString(1, pname);
		pstmt.setString(2, pcategory_fk);
		pstmt.setString(3, pcompany);
		pstmt.setString(4, pimage);
		pstmt.setString(5, pqty);
		pstmt.setString(6, price);
		pstmt.setString(7, pspec);
		pstmt.setString(8, pcontents);
		pstmt.setString(9, point);

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(d);
		pstmt.setString(10, today);

		int cnt = pstmt.executeUpdate();

		pstmt.close();

		return cnt;
	}//

	public ArrayList<ProductBean> getAllProducts() throws SQLException{

		ArrayList<ProductBean> arr = new ArrayList<ProductBean>();

		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String sql = "select * from product order by pnum";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		while(rs.next()){
			int pnum=rs.getInt(1);
			String pname=rs.getString(2);
			String pcategory_fk=rs.getString(3);
			String pcompany=rs.getString(4);
			String pimage=rs.getString(5);
			int pqty=rs.getInt(6);
			int price=rs.getInt(7);
			String pspec=rs.getString(8);
			String pcontents=rs.getString(9); 
			int point=rs.getInt(10);
			String pinputdate = rs.getString(11);
			ProductBean pbean = new ProductBean(pnum,pname,pcategory_fk,
					pcompany,pimage,pqty,price,pspec,pcontents,point,pinputdate,0,0,0);

			arr.add(pbean);
		}
		pstmt.close();
		return arr;
	}//

	// 상품 수정, 상세보기, 장바구니담기(재고수량), 장바구니담기
	public ProductBean selectByPnum(String pnum) throws SQLException{
		ProductBean pbean = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String sql = "select * from product where pnum=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, pnum);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			int pnum2=rs.getInt(1);
			String pname=rs.getString(2);
			String pcategory_fk=rs.getString(3);
			String pcompany=rs.getString(4);
			String pimage=rs.getString(5);
			int pqty=rs.getInt(6);
			int price=rs.getInt(7);
			String pspec=rs.getString(8);
			String pcontents=rs.getString(9); 
			int point=rs.getInt(10);
			String pinputdate = rs.getString(11);
			pbean = new ProductBean(pnum2,pname,pcategory_fk,
					pcompany,pimage,pqty,price,pspec,pcontents,point,pinputdate,0,0,0);

		}
		pstmt.close();
		return pbean;
	}//

	public int updateProduct(MultipartRequest mr) /* throws SQLException */{

		String pcategory_fk = mr.getParameter("pcategory_fk");
		//System.out.println("pcategory_fk: "+pcategory_fk);

		String pname = mr.getParameter("pname");
		//System.out.println("pname: "+pname);

		String pimage2 = mr.getParameter("pimage2"); //Koala.jpg, null
		//System.out.println("pimage2: "+pimage2);

		String pimage = mr.getOriginalFileName("pimage"); //Pe.jpg, null
		//System.out.println("pimage: "+pimage);

		if(pimage == null) {
			if(pimage2 != null) { // 기존:O, 새:X
				pimage = pimage2;
			}
		}

		String pcompany = mr.getParameter("pcompany");
		String pqty=mr.getParameter("pqty");
		String price=mr.getParameter("price");
		String pspec=mr.getParameter("pspec");
		String pcontents=mr.getParameter("pcontents");
		String point=mr.getParameter("point");
		String pnum=mr.getParameter("pnum");

		PreparedStatement pstmt = null ;
		int cnt = -1;

		String sql ="update product set pname=?, pcategory_fk=?, " +
				"pcompany=?, pimage=?, pqty=?, price=?, pspec=?,pcontents=?,"+
				"point=? where pnum=?";
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1,pname);
			pstmt.setString(2,pcategory_fk);
			pstmt.setString(3,pcompany);
			pstmt.setString(4,pimage);
			pstmt.setInt(5, Integer.parseInt(pqty));
			pstmt.setInt(6, Integer.parseInt(price));
			pstmt.setString(7,pspec);
			pstmt.setString(8,pcontents);
			pstmt.setInt(9, Integer.parseInt(point));
			pstmt.setInt(10, Integer.parseInt(pnum));

			cnt = pstmt.executeUpdate();
		}catch(SQLException e) {

		}finally {
			try {
				pstmt.close() ;
			}catch(SQLException e) {

			}
		}

		return cnt;
	}//
	
	public int deleteProduct(String pnum) throws SQLException{
		PreparedStatement pstmt = null ;
		int cnt = -1 ;
		String sql = "delete from product where pnum=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, pnum);
		cnt = pstmt.executeUpdate();
		pstmt.close() ;
		return cnt;
	}//
	
	public ArrayList<ProductBean> selectByPspec(String pspec2) throws SQLException{
		// HIT
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		ArrayList<ProductBean> lists = new ArrayList<ProductBean>();
		String sql = "SELECT * FROM product WHERE pspec=?";
		pstmt=conn.prepareStatement(sql);		
		pstmt.setString(1, pspec2);	
		rs=pstmt.executeQuery();
		
		while(rs.next()){
			int pnum=rs.getInt(1);
			String pname=rs.getString(2);
			String pcategory_fk=rs.getString(3);
			String pcompany=rs.getString(4);
			String pimage=rs.getString(5);
			int pqty=rs.getInt(6);
			int price=rs.getInt(7);
			String pspec=rs.getString(8);
			String pcontents=rs.getString(9); 
			int point=rs.getInt(10);
			String pinputdate = rs.getString(11);
			ProductBean pbean = new ProductBean(pnum,pname,pcategory_fk,
					pcompany,pimage,pqty,price,pspec,pcontents,point,pinputdate,0,0,0);

			lists.add(pbean);
		}
		return lists;
	}//
	
	public ArrayList<ProductBean>  selectByCategory(String code) throws SQLException {
		
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		ArrayList<ProductBean> lists = new ArrayList<ProductBean>();
		
		String sql = "SELECT * FROM product WHERE pcategory_fk like ?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, code+"%");
		rs=pstmt.executeQuery();
		while(rs.next()) {
			int pnum=rs.getInt(1);
			String pname=rs.getString(2);
			String pcategory_fk=rs.getString(3);
			String pcompany=rs.getString(4);
			String pimage=rs.getString(5);
			int pqty=rs.getInt(6);
			int price=rs.getInt(7);
			String pspec=rs.getString(8);
			String pcontents=rs.getString(9); 
			int point=rs.getInt(10);
			String pinputdate = rs.getString(11);
			ProductBean pbean = new ProductBean(pnum,pname,pcategory_fk,
					pcompany,pimage,pqty,price,pspec,pcontents,point,pinputdate,0,0,0);

			lists.add(pbean);
		}
		rs.close() ;
		pstmt.close() ;
		
		return lists;
	}
}









