package broker.three.client;
/*
 * Protocol +Jury
 * 서버 --Protocol Handler
 * 클라이언트 -- broker
 */
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import broker.three.exception.DuplicateSSNException;
import broker.three.exception.InvalidTransactionException;
import broker.three.exception.RecordNotFoundException;
import broker.three.shares.Command;
import broker.threetier.vo.CustomerRec;
import broker.threetier.vo.ShresRec;
import broker.threetier.vo.StockRec;

/*
 * Database 와 비슷하면서 다른 놈
 * 클라이언트 사이드의 통신의 대표 주자  Socket -> 스트림 
 */
public class Protocol { 
	
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Command cmd;
	static final int MIDDLE_PORT = 60000;
	
	public Protocol(String serverIP) throws Exception {
		s = new Socket(serverIP, MIDDLE_PORT);
		oos = new ObjectOutputStream(s.getOutputStream()); //나가는거 먼저 만들고
		ois = new ObjectInputStream(s.getInputStream()); // 받는거 만들기 
		}
	
	public void close(){
		try {
			s.close();
		}catch (Exception e) {
			System.out.println("Protocool.close()"+ e);
		}
	}
	//중복되는 로직 작성
	public void writeCommand(Command cmd) {
		try {
			oos.writeObject(cmd);
			System.out.println("Client writeCOmmand..end");
			}catch(IOException e) {
				System.out.println("Client Protocol Writecommand eroor" +e);
			}
	}
	public int getResponse() { // readObject() + getResults().getStatus() --> status code
		try {
			cmd = (Command)ois.readObject();
			System.out.println("client ReadObject()...and");
		} catch (Exception e) {
			System.out.println("client getResponse...error"+e);
		}
		// 0, DuplicateE(-2), RecordNot(-1), InvalidTE(-3)
		int status = cmd.getResults().getStatus(); 
		return status;
	}
	
	public void addCustomer(CustomerRec cust) throws DuplicateSSNException{
		/*
		 * 1. 도시락 싼다.
		 * 2. 도시락 던진다....JURY가 받는다. 
		 * -----------------------------
		 * 3. JURY가 던진 도시락을 받는다. 
		 * 4. status가 양수면 정상, 음수면 오류 터트리기
		 */
		//도시락 싼다. 
		cmd = new Command(Command.ADDCUSTOMER);
		String[ ] str = { cust.getSsn(),cust.getName(), cust.getAddress()};
		cmd.setArgs(str);
		// 도시락 날린다. 
		writeCommand(cmd);
		// JURY가 보낸 데이타 받는다...응답 결과
		int status = getResponse();
		if(status==-2) throw new DuplicateSSNException("그런 회원 이미 있어요");
	}
	
	public void deleteCustomer(String ssn) throws RecordNotFoundException {
		cmd = new Command(Command.DELETECUSTOMER);
		String [ ] str = {ssn};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status = getResponse();
		if(status==-1) throw new RecordNotFoundException("그런 회원 없습니다.");
	}
	
	public void updateCustomer(CustomerRec cust) throws RecordNotFoundException {
		cmd = new Command(Command.DELETECUSTOMER);
		String [ ] str = {cust.getSsn(), cust.getName(), cust.getAddress()};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status = getResponse();
		if(status==-1) throw new RecordNotFoundException("그런 회원 없습니다.");
	}

	public CustomerRec getCustomer(String ssn) throws RecordNotFoundException {
		CustomerRec cr =null;
		cmd = new Command(Command.GETCUSTOMER);
		String [ ] str = {ssn};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status = getResponse();
		if(status == -1) throw new RecordNotFoundException("그런 회원 없습니다.");
		cr = (CustomerRec) cmd.getResults().get(0);
		return cr;
	}
	
	public ArrayList<CustomerRec> getAllCustomers() throws RecordNotFoundException {
		ArrayList<CustomerRec> list = new  ArrayList<CustomerRec>();
		cmd = new Command(Command.GETALLCUSTOMERS);
		writeCommand(cmd);
		
		int status = getResponse(); // 여기서 cmd는 jury가 던진 cmd가 됨
		if(status == -1) throw new RecordNotFoundException("회원이 아무도 없습니다.");
		list = (ArrayList<CustomerRec>) cmd.getResults().get(0);
		return list;
				
	}
	
	public ArrayList<StockRec> getAllStocks() throws RecordNotFoundException {
		ArrayList<StockRec> list = new  ArrayList<StockRec>();
		cmd = new Command(Command.GETALLSTOCKS);
		writeCommand(cmd);
		
		int status = getResponse(); // 여기서 cmd는 jury가 던진 cmd가 됨
		if(status == -1) throw new RecordNotFoundException("회원이 아무도 없습니다.");
		list = (ArrayList<StockRec>) cmd.getResults().get(0);
		return list;
		}
	
	public void buyShares(String ssn, String symbol, int quantity) throws RecordNotFoundException {
		cmd = new Command(Command.BUYSHARES);
		String[ ] str = {ssn, symbol, String.valueOf(quantity)}; //valueOf : int를 String으로 바꿔줌
		cmd.setArgs(str);
		// 도시락 날린다. 
		writeCommand(cmd);
		// JURY가 보낸 데이타 받는다...응답 결과
		int status = getResponse();
		if(status ==-1) {
			throw new RecordNotFoundException("회원이 존재하지 않습니다. ");
		}
		
	}
	public void sellShares(String ssn, String symbol, int quantity) throws RecordNotFoundException, InvalidTransactionException {
		cmd = new Command(Command.SELLSHARES);
		String[ ] str = {ssn, symbol, String.valueOf(quantity)}; //valueOf : int를 String으로 바꿔줌
		cmd.setArgs(str);
		// 도시락 날린다. 
		writeCommand(cmd);
		// JURY가 보낸 데이타 받는다...응답 결과
		int status = getResponse();
		if(status ==-1) {
			throw new RecordNotFoundException("주식을 팔 사람이 없습니다. ");
		}
		if(status ==-3) {
			throw new InvalidTransactionException("팔려는 주식의 양이 가지고있는것 보다 많습니다.");
		}
	}
}
