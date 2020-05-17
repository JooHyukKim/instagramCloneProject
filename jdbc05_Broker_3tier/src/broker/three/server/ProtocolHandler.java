package broker.three.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import broker.three.client.Protocol;

/*
 * server 사이드의 Process Class
 * 서버 소켓을 가지면서 클라이언트의 접속을 기다린다.
 * 해당 클래스는 무한 로핑을 돌면서 계속적으로 서버에 접속하는 클라이언트를 accept 하게 될 것이다.
 * 리스너 쓰레드 
 */
public class ProtocolHandler extends Thread {
	// 필드 선언
	ServerSocket server;
	Socket s;
	JuryThread jury;
	Database db;
	static final int MIDDLE_PORT = 60000;
	
	public ProtocolHandler(String serverIP) {
		//Server 소켓 생성, Database 생성
		try {
			server = new ServerSocket(MIDDLE_PORT);
			System.out.println("ServerSocket 생성");
			db = new Database(serverIP);
		} catch (Exception e) {
			System.out.println("서버 연결 실패");
		}
		System.out.println("Start Protocoll Handler...Service port ::" + MIDDLE_PORT);
		
	}
	
	public void run() {
		// 무한 루핑 돌면서 클라이언트와 접속하면 받아서 소켓을 리턴 -> jury에게 리턴
		while(true) {
			try {
				s = server.accept(); // 클라이언트의 소켓
				jury = new JuryThread(s, db);
				jury.start();
				
			}catch(Exception e){
				
			}
		}
	}
	
	public static void main(String[] args) {
		ProtocolHandler handler = new ProtocolHandler("127.0.0.1"); //서버측 IP
		handler.start();
		
	}
}
