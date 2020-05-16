package instagram.server;

import java.awt.print.Printable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

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
					boolean authentication = false;
					System.out.println("MidThread recieved AUTHENTICATEUSER and sedning to DB : "+args[0] + args[1]);
					authentication = db.authenticateUser(args[0], args[1]);
					if (authentication == true) {
						System.out.println("correct ID and Password");
						r.setStatus(0);
					} else {
						System.out.println("Wrong ID and Password");	
					}
					
				}catch(Exception e) {	
					System.out.println("MidThread AUTH USER error");
				}
				break;
				
			case Command.ADDUSER:
				try {
					System.out.println("MidThread recieved ADDUSER and sedning to DB : ");
					db.addUser(new User(args[0], args[1], args[2], args[3], args[4]));
					r.setStatus(0);
				}catch(Exception e) {	
					r.setStatus(-2);
				}
				break;
			
			case Command.GETUSERBYEMAIL:
				try {
					System.out.println("MidThread recieved GETUSERBYEMAIL");
					String id = db.getUserByEmail(args[0]);
					r.add(id);
					if (id=="") r.setStatus(-2);
					else r.setStatus(0);
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;
			
			case Command.GETUSERBYID:
				try {
					System.out.println("MidThread recieved GETUSERBYID");
					String id = db.getUserById(args[0]);
					r.add(id);
					if (id=="") r.setStatus(-2);
					else r.setStatus(0);
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;

			case Command.UPDATEUSER:
				try {
					System.out.println("MidThread recieved GETUSERBYID");
					if (db.updateUser(new User(args[0], args[1], args[2], args[3], args[4]))) r.setStatus(0);
					else r.setStatus(-2);
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;
				
			case Command.DELETEUSER:
				try {
					System.out.println("MidThread recieved DELETEUSER");
					if (db.deleteUser(args[0],args[1])) r.setStatus(0);
					else r.setStatus(-2);
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;
				
			case Command.ADDPOST:
				try {
					System.out.println("MidThread recieved ADDPOST");
					if (db.addPost(args[0], new Post(args[1], args[2], args[3]))) r.setStatus(0);
					else r.setStatus(-2);
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;
				
			case Command.UPDATEPOST:
				try {
					System.out.println("MidThread recieved UPDATEUSER");
					if (db.updatePost(args[0], new Post(args[1], args[2], args[3]))) r.setStatus(0);
					else r.setStatus(-2);
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;
				
			case Command.DELETEPOST:
				try {
					System.out.println("MidThread recieved DELETEPOST");
					if (db.deletePost(args[0], new Post(args[1]))) r.setStatus(0);
					else r.setStatus(-2);
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;	
				
			case Command.LIKEPOST:
				try {
					System.out.println("MidThread recieved LIKEPOST");
					if (db.likePost(args[0], args[1])) r.setStatus(0);
					else r.setStatus(-2);
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;	
			
			case Command.GETPOSTSBYHASHTAG:
				try {
					System.out.println("MidThread recieved GETPOSTSBYHASHTAG");
					posts = db.getPostsByHashTag(new Hashtag(args[0]));
					if (posts.size()==0) r.setStatus(-2);
					else {
						r.add(posts);
						r.setStatus(0);}
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;
			
			case Command.GETALLPOSTSOFPERSON:
				try {
					System.out.println("MidThread recieved GETALLPOSTSOFPERSON");
					posts = db.getAllPostsOfPerson(args[0]);
					if (posts.size()==0) r.setStatus(-2);
					else {
						r.add(posts);
						r.setStatus(0);}
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;
			
			case Command.GETHASHTAGSONPOST:
				try {
					System.out.println("MidThread recieved GETHASHTAGSONPOST");
					ArrayList<Hashtag> hashtags = db.getHashtagsOnPost(args[0]);
					if (hashtags.size()==0) r.setStatus(-2);
					else {
						r.add(hashtags);
						r.setStatus(0);}
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;
				
			case Command.ADDCOMMENT:
				try {
					System.out.println("MidThread recieved ADDCOMMENT");
					if (db.addComment(args[0], args[1], args[2])) r.setStatus(0);
				}catch(Exception e) {	
					r.setStatus(-2);
				}
				break;	
				
			case Command.UPDATECOMMENT:
				try {
					System.out.println("MidThread recieved UPDATECOMMENT");
					if (db.updateComment(args[0], Integer.parseInt(args[1]), args[2], args[3])) r.setStatus(0);
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;	
				
			case Command.DELETECOMMENT:
				try {
					System.out.println("MidThread recieved DELETECOMMENT");
					if (db.deleteComment(args[0], args[1], Integer.parseInt(args[2]))) r.setStatus(0);
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;	
			
			case Command.GETCOMMENTSONPOST:
				try {
					System.out.println("MidThread recieved GETCOMMENTSONPOST");
					ArrayList<Comment> comments = db.getCommentsOnPost(args[0]);
					if (comments.size()==0) r.setStatus(-2);
					else {
						r.add(comments);
						r.setStatus(0);}
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;
			
			case Command.LIKECOMMENT:
				try {
					System.out.println("MidThread recieved LIKECOMMENT");
					if (db.likeComment(args[0], Integer.parseInt(args[1]))) r.setStatus(0);
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;	
				
			case Command.GETALLCOMMENTSOFPERSON:
				try {
					System.out.println("MidThread recieved GETALLCOMMENTSOFPERSON");
					ArrayList<Comment> comments = db.getAllCommentsOfPerson(args[0]);
					if (comments.size()==0) r.setStatus(-2);
					else {
						r.add(comments);
						r.setStatus(0);}
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;
			
			case Command.GETPERSONTAGSONPOST:
				try {
					System.out.println("MidThread recieved GETPERSONTAGSONPOST");
					ArrayList<User> personTags = db.getPersontagsOnPost(args[0]);
					if (personTags.size()==0) r.setStatus(-2);
					else {
						r.add(personTags);
						r.setStatus(0);}
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;
			
			case Command.GETUSER:
				try {
					System.out.println("MidThread recieved GETUSER");
					User user = db.getUser(args[0]);
					if (user==null) r.setStatus(-2);
					else {
						r.add(user);
						r.setStatus(0);}
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;
			case Command.GETUSERBYUSERNAME:
				try {
					System.out.println("MidThread recieved GETUSERBYUSERNAME");
					User user = db.getUser(new User(args[0]));
					if (user==null) r.setStatus(-2);
					else {
						r.add(user);
						r.setStatus(0);}
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;
			
			case Command.GETFOLLOWINGUSERS:
				try {
					System.out.println("MidThread recieved GETFOLLOWINGUSER");
					ArrayList<User> users = db.getFollowingUsers(args[0]);
					if (users.size()==0) r.setStatus(-2);
					else {
						r.add(users);
						r.setStatus(0);}
				}catch(Exception e) {
					r.setStatus(-2);
				}
				break;
			
			case Command.GETFOLLOWERUSERS:
				try {
					System.out.println("MidThread recieved GETFOLLOWERUSER");
					ArrayList<User> users = db.getFollowerUsers(args[0]);
					if (users.size()==0) r.setStatus(-2);
					else {
						r.add(users);
						r.setStatus(0);}
				}catch(Exception e) {
					r.setStatus(-2);
				}
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
