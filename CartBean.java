package my.shop.mall;

import java.sql.SQLException;
import java.util.Vector;

import my.shop.ProductBean;
import my.shop.ProductDao;

public class CartBean { 
	
	private Vector<ProductBean> clist; // 장바구니 
	
	public CartBean() {
		System.out.println("CartBean 생성자");
		clist = new Vector<ProductBean>();
	}// 
	
	public void addProduct(String pnum, String oqty) throws SQLException {
		ProductDao pdao = ProductDao.getInstance();
		ProductBean pbean = pdao.selectByPnum(pnum);
		
		int pnum_new = Integer.parseInt(pnum);
		int oqty_new = Integer.parseInt(oqty);
		
		for(int i=0;i<clist.size();i++) {
			
			int cPnum = clist.get(i).getPnum();
			System.out.println("cPnum:"+cPnum);
			if(cPnum == pnum_new) { // 
				System.out.println("번호 같음");
				int cPqty = clist.get(i).getPqty();
				clist.get(i).setPqty(cPqty + oqty_new);
				clist.get(i).setTotalPoint(clist.get(i).getPoint()*(cPqty + oqty_new));
				clist.get(i).setTotalPrice(clist.get(i).getPrice()*(cPqty + oqty_new));
				return;
			}
			else {
				System.out.println("번호 안같음");
			}
		}
		
		// 새상품
		
		pbean.setPqty(oqty_new);
		int totalPrice = pbean.getPrice() * oqty_new;
		int totalPoint = pbean.getPoint() * oqty_new;
		pbean.setTotalPrice(totalPrice);
		pbean.setTotalPoint(totalPoint);
		clist.add(pbean);
		
		System.out.println("clist 갯수:" + clist.size());
		
	} //
	
	public Vector<ProductBean> getAllProducts(){
		return clist;
	}
	
	public void setEdit(String pnum,String oqty){ // 15,1
		//15 3=>1
		//1 2 
		//7 3
		int oqty_int = Integer.parseInt(oqty);
		for( ProductBean pbean : clist) {
			if(pbean.getPnum() == Integer.parseInt(pnum)) {
				pbean.setPqty(oqty_int);
				int totalPrice = pbean.getPrice() * pbean.getPqty();
				int totalPoint = pbean.getPoint() * pbean.getPqty();
				pbean.setTotalPrice(totalPrice);
				pbean.setTotalPoint(totalPoint);
			}
			
		}
	}//
	
	public void	removeProduct(String pnum){
		int pnum_int = Integer.parseInt(pnum);
		for(ProductBean pbean : clist) {
			if(pbean.getPnum() == pnum_int) {
				clist.remove(pbean);
				break;
			}
		}
	}//
	
	public void removeAllProduct() {
		clist.removeAllElements();
	}
}








