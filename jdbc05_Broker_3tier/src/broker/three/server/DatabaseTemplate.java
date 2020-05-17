package broker.three.server;

import java.sql.Connection;
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



public interface DatabaseTemplate {
	Connection getConnect() throws SQLException;
	void closeAll(PreparedStatement ps, Connection conn) throws SQLException;
	void closeAll(ResultSet rs,PreparedStatement ps, Connection conn) throws SQLException;
	
	//비즈니스 로직..CRUD
	void addCustomer(CustomerRec cust) throws SQLException, DuplicateSSNException;
	void deleteCustomer(String ssn) throws SQLException, DuplicateSSNException, RecordNotFoundException;
	void updateCustomer(CustomerRec cust) throws SQLException, RecordNotFoundException;
	
	Vector<ShresRec> getPortfolio(String ssn) throws SQLException, DuplicateSSNException;
	CustomerRec getCustomer(String ssn) throws SQLException, DuplicateSSNException;
	ArrayList<CustomerRec> getAllCustomers() throws SQLException;
	ArrayList<StockRec> getAllStocks() throws SQLException;
	
	//비즈니스 로직..특화된 로직
	void buyShares(String ssn, String symbol, int quantity) throws SQLException;
	void sellShares(String ssn, String symbol, int quantity) throws SQLException, InvalidTransactionException, RecordNotFoundException;
	
	
}



















