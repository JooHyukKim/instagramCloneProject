package instagram.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import instagram.share.Command;


public class Protocol {
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Command cmd;
	public static final int MIDDLE_PORT = 60000;	
	
	public Protocol(String serverIp) throws Exception{
		s = new Socket(serverIp, MIDDLE_PORT);
		oos = new ObjectOutputStream(s.getOutputStream());
		ois = new ObjectInputStream(s.getInputStream());		
	}
	
	public void close() {
		try {
			s.close();
		}catch(Exception e) {
			System.out.println("Protocol.close()...."+e);
		}
	}
	
	public void writeCommand(Command cmd) {
		try {
			oos.writeObject(cmd);
			System.out.println("Client writeCommand....end..");
		}catch(IOException e) {
			System.out.println("Client Protocol writeCommand....error"+e);
		}
	}
	
	public int getResponse() { //readObject() + getResults().getStatus()--> status code
		try {
			cmd =(Command)ois.readObject();
			System.out.println("client readObject()....end...");			
		}catch(Exception e){
			System.out.println("client getResponse()....error"+e);	
		}
		//0, DuplicateE(-2), RecordNE(-1), InvalidTE(-3)
		int status=cmd.getResults().getStatus();
		return status;
	}
	
}//클래스
