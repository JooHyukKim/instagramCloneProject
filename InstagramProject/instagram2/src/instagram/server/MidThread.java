package instagram.server;

import java.awt.print.Printable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import instagram.exception.DuplicateRercordException;
import instagram.exception.RecordNotFoundException;
import instagram.share.Command;
import instagram.share.Result;
import instagram.vo.Comment;
import instagram.vo.Hashtag;
import instagram.vo.PersonTag;
import instagram.vo.Post;
import instagram.vo.User;

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
	
	@SuppressWarnings("unchecked")
	public void run() {  //run은 Handler에서 start로 실행됨. 이제 클라이언트의 명령과 값들을 받아서 dB로 넘기고  db에서 받는 결과들을 소켓을 통해(스트림을 통해)client로 넘기면 된다. 
		System.out.println("run()..들어옴...");
		while(true) {
			try {
				cmd = (Command)ois.readObject();
				System.out.println("cmd... midThread readObject()");
			}catch(Exception e) {
				
			}
			ArrayList<Post> posts = new ArrayList<>();
			int comm = cmd.getCommandValue();
			String[] args = cmd.getArgs();
			Result r = cmd.getResults();
			
			switch(comm) {
			
			case Command.AUTHENTICATEUSER:
				try {
					db.authenticateUser(args[0], args[1]);
					r.setStatus(0);}
				catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.AUTHENTICATEUSER ::: RecordNotFound");
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					r.setStatus(-1); //SQL오류 CLIENT
					e.printStackTrace();}//SQL오류 SERVER}
				break;
				
			case Command.ADDUSER:
				try {
					db.addUser(new User(args[0], args[1], args[2], args[3], args[4]));
					r.setStatus(0);} //있다 client/server
				catch(DuplicateRercordException e) {
					r.setStatus(-3); // 없다 client
					System.out.println("DB.UPDATEPOST ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
			
			case Command.GETUSERBYEMAIL:
				try {
					r.add(db.getUserByEmail(args[0]));
					r.setStatus(0);} //있다 client/server
				catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.GETUSERBYEMAIL ::: RecordNotFound");
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					r.setStatus(-1); //SQL오류 CLIENT
					e.printStackTrace();}//SQL오류 SERVER}
				break;
			
			case Command.GETUSERBYID:
				try {
					r.add(db.getUserById(args[0]));
					r.setStatus(0);} //있다 client/server}
				catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.GETUSERBYID ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;

			case Command.UPDATEUSER:
				try {
					db.updateUser(new User(args[0], args[1], args[2], args[3], args[4])); 
					r.setStatus(0);}
				catch(RecordNotFoundException e) {
					r.setStatus(-3); // 없다 client
					System.out.println("DB.GETUSERBYID ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
				
			case Command.DELETEUSER:
				try {
					db.deleteUser(args[0],args[1]); 
					r.setStatus(0);
				}catch(RecordNotFoundException e) {
					r.setStatus(-3); // 없다 client
					System.out.println("DB.GETUSERBYID ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
				
			case Command.ADDPOST:
				try {
					db.addPost(args[0], new Post(args[1], args[2], args[3]));
					r.setStatus(0);
				}catch(DuplicateRercordException e) {
					r.setStatus(-3); // 없다 client
					System.out.println("DB.UPDATEPOST ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
				
			case Command.UPDATEPOST:
				try {
					db.updatePost(args[0], new Post(args[1], args[2], args[3])); 
					r.setStatus(0);
				}catch(RecordNotFoundException e) {
					r.setStatus(-3); // 없다 client
					System.out.println("DB.UPDATEPOST ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
				
			case Command.DELETEPOST:
				try {
					db.deletePost(args[0], new Post(args[1]));
					r.setStatus(0);
				}catch(RecordNotFoundException e) {
					r.setStatus(-3); // 없다 client
					System.out.println("DB.DELETEPOST ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;	
				
			case Command.LIKEPOST:
				try {
					db.likePost(args[0], args[1]);
					r.setStatus(0);
				}catch(RecordNotFoundException e) {
					r.setStatus(-3); // 없다 client
					System.out.println("DB.LIKEPOST ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;		
			
			case Command.GETPOSTSBYHASHTAG:
				try {
					r.add(db.getPostsByHashTag(new Hashtag(args[0])));
					r.setStatus(0);}
				catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.GETPOSTSBYHASHTAG ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
			
			case Command.GETALLPOSTSOFPERSON:
				try {
					r.add(db.getAllPostsOfPerson(args[0]));
					r.setStatus(0);}
				catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.GETALLPOSTSOFPERSON ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
			
			case Command.GETHASHTAGSONPOST:
				try {
					r.add(db.getHashtagsOnPost(args[0]));
					r.setStatus(0);}
				catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.GETHASHTAGSONPOST ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
				
			case Command.ADDCOMMENT:
				try {
					db.addComment(args[0], args[1], args[2]);
					r.setStatus(0);
				}catch(DuplicateRercordException e) {
					r.setStatus(-3); // client
					System.out.println("DB.ADDCOMMENT ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;	
				
			case Command.UPDATECOMMENT:
				try {
					db.updateComment(args[0], Integer.parseInt(args[1]), args[2], args[3]); 
					r.setStatus(0);
				}catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.UPDATECOMMENT ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;	
				
			case Command.DELETECOMMENT:
				try {
					db.deleteComment(args[0], args[1], Integer.parseInt(args[2]));
					r.setStatus(0);
				}catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.DELETECOMMENT ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;	
			
			case Command.GETCOMMENTSONPOST:
				try {
					r.add(db.getCommentsOnPost(args[0]));
					r.setStatus(0);}
				catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.GETCOMMENTSONPOST ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
			
			case Command.LIKECOMMENT:
				try {
					db.likeComment(args[0], Integer.parseInt(args[1])); 
					r.setStatus(0);
				}catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.LIKECOMMENT ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
				
			case Command.GETALLCOMMENTSOFPERSON:
				try {
					r.add(db.getAllCommentsOfPerson(args[0]));
					r.setStatus(0);
				}catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.GETALLCOMMENTSOFPERSON ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
			
			case Command.GETPERSONTAGSONPOST:
				try {
					r.add(db.getPersontagsOnPost(args[0]));
					r.setStatus(0);
				}catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.GETPERSONTAGSONPOST ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
			
			case Command.GETUSER:
				try {
					r.add(db.getUser(args[0]));
					r.setStatus(0);
				}catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.GETUSER ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
			case Command.GETUSERBYUSERNAME:
				try {
					r.add(db.getUser(new User(args[0])));
					r.setStatus(0);
				}catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.GETUSERBYUSERNAME ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
			
			case Command.GETFOLLOWINGUSERS:
				try {
					r.add(db.getFollowingUsers(args[0]));
					r.setStatus(0);
				}catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.GETFOLLOWINGUSERS ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
			
			case Command.GETFOLLOWERUSERS:
				try {
					r.add(db.getFollowerUsers(args[0]));
					r.setStatus(0);}
				catch(RecordNotFoundException e) {
					r.setStatus(-3); // client
					System.out.println("DB.GETFOLLOWERUSERS ::: RecordNotFound"); //서버 트래킹용.
					e.printStackTrace();} //없다 SERVER
				catch(SQLException e) {
					e.printStackTrace();//SQL오류 SERVER
					r.setStatus(-1);} //SQL오류 CLIENT
				break;
			
			

			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			try {
				oos.writeObject(cmd);
				System.out.println("response made...");
			}catch(Exception e) {
				
			}
		}
	}
}
