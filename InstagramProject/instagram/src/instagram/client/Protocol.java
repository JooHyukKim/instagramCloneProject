package instagram.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import instagram.share.Command;
import instagram.vo.Comment;
import instagram.vo.Post;
import instagram.vo.User;


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
	
	public boolean addUser(User user) throws Exception {
		//도시락싸기
		cmd = new Command(Command.ADDUSER);
		String[ ] str = {user.getUserId(), user.getUserName(), user.getPassword(), user.getEmail(), user.getGender()};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("존재하는 아이디입니다.");
		else
			return false;
	}
	
	public boolean getUserByEmail(String Email) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETUSER);
		String[ ] str = {Email};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("회원이 존재하지 않습니당.");
		else return true;
	}
	
	public boolean getUser(String id) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETUSER);
		String[ ] str = {id};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception(id + "의 회원은 존재하지 않습니당.");
		else return true;
	}
	
	public ArrayList<User> getUserByName(String name) throws Exception {
		return null;
	}
	
	public ArrayList<User> getUserByTag(String tag) throws Exception {
		return null;
	}
	
	public void updateUser(User user) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETUSER);
		String[ ] str = {user.getUserId(), user.getUserName(), user.getPassword(), user.getEmail(), user.getGender()};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception(user.getUserId() + "의 회원은 존재하지 않습니당.");
	}
	
	public void deleteUser(String userId) throws Exception {
		//도시락싸기
		cmd = new Command(Command.DELETEUSER);
		String[ ] str = {userId};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception(userId  + "의 회원은 존재하지 않습니당.");
	}
	
	public boolean authenticateUser(String userId, String password) throws Exception {
		//도시락싸기
		cmd = new Command(Command.AUTHENTICATEUSER);
		String[ ] str = {userId, password};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) { 
			throw new Exception("아이디 또는 비밀번호가 맞지 않습니다.");
		}
		else 
			return false;
	}
	
	public void getFollowerUsers(String userId) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETFOLLOWERUSERS);
		String[ ] str = {userId};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("GET FOLLOWER ERROR니다.");
	}
	
	public void getFollowingUsers(String userId) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETFOLLOWINGUSERS);
		String[ ] str = {userId};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("GET FOLLOWING USERS ERORR 맞지 않습니다.");
	}
	
	public void addComment(String userId, String postId, Comment comment) throws Exception {
		//도시락싸기
		cmd = new Command(Command.ADDCOMMENT);
		String[ ] str = {userId, postId, comment.getComment()};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("ADD COMMENT ERROR");
	}
	public void updateComment(String userId, String postId, Comment comment) throws Exception {
		//도시락싸기
		cmd = new Command(Command.UPDATECOMMENT);
		String[ ] str = {userId, postId, comment.getComment()};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("UPDATE COMMENT ERROR");
	}
	public void deleteComment(String userId, String postId, String commentId) throws Exception {
		//도시락싸기
		cmd = new Command(Command.DELETECOMMENT);
		String[ ] str = {userId, postId, commentId};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("DELETECOMMENT ERROR");
	}
	public void getCommentsOnPost(String postId) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETCOMMENTSONPOST);
		String[ ] str = {postId};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("GETCOMMENTSONPOST ERROR 포스트아이디가 맞지 않습니다.");
	}
	public void getAllComments(String userId) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETALLCOMMENTS);
		String[ ] str = {userId};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("Get all comments ERROR 포스트아이디가 맞지 않습니다.");
	}
	
	public void addPost(String userId, Post post) throws Exception {
		//도시락싸기
		cmd = new Command(Command.ADDPOST);
		String[ ] str = {userId, post.getCaption(), post.getImageSrc()};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status =getResponse();
		if(status==-2) throw new Exception("ADDPOST ERROR");
	}
	public void getPost(String userId, String postId) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETPOST);
		String[ ] str = {userId, postId};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("GETPOST ERROR");
	}
	
	public void getAllPostsOfPerson(String userId) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETALLPOSTSOFPERSON);
		String[ ] str = {userId};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("Get all posts of person ERROR");
	}
	
	public void getSomePostsOfOtherPerson(String userId) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETSOMEPOSTSOFOTHERPERSON);
		String[ ] str = {userId};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("get some posts of other person ERROR");
	}
	
	public void updatePost(String userId, Post post) throws Exception {
		//도시락싸기
		cmd = new Command(Command.UPDATEPOST);
		String[ ] str = {userId, post.getPostId(), post.getCaption(), post.getImageSrc()};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("UPDATE POST ERROR");
	}
	
	public void deletePost(String userId, String postId) throws Exception {
		//도시락싸기
		cmd = new Command(Command.DELETEPOST);
		String[ ] str = {userId, postId};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("DELETE POST ERROR");
	}
	
	public void getUsersByPersonTag(String postId) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETUSERSBYPERSONTAG);
		String[ ] str = {postId};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("GETUSERSBY PERSON TAG STRING ERROR");
	}
	
	public void getPersontagsOnPost(String userId, String postId) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETPERSONTAGSONPOST);
		String[ ] str = {userId, postId};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("get person tags on post ERROR");
	}
	
	public void getUserByPersonTag(int personTagIdx) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETUSERSBYPERSONTAG);
		String[ ] str = {String.valueOf(personTagIdx)};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("GetUsersByPersonTag INT ERROR");
	}
	
	public void getHashtagsOnPost(String postId) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETHASHTAGSONPOST);
		String[ ] str = {postId};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("GetHashTagsOn post ERROR");
	}
	
	public void getPostsByHashTag(String hashtagId) throws Exception {
		//도시락싸기
		cmd = new Command(Command.GETPOSTSBYHASHTAG);
		String[ ] str = {hashtagId};
		cmd.setArgs(str);
		//도시락보내기
		writeCommand(cmd);
		//도시락
		int status=getResponse();
		if(status==-2) throw new Exception("GETPOSTSBYHASHTAG ERROR");
	}
	
	
}//클래스
