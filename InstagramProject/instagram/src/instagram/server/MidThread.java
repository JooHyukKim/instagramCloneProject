package instagram.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import instagram.share.Command;
import instagram.share.Result;

public class MidThread extends Thread{
	
	Socket s;
	Database db;  //서버클래스에서 받은 소켓과 db객체를 "
	ObjectInputStream ois; //소켓 통신 연결이 되면 스트림이 생성됨. 이 스트림으로 값이나 객체들을주고받아야함. 
	ObjectOutputStream oos;
	Command cmd;
	
	public MidThread(Socket s, Database db) {
		this.s = s;
		this.db = db;
		
		try {
			ois = new ObjectInputStream(s.getInputStream());//먼저 input stream.
			oos = new ObjectOutputStream(s.getOutputStream());
		}catch(IOException e) {
			
		}
		System.out.println("Midthread creating..");
	}
	
	public void run() {  //run은 Handler에서 start로 실행됨. 이제 클라이언트의 명령과 값들을 받아서 dB로 넘기고  db에서 받는 결과들을 소켓을 통해(스트림을 통해)client로 넘기면 된다. 
		System.out.println("run()..들어옴...");
		while(true) {
			try {
				cmd = (Command)ois.readObject();
				System.out.println("cmd... midThread readObject()");
			}catch(Exception e) {
				
			}
			int comm = cmd.getCommandValue();
			String[] args = cmd.getArgs();
			Result r = cmd.getResults();
			
			switch(comm) {
			case Command.ADDUSER:
				try{
					db.addUser(args[0], args[1],);
				}catch(Exception e) {
					
				}
				break;
			case Command.GETUSER:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.UPDATEUSER:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.DELETEUSER:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.AUTHENTICATEUSER:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.GETUSERSBYPERSONTAG:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.GETUSERBYPERSONTAG:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.GETFOLLOWERUSERS:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.GETFOLLOWINGUSERS:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.ADDPOST:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.GETPOST:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.UPDATEPOST:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.DELETEPOST:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.GETALLPOSTSOFPERSON:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.GETSOMEPOSTSOFOTHERPERSON:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.GETPOSTSBYHASHTAG:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.ADDCOMMENT:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.UPDATECOMMENT:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.DELETECOMMENT:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.GETCOMMENTSONPOST:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.GETALLCOMMENTS:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.GETHASHTAGSONPOST:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			case Command.GETPERSONTAGSONPOST:
				try{
					
				}catch(Exception e) {
					
				}
				break;
			}
		}
	}
}
