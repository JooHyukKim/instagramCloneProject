package broker.three.server;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import broker.three.exception.DuplicateSSNException;
import broker.three.exception.InvalidTransactionException;
import broker.three.exception.RecordNotFoundException;
import broker.three.shares.Command;
import broker.three.shares.Result;
import broker.threetier.vo.CustomerRec;
import broker.threetier.vo.StockRec;

public class JuryThread extends Thread {
	
	Socket s;
	Database db;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	Command cmd;
	
	public JuryThread(Socket s, Database db) {
		this.s = s;
		this.db =db;
		
		try {
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
		}catch (Exception e) {
			
		}
		System.out.println("Jury Creating");
	}

	public void run() {
		System.out.println("run() 들어옴");
		while(true) {
			try {
				cmd = (Command)ois.readObject();
				System.out.println("CMD jury readObject");
			}catch (Exception e) {
				
			}
			//2. 도시락 깐다. (Data Unpack)...getter
			int comm = cmd.getCommandValue(); // 10~90 까지의 숫자가 튀어나온다. 
			String[ ] args= cmd.getArgs();
			Result r = cmd.getResults();
			
			switch(comm) { // 실수형은 못온다. 
			case Command.BUYSHARES :
				try {
				String ssn1 = args[0];
				String symbol =args[1];
				int quantity = Integer.parseInt(args[2]);
				db.buyShares(ssn1, symbol, quantity);
				r.setStatus(0);
				}catch(Exception e) {
				}
				break;
			case Command.SELLSHARES : 
				try {
					String ssn1 = args[0];
					String symbol =args[1];
					int quantity = Integer.parseInt(args[2]);
					db.sellShares(ssn1, symbol, quantity);
					r.setStatus(0);
				}catch(RecordNotFoundException e) {
					System.out.println("주식을 살 고객이 없어요");
					r.setStatus(-1);
				}catch(InvalidTransactionException e) {
					System.out.println("팔려는 주식의 수량이 너무 많아요");
					r.setStatus(-3);
				}catch(Exception e) {
				}
				break;
			case Command.GETALLSTOCKS :
				try {
					ArrayList<StockRec> list = db.getAllStocks();
					r.setStatus(0);
					r.add(list);
				} catch (Exception e2) {
					
				}
				break;
			case Command.GETALLCUSTOMERS :
				try {
					ArrayList<CustomerRec> list = db.getAllCustomers();
					r.setStatus(0);
					r.add(list);
				} catch (Exception e2) {
					
				}
				break;
			case Command.GETCUSTOMER :
				try {
					String ssn = args[0];
					CustomerRec cust = db.getCustomer(ssn);
					r.setStatus(0);
					r.add(cust);
				} catch (SQLException e1) {
				}		
				break;
			case Command.ADDCUSTOMER :
				try {
				CustomerRec cr = new CustomerRec(args[0], args[1], args[2]);
				db.addCustomer(cr);
				r.setStatus(0);
				}catch(DuplicateSSNException e) {
					r.setStatus(-2);
				}
				catch(Exception e) {
				}
				break;
			case Command.DELETECUSTOMER :
				try {
					String ssn1 = args[0];
					db.deleteCustomer(ssn1);
					r.setStatus(0);
				} catch (SQLException e) {
					
				} catch (RecordNotFoundException e) {
					r.setStatus(-1);
				}
				break;
			case Command.UPDATECUSTOMER :
				try {
					CustomerRec cust = new CustomerRec(args[0], args[1], args[2]);
					db.updateCustomer(cust);
					r.setStatus(0);
				} catch (SQLException e) {
					
				} catch (RecordNotFoundException e) {
					r.setStatus(-1);
				}
				break;
			}
			
			//다시 protocol로 cmd를 던진다. 
			try {
				oos.writeObject(cmd);
			}catch(Exception e) {
			}
		}
	}//run
}//class
