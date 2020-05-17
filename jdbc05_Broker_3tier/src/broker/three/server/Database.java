package broker.three.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import broker.three.exception.DuplicateSSNException;
import broker.three.exception.InvalidTransactionException;
import broker.three.exception.RecordNotFoundException;
import broker.threetier.vo.CustomerRec;
import broker.threetier.vo.ShresRec;
import broker.threetier.vo.StockRec;
import config.ServerInfo;



public class Database implements DatabaseTemplate{
	
	public Database(String serverIp) throws ClassNotFoundException{
		Class.forName(ServerInfo.DRIVE_NAME);
		System.out.println("드라이버 로딩 성공....");
	}
	//공통적인 로직....
	@Override
	public Connection getConnect() throws SQLException {
		Connection conn =DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
		System.out.println("Database Connection......");
		return conn;
	}
	@Override
	public void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if(ps!=null) ps.close();
		if(conn!=null) conn.close();		
	}
	@Override
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if(rs!=null) rs.close();
		closeAll(ps, conn);	
	}
	
	public boolean isExist(String ssn, Connection conn)throws SQLException {
		//있는지 없는지 존재유무 확인...
		
		String sql ="SELECT ssn FROM customer WHERE ssn=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1,ssn);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}	
	
	@Override
	public void addCustomer(CustomerRec cust) throws SQLException, DuplicateSSNException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			
			if(!isExist(cust.getSsn(), conn)) { //ssn이 없는거야...
				String query = "INSERT INTO customer(ssn, cust_name, address) VALUES(?,?,?)";
				ps = conn.prepareStatement(query);
			
				ps.setString(1, cust.getSsn());
				ps.setString(2, cust.getName());
				ps.setString(3, cust.getAddress());
			
				System.out.println(ps.executeUpdate()+" 명 INSERT OK....addCustomer()...");
			}else { //ssn이 있는거야...
				throw new DuplicateSSNException(cust.getName()+" 님은 이미 회원이십니다...");
			}			
			
		}finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public void deleteCustomer(String ssn) throws SQLException,RecordNotFoundException {
		Connection conn = null;
		 PreparedStatement ps = null;		 
		 try{
			 conn = getConnect();
			 if(isExist(ssn, conn)){
				 String query = "DELETE FROM customer WHERE ssn=?";			
				 ps = conn.prepareStatement(query);
				 ps.setString(1,ssn);			
				 System.out.println(ps.executeUpdate()+"명 delete success...deleteCustomer()");
			 }else{
				 throw new RecordNotFoundException("삭제할 사람 없어여..");
			 }
		 }finally{
			 closeAll(ps, conn);			 
		 }
		
	}

	@Override
	public void updateCustomer(CustomerRec cust) throws SQLException,RecordNotFoundException  {
		Connection conn = null;
		 PreparedStatement ps = null;		
		 try{
			 conn = getConnect();
			 String query ="UPDATE customer SET cust_name=?, address=? WHERE ssn=?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1, cust.getName());
			 ps.setString(2, cust.getAddress());
			 ps.setString(3, cust.getSsn());
			 int row = ps.executeUpdate();
			 
			 if(row==1) System.out.println(row+" 명 update success...");
			 else throw new RecordNotFoundException("수정할 대상이 없어여..");
		 }finally{
			 closeAll(ps, conn);
		 }
	}

	@Override
	public Vector<ShresRec> getPortfolio(String ssn) throws SQLException {
		 Connection conn = null;
		 PreparedStatement ps = null;	
		 ResultSet rs = null;
		 Vector<ShresRec> v = new Vector<ShresRec>();
		 try{
			 conn = getConnect();
			 String query = "SELECT ssn, symbol, quantity FROM shares WHERE ssn=?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1,ssn);
			 rs = ps.executeQuery();
			 while(rs.next()){
				 v.add(new ShresRec(ssn, 
						 			rs.getString("symbol"), 
						 			rs.getInt("quantity")));
			 }			 
		 }finally{
			 closeAll(rs, ps, conn);
		 }
		 return v; 
	}

	@Override
	public CustomerRec getCustomer(String ssn) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;	
		ResultSet rs = null;
		CustomerRec cust = null;
		try {
			conn = getConnect();
			String query = "SELECT * FROM customer WHERE ssn=?";
			ps = conn.prepareStatement(query);
			
			ps.setString(1, ssn);
			rs=ps.executeQuery();
			if(rs.next()) {
				cust = new CustomerRec(
									   ssn, 
									   rs.getString("cust_name"), 
									   rs.getString("address"));
			}//if
			cust.setPortfolio(getPortfolio(ssn));
		}finally {
			closeAll(rs, ps, conn);
		}
		return cust;
	}

	@Override
	public ArrayList<CustomerRec> getAllCustomers() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;	
		ResultSet rs = null;
		ArrayList<CustomerRec> list = new ArrayList<>();
		try {
			conn = getConnect();
			String query=  "SELECT * FROM customer";
			
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new CustomerRec(
						rs.getString(1), 
						rs.getString(2), 
						rs.getString(3), 
						getPortfolio(rs.getString(1))));
			}
		}finally {
			closeAll(rs, ps, conn);
		}
		return list;
	}

	@Override
	public ArrayList<StockRec> getAllStocks() throws SQLException {
		 Connection conn = null;
		 PreparedStatement ps = null;	
		 ResultSet rs = null;
		 ArrayList<StockRec> list = new ArrayList<StockRec>();
		 try{
			 conn = getConnect();
			 String query = "SELECT * FROM stock";
			 ps = conn.prepareStatement(query);
			 rs = ps.executeQuery();
			 while(rs.next()){
				 list.add(new StockRec(rs.getString(1), 
						 			   rs.getFloat(2)));
			 }
		 }finally{
			 closeAll(rs, ps, conn);
		 }
		 return list;
	}
	/*
	 누가 어떤 주식을 몇개 살것인가...
	 지금 주식이 몇개 있는가를 먼저 알아봐야 한다...이미 가지고 있냐/없냐에 해당하는 쿼리문이 된다...0/N
	 50 , 100 --- update
	 0  , 100 --- insert
	 */
	@Override
	public void buyShares(String ssn, String symbol, int quantity) throws SQLException {
		 Connection conn = null;
		 PreparedStatement ps = null;	
		 ResultSet rs = null;
		 try {
			 conn=  getConnect();
			 String query = "SELECT quantity FROM shares WHERE ssn=? AND symbol=?";
			 
			 ps = conn.prepareStatement(query);
			 ps.setString(1, ssn);
			 ps.setString(2, symbol);
			 rs = ps.executeQuery();
			 if(rs.next()) { 
				 int q = rs.getInt(1); //50..현재 가지고 있는 주식의 수량
				 int newQuantity = q + quantity; //50 + 100=150...UPDATE
				 String query1 = "UPDATE shares SET quantity=? WHERE ssn=? AND symbol=?";
				 
				 ps = conn.prepareStatement(query1);
				 ps.setInt(1, newQuantity);
				 ps.setString(2, ssn);
				 ps.setString(3, symbol);
				 
				 System.out.println(ps.executeUpdate()+" row buyShares()....OK");
			 }else { //주식이 없다...INSERT
				 String query2 ="INSERT INTO shares (ssn, symbol, quantity) VALUES(?,?,?)";
				 ps = conn.prepareStatement(query2);
				 ps.setString(1, ssn);
				 ps.setString(2, symbol);
				 ps.setInt(3, quantity);
				 System.out.println(ps.executeUpdate()+" row buyShares()....insert OK");
			 }
			 
		 }finally {
			 closeAll(rs, ps, conn);
		 }		
	}
/*
누가 어떤 주식을 몇개 팔것이가....현재 몇개를 가지고 있는가....q
 1) 100개를 현재 가지고 있다 ---- 50 ----UPDATE
 2) 100개를 현재 가지고 있다 ---- 100 ---DELETE
 3) 100개를 현재 가지고 있다 ---- 200 --- 펑
 4) 주식이 없을때      ---- 펑
 */
	@Override
	public void sellShares(String ssn, String symbol, int quantity) throws SQLException,InvalidTransactionException,RecordNotFoundException {
		 Connection conn = null;
		 PreparedStatement ps = null;	
		 ResultSet rs = null;
		 try {
			 conn = getConnect();
			 if(!isExist(ssn, conn)) {
				 throw new RecordNotFoundException("주식을 팔려는 사람이 없어요");
			 }else {
				String query = "SELECT quantity FROM shares WHERE ssn=? AND symbol=?"; 
				ps = conn.prepareStatement(query);
				ps.setString(1,ssn);
				ps.setString(2,symbol);
				rs = ps.executeQuery();
				
				rs.next();//일단 커서를 한단계 밑으로 내려서 수량을 받아올 준비를 한다.
				
				int q = rs.getInt(1); // 100개 현재 가지고 있다.
				int newQuantity = q - quantity; //팔고 남은 수량...
				
				if(q==quantity) { //delete
					String query1 = "DELETE  FROM shares WHERE ssn=? AND symbol=?"; 
					ps = conn.prepareStatement(query1);
					
					ps.setString(1,ssn);
					ps.setString(2,symbol);
					System.out.println(ps.executeUpdate()+" row SHARES DELETE...sellShares()..");							
				}else if(q>quantity) { //update
					String query2 = "UPDATE shares SET quantity=? WHERE ssn=? AND symbol=?";
					ps = conn.prepareStatement(query2);
					ps.setInt(1, newQuantity);
					ps.setString(2,ssn);
					ps.setString(3,symbol);
					System.out.println(ps.executeUpdate()+ "row SHARES update...sellShares()..");
				}else { //q<quantity...펑
					throw new InvalidTransactionException("팔려는 주식의 수량이 너무 많아요~~~^^;");
				}
				
			 }
		 }finally {
			 closeAll(rs, ps, conn);
		 }		
	}
}









