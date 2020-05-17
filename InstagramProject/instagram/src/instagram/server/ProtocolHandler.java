package instagram.server;

import java.net.ServerSocket;
import java.net.Socket;

//클라이언트에서 오는 소켓을 다루는 핸들러.
public class ProtocolHandler extends Thread{
	//서버에서 작동하니까.. 서버 소켓이랑  클라이언트로부터 받을 소켓 선언
	ServerSocket server;
	Socket s;
	//서버에서 프로세스가 돌아가니까. 스레스 선언 , 그리고 비즈니스 로직을 수행해야하므로 db선언
	MidThread mThread;
	Database db;
	
	public static final int MIDDLE_PORT = 60000;
	public ProtocolHandler(String DBserverIp) {
		//생성자에서 만드는 이유는 서버를 선언함과 동시에 
		try {
			//생성자에서 서버소켓과 비즈니스 객체를 생성. 
			server = new ServerSocket(MIDDLE_PORT);
			System.out.println("ServerSocket 생성");
			db = new Database(DBserverIp);
			
		}catch(Exception e) {
			System.out.println("서버 연결 실패");
		}
		System.out.println("start protocol handler... service port :"+MIDDLE_PORT);
	}
	
	public void run() {
		while(true) {
			try {
				s = server.accept();  //서버가 클라이언트의 소켓을 받고 스레드객체를 생성해서 클라이언트의 소켓과 연결. 이걸로 db비즈니스 객체 활용
				mThread = new MidThread(s, db);
				mThread.start(); 
			}catch(Exception e) {
				
			}
		}
	}
	public static void main(String[] args) {
		ProtocolHandler handler = new ProtocolHandler("127.0.0.1");//db서버 IP를 넣는다.서버 소켓과 비즈니스객체 생성. 
		handler.start();  //핸들러 자체가 thread...
	}
}