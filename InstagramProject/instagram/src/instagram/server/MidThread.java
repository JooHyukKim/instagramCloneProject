package instagram.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import instagram.exception.DuplicateUserIdException;
import instagram.exception.RecordNotFoundException;
import instagram.share.Command;
import instagram.share.Result;
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
					User user = new User(args[0], args[1], args[2], args[3], args[4]);
					db.addUser(user);
					r.setStatus(0);
			
				}catch(DuplicateUserIdException e) {
					r.setStatus(-2);
				}catch(SQLException e) {
		
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
					String userId = args[0];
					String password = args[1];
					db.authenticateUser(userId, password);
					r.setStatus(0);
				}catch(SQLException e) {
					
				}catch(Exception e) {
					r.setStatus(-1);
				}
				break;
			case Command.GETUSERSBYPERSONTAG:
				try{
					String postId = args[0];
					ArrayList<User> list = db.getUsersByPersonTag(postId);
					r.setStatus(0);
					r.add(list);
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
					String userId = args[0];
					String caption = args[1];
					String imageSrc = args[2];
					String loginUserId = args[3];
					db.addPost(userId, new Post(caption, imageSrc),loginUserId);
					r.setStatus(0);
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
					String userId = args[0];
					ArrayList<Post> list = db.getAllPostsOfPerson(userId);
					r.setStatus(0);
					r.add(list);
				}catch(Exception e) {
					
				}
				break;
			case Command.GETSOMEPOSTSOFFOLLOWINGPERSON:
				try{
					String userId = args[0];
					ArrayList<Post> list = db.getSomePostsOfFollowingPerson(userId);
					r.setStatus(0);
					r.add(list);
					} catch (Exception e) {
				}
				break;
			case Command.GETPOSTSBYHASHTAG:
				try{
					String hashtagId = args[0];
					ArrayList<Post> list = db.getPostsByHashTag(hashtagId);
					r.setStatus(0);
					r.add(list);
					System.out.println(list+ "MidThread 2222222222222222222222222");
				}catch(Exception e) {
					
				}
				break;
			case Command.ADDCOMMENT:
				try{
					String userId = args[0];
					String postId = args[1];
					String comment = args[2];
					db.addComment(userId, postId, comment);
					r.setStatus(0);
					
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
			case Command.CHECKUSERID:
				try {
					String userId = args[0];
					db.checkUserId(userId);
					r.setStatus(0);
					
				}catch(DuplicateUserIdException e) {
					System.out.println("이미 존재합니다.");
					r.setStatus(-2);
				}catch(SQLException e) {
					
				}
			case Command.LIKEPOST:
				try{
					String postId = args[0];
					db.likePost(postId);
					r.setStatus(0);
				}catch(SQLException e) {
					
				}
				break;
			}
			//다시 protocol로 cmd를 던진다. 
			try {
				oos.writeObject(cmd);
			}catch(Exception e) {
			}
		}
	}
}
