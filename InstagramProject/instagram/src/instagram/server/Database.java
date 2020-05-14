package instagram.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import instagram.config.ServerInfo;
import instagram.exception.DuplicateUserIdException;
import instagram.vo.Comment;
import instagram.vo.Hashtag;
import instagram.vo.PersonTag;
import instagram.vo.Post;
import instagram.vo.User;

public class Database implements DatabaseTemplate{

	public Database(String serverIp) throws ClassNotFoundException{
		Class.forName(ServerInfo.DRIVER_NAME);
		System.out.println("드라이버 로딩 성공....");
	}
	
	@Override
	public Connection getConnect() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
		System.out.println("Database Connection");
		return conn;
	}

	@Override
	public void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if(ps!=null) ps.close();
		if(conn!=null) conn.close();		
	}

	@Override
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if(rs!=null) rs.close();
		closeAll(ps, conn);			
	}
	
	public boolean isExist(String userId, Connection conn)throws SQLException { // 존재 유무 확인
		String sql ="SELECT ssn FROM customer WHERE useId=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1,userId);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}

	@Override
	public void addUser(User user) throws SQLException, DuplicateUserIdException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
		conn =getConnect();
		
		if(!isExist(user.getUserId(), conn)) { // userId가 없다...
			String query = "INSERT INTO user(userId, userName, password, email, gender) values(?,?,?,?,?)";
			ps = conn.prepareStatement(query);
			
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getUserName());
			ps.setString(2, user.getPassword());
			ps.setString(4, user.getEmail()); // 생성자로 받을때 두개는 null로 받음
			ps.setString(4, user.getGender());
		}else { // 같은 userId 이미 존재
			throw new DuplicateUserIdException(user.getUserId()+"는 이미 존재합니다.");
		}
		}finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public User getUser(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(User user) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String userId) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<User> getFollowerUsers(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> getFollowingUsers(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addComment(String userId, String postId, Comment comment) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateComment(String userId, String postId, Comment comment) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteComment(String userId, String postId, String commentId) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Comment> getCommentsOnPost(String postId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Comment> getAllComments(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPost(String userId, Post post) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Post getPost(String userId, String postId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Post> getAllPostsOfPerson(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Post> getSomePostsOfOtherPerson(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePost(String userId, Post post) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePost(String userId, String postId) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<User> getUsersByPersonTag(String postId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PersonTag> getPersontagsOnPost(String userId, String postId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByPersonTag(int personTagIdx) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Hashtag> getHashtagsOnPost(String postId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Post> getPostsByHashTag(String hashtagId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean authenticateUser(String userId, String password) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	

}
