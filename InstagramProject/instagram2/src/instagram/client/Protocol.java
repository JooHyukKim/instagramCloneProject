package instagram.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

import instagram.exception.DuplicateRercordException;
import instagram.exception.RecordNotFoundException;
import instagram.share.Command;
import instagram.vo.Comment;
import instagram.vo.HashGroup;
import instagram.vo.Hashtag;
import instagram.vo.PersonTag;
import instagram.vo.Post;
import instagram.vo.User;


public class Protocol {
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Command cmd;
	public static final int MIDDLE_PORT = 60000;	
	
	public Protocol(String serverIp){
		
		try {
			s = new Socket(serverIp, MIDDLE_PORT);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			System.out.println("Protocol sent... Connected");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		}catch(IOException e) {
			System.out.println("Client Protocol writeCommand....error"+e);
		}
	}
	
	public int getResponse() { //readObject() + getResults().getStatus()--> status code
		try {
			cmd =(Command)ois.readObject();			
		}catch(Exception e){
			System.out.println("client getResponse()....error"+e);	
		}
		int status=cmd.getResults().getStatus();
		return status;
	}
	
	public void authenticateUser(String userId, String password) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.AUTHENTICATEUSER);
		String[ ] str = {userId, password};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) throw new SQLException("서버처리오류");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else System.out.println("로그인되었습니다.");;	
	}
	
	public void addUser(User user) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.ADDUSER);
		String[ ] str = {user.getUserId(), user.getUserName(), user.getPassword(), user.getEmail(), user.getGender()};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) throw new SQLException("서버처리오류");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else System.out.println(user.getUserId()+"/ 회원가입 성공");
	}	
	
	public void getUserByEmail(String Email) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.GETUSERBYEMAIL);
		String[ ] str = {Email};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) throw new SQLException("서버처리오류");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else System.out.println("아이디는 "+(String) cmd.getResults().get(0) +" 입니다.");	
	}
	
	public void getUserById(String id) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.GETUSERBYID);
		String[ ] str = {id};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) throw new SQLException("서버처리오류");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else System.out.println("등록된 이메일로 비밀번호가 전송되었습니다."+ (String) cmd.getResults().get(0));
	}
	
	public void updateUser(User user) throws SQLException, RecordNotFoundException, DuplicateRercordException {
		cmd = new Command(Command.UPDATEUSER);
		String[ ] str = {user.getUserId(), user.getUserName(), user.getPassword(), user.getEmail(), user.getGender()};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) throw new SQLException("서버처리오류");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else System.out.println(user.getUserId()+"/ 정보변경 성공!");
	}	
	
	public void deleteUser(User user) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.DELETEUSER);
		String[ ] str = {user.getUserId(),user.getPassword()};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) throw new SQLException("서버처리오류");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else System.out.println(user.getUserId()+"/ 탈퇴 성공....ㅜㅜ 아디오스 미 아미고");
	}	
	
	public void addPost(String userId, Post post) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.ADDPOST);
		String[ ] str = {userId, post.getPostId(), post.getCaption(), post.getImageSrc()};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) throw new SQLException("서버처리오류");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else System.out.println("포스팅 성공!");
	}
	
	public void updatePost(String userId, Post post) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.UPDATEPOST);
		String[ ] str = {userId, post.getPostId(), post.getCaption(), post.getImageSrc()};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) throw new SQLException("서버처리오류");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else System.out.println("게시물 수정 성공!!!");
	}
	public void deletePost(String userId, Post post) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.DELETEPOST);
		String[ ] str = {userId, post.getPostId()};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) throw new SQLException("서버처리오류");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else System.out.println("게시물 삭제 성공!!!");
	}
	
	public void likePost(String userId, String postId) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.LIKEPOST);
		String[ ] str = {userId, postId};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) throw new SQLException("서버처리오류");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else System.out.println("좋아요가 눌렸습니다ㅏㅏㅏㅏ!!!");
	}
	
	public ArrayList<Post> getAllPostsOfPerson(String userId) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.GETALLPOSTSOFPERSON);
		String[ ] str = {userId};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) throw new SQLException("서버처리오류");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else return (ArrayList<Post>) cmd.getResults().get(0);
	}
	
	public ArrayList<Hashtag> getHashtagsOnPost(String postId) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.GETHASHTAGSONPOST);
		String[ ] str = {postId};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) throw new SQLException("서버처리오류");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else return (ArrayList<Hashtag>) cmd.getResults().get(0);
	}
	
	public ArrayList<Post> getPostsByHashTag(Hashtag hashtag) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.GETPOSTSBYHASHTAG);
		String[ ] str = {hashtag.getHashtagId()};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) throw new SQLException("서버처리오류");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else return (ArrayList<Post>) cmd.getResults().get(0);
	}
	
	public void addComment(String userId, String postId, String comment) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.ADDCOMMENT);
		String[ ] str = {userId, postId, comment};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) throw new SQLException("서버처리오류");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else System.out.println("댓글을 달았습니다.");
	}

	public void updateComment(String userId, int commentid, String postId, String comment) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.UPDATECOMMENT);
		String[ ] str = {userId, String.valueOf(commentid), postId, comment};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==0) System.out.println("댓글 수정 성공!!!");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else throw new SQLException("서버처리오류");
	}
	
	public void deleteComment(String userId, String postId, int commentId) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.DELETECOMMENT);
		String[ ] str = {userId, postId, String.valueOf(commentId)};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==0) throw new SQLException("댓글 삭제 성공!!!");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else throw new SQLException("서버처리오류");
	}
	public ArrayList<Comment> getCommentsOnPost(String postId) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.GETCOMMENTSONPOST);
		String[ ] str = {postId};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==0) return (ArrayList<Comment>) cmd.getResults().get(0);
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else throw new SQLException("서버처리오류");
	}
	public void likeComment(String userId, int commentId) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.LIKECOMMENT);
		String[ ] str = {userId, String.valueOf(commentId)};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==0) System.out.println("좋아요가 눌렸습니다ㅏㅏㅏㅏ!!!");
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else throw new SQLException("서버처리오류");
	}

	public ArrayList<Comment> getAllCommentsOfPerson(String userId) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.GETALLCOMMENTSOFPERSON);
		String[ ] str = {userId};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==0) return (ArrayList<Comment>) cmd.getResults().get(0);
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else throw new SQLException("서버처리오류");
	}

	public ArrayList<User> getPersontagsOnPost(String postId) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.GETPERSONTAGSONPOST);
		String[ ] str = {postId};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==0) return (ArrayList<User>) cmd.getResults().get(0);
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else throw new SQLException("서버처리오류");
		
	}

	public User getUser(String userId) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.GETUSER);
		String[ ] str = {userId};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==0) return (User) cmd.getResults().get(0);
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else throw new SQLException("서버처리오류");
		
	}
	public User getUser(User user) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.GETUSERBYUSERNAME);
		String[ ] str = {user.getUserName()};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==0) return (User) cmd.getResults().get(0);
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else throw new SQLException("서버처리오류");
		
	}

	public ArrayList<User> getFollowingUsers(String userId) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.GETFOLLOWINGUSERS);
		String[ ] str = {userId};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==-1) return (ArrayList<User>) cmd.getResults().get(0);
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else throw new SQLException("서버처리오류");
	}

	public ArrayList<User> getFollowerUsers(String userId) throws SQLException,DuplicateRercordException,RecordNotFoundException{
		cmd = new Command(Command.GETFOLLOWERUSERS);
		String[ ] str = {userId};
		cmd.setArgs(str);
		writeCommand(cmd);
		int status=getResponse();
		if(status==0) return (ArrayList<User>) cmd.getResults().get(0);
		else if(status==-2) throw new DuplicateRercordException("이미 존재하는 정보 입니다.");
		else if(status==-3) throw new RecordNotFoundException("정보를 찾을수없습니다.");
		else throw new SQLException("서버처리오류");
	}











}//클래스
