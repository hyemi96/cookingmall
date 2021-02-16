package my.shop.mall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import my.shop.ProductBean;

public class OrdersDao {
	
	Connection conn;
	public OrdersDao() {
		System.out.println("OrdersDao() 생성자");
		
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/OracleDB"); 
			conn = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//
	
	public int insertOrders(Vector<ProductBean> cv, int mno) throws SQLException{
		
		PreparedStatement ps = null;
		int cnt = 0;
		for(int i=0;i<cv.size();i++) {
			String sql = "insert into orders(orderId,memno,pnum,qty,amount) " + 
							"values(orderseq.nextval,?,?,?,?)";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1,mno);
			ps.setInt(2,cv.get(i).getPnum());
			ps.setInt(3,cv.get(i).getPqty());
			ps.setInt(4,cv.get(i).getTotalPrice()); // 100*2=200
			
			cnt += ps.executeUpdate();
		}
		ps.close();
		return cnt;
	}//
	
	public Vector<OrderBean> getOrderList(String memid) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<OrderBean> lists = new Vector<OrderBean>();
		
		String sql = "select m.name mname, m.id mid, p.pname, o.qty, o.amount " + 
				"from (members m join orders o on m.no = o.memno ) join product p " + 
				"on o.pnum = p.pnum " + 
				"where m.id = ?";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, memid);
		rs = ps.executeQuery();
		
		while(rs.next()) {
			OrderBean ob = new OrderBean();
			ob.setMname(rs.getString("mname"));
			ob.setMid(rs.getString("mid"));
			ob.setPname(rs.getString("pname"));
			ob.setQty(rs.getInt("qty"));
			ob.setAmount(rs.getInt("amount"));
			lists.add(ob);
		}
		return lists;
	}//
}

/*
col mname for a10
col mid for a8

select m.name mname, m.id mid, o.pnum, o.qty, o.amount
from members m join orders o 
on m.no = o.memno;
		
MNAME      MID            PNUM        QTY     AMOUNT
---------- -------- ---------- ---------- ----------
홍길동  hong             16          3      12000
홍길동     hong              1          1        100
홍길동     hong              1          1        100
홍길동     hong              3          1        100
김연아     kim              15          1        100
김연아     kim              17          1        100


select m.name mname, m.id mid, p.pname, o.qty, o.amount
from (members m join orders o on m.no = o.memno ) join product p 
on o.pnum = p.pnum;
		
MNAME      MID      PNAME                 QTY     AMOUNT
---------- -------- -------------- ---------- ----------
홍길동     hong     양복                    1        100
홍길동     hong     양복                    3        300
홍길동     hong     캐주얼                  1        100
홍길동     hong     남성캐주얼              1        100
홍길동     hong     남자시계                3      12000
홍길동     hong     분홍원피스              1        100
김연아     kim      이름                    1        100
김연아     kim      민트색아동복            1        100

select m.name mname, m.id mid, p.pname, o.qty, o.amount
from (members m join orders o on m.no = o.memno ) join product p 
on o.pnum = p.pnum
where m.id = 'hong';
*/

