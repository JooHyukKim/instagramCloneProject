package instagram.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import instagram.config.ServerInfo;
import instagram.exception.DuplicateUserIdException;
import instagram.exception.RecordNotFoundException;
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
	
	public boolean isExistUserId(String userId, Connection conn)throws SQLException { // user 존재 유무 확인
		String sql ="SELECT userId FROM customer WHERE useId=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1,userId);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	
	public boolean isExistPostId(String postId, Connection conn)throws SQLException { //post 존재 유무 확인
		String sql ="SELECT postId FROM post WHERE postId=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1,postId);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	
	public boolean isExistCommentId(String commentId, Connection conn)throws SQLException { //post 존재 유무 확인
		String sql ="SELECT commentId FROM post WHERE commentId=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1,commentId);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	

	@Override
	public void addUser(User user) throws SQLException, DuplicateUserIdException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
		conn =getConnect();
		
		if(!isExistUserId(user.getUserId(), conn)) { // userId가 없다...
			String query = "INSERT INTO user(userId, userName, password, email, gender) values(?,?,?,?,?)";
			ps = conn.prepareStatement(query);
			
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getUserName());
			ps.setString(2, user.getPassword());
			ps.setString(4, user.getEmail()); // 생성자로 받을때 두개는 null로 받음
			ps.setString(4, user.getGender());
			
			int row =ps.executeUpdate();
			System.out.println(row +"명 추가 완료");
		}else { // 같은 userId 이미 존재
			throw new DuplicateUserIdException(user.getUserId()+"는 이미 존재합니다.");
		}
		}finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public User getUser(String userId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		
		try {
			conn =getConnect();

			if(isExistUserId(user.getUserId(), conn)) { 
				String query = "select userId, userName, followerNum, followingNum, postNum, email from user where userId = ?";
				ps = conn.prepareStatement(query);
				ps.setString(1, userId);
								
				rs = ps.executeQuery();
				if(rs.next()) {
					user = new User(rs.getString("userId"), rs.getString("userName"), rs.getInt("followerNum"), 
							rs.getInt("followingNum"), rs.getInt("postNum"), rs.getString("email"));
				}
			}else { // 같은 userId가 없다.
				throw new RecordNotFoundException(userId +"는 존재하지않습니다.");
			}
			}finally {
				closeAll(rs, ps, conn);
			}
		return user;
	}

	@Override
	public void updateUser(User user) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;		
		 try{
			 conn = getConnect();
			 
			 String query ="update user set userName =?, password=?, email =?, gender =? where userId=?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1, user.getUserName());
			 ps.setString(2, user.getPassword());
			 ps.setString(3, user.getEmail());
			 ps.setString(4, user.getGender());
			 ps.setString(5, user.getUserId());
			
			 int row = ps.executeUpdate();
			 if(row==1) System.out.println(row+" 명 update success...");
			 else throw new RecordNotFoundException("수정할 대상이 없습니다.");
		 }finally{
			 closeAll(ps, conn);
		 }
	}

	@Override
	public void deleteUser(String userId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;	
		try{
			conn = getConnect();
			
			if(!isExistUserId(userId, conn)) { // userId가 없다...
			String query = "DELETE FROM user WHERE userId=?";			
			ps = conn.prepareStatement(query);
			ps.setString(1,userId);		
			
			ps.executeUpdate();
			System.out.println(userId+"가 삭제되었습니다");
			}else{
			throw new RecordNotFoundException(userId+ "가 존재하지않습니다.");
			}
		}finally{
			closeAll(ps, conn);			 
		}
	}

	@Override
	public ArrayList<User> getFollowerUsers(String userId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<User> list = new ArrayList<>();
		
		try {
			conn = getConnect();
			if(isExistUserId(userId, conn)) {
				String query = " select f.followingId, u.followerNum, u.followingNum, u.postNum, "
						+ "u.email from user u left join follow f on u.userId = f.userId "
						+ "where u.userId=? ";
				
				ps = conn.prepareStatement(query);
				ps.setString(1, userId);
				
				rs =ps.executeQuery();
				while(rs.next()) {
					list.add(new User(rs.getString("followingId"), rs.getInt("followerNum"), 
							rs.getInt("followingNum"), rs.getInt("postNum"), rs.getString("email")));
				}
			}else {
				throw new RecordNotFoundException(userId+ "가 존재하지않습니다.");
			}
		
		}finally {
			closeAll(rs, ps, conn);
		}
		return list;
	}

	@Override
	public ArrayList<User> getFollowingUsers(String userId) throws SQLException, RecordNotFoundException {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<User> list = new ArrayList<>();
		
		try {
			conn = getConnect();
			if(isExistUserId(userId, conn)) {
				String query = " select u.userId, u.followerNum, u.followingNum, u.postNum, "
						+ "u.email from user u left join follow f on u.userId = f.userId "
						+ "where f.followingId=? ";
				
				ps = conn.prepareStatement(query);
				ps.setString(1, userId);
				
				rs =ps.executeQuery();
				while(rs.next()) {
					list.add(new User(rs.getString("userId"), rs.getInt("followerNum"), 
							rs.getInt("followingNum"), rs.getInt("postNum"), rs.getString("email")));
				}
			}else {
				throw new RecordNotFoundException(userId+ "가 존재하지않습니다.");
			}
		
		}finally {
			closeAll(rs, ps, conn);
		}
		return list;
	}

	@Override
	public void addComment(String userId, String postId, Comment comment) throws SQLException, RecordNotFoundException {
		
		Connection conn = null;
		PreparedStatement ps = null;	
		
		try{
			conn = getConnect();
			
			if(isExistUserId(userId, conn)){
				String query = "insert into comment(commentId, comment, userId, postId) values(?, ?, ?, ?)";			
				ps = conn.prepareStatement(query);
				ps.setString(1, comment.getCommentId());
				ps.setString(2, comment.getComment());
				ps.setString(3, userId);
				ps.setString(4, postId);
				
				ps.executeUpdate();
				System.out.println(postId+"에" + userId +"님의 댓글이 달렸습니다.");
				}else{
				throw new RecordNotFoundException(userId+ "가 존재하지않습니다.");
				}
		}finally{
			closeAll(ps, conn);			 
		}
	}

	@Override
	public void updateComment(String userId, String postId, String commentId, String comment) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;	
		
		try{
			conn = getConnect();
			
			if(isExistCommentId(commentId, conn)){

				String query = "update comment set comment = ? where commentId = ? and postId =?";			
				ps = conn.prepareStatement(query);
				ps.setString(1, comment);
				ps.setString(2, commentId);
				ps.setString(3, postId);
				
				ps.executeUpdate();
				System.out.println("댓글이 수정되었습니다.");
				}else{
				throw new RecordNotFoundException(commentId+ " 존재하지않습니다.");
				}
		}finally{
			closeAll(ps, conn);			 
		}
	}

	@Override
	public void deleteComment(String userId, String postId, String commentId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;	
		
		try{
			conn = getConnect();
			
			if(isExistCommentId(commentId, conn)){

				String query = "delete from comment where commentId=? ";			
				ps = conn.prepareStatement(query);
				ps.setString(1, commentId);
				
				ps.executeUpdate();
				System.out.println("댓글이 삭되었습니다.");
				}else{
				throw new RecordNotFoundException(commentId+ "가 존재하지않습니다.");
				}
		}finally{
			closeAll(ps, conn);			 
		}
	}

	@Override
	public ArrayList<Comment> getCommentsOnPost(String userId ,String postId) throws SQLException, RecordNotFoundException { 
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Comment> list = new ArrayList<>();
		
		try {
			conn = getConnect();
			if(isExistPostId(postId, conn)) {
				String query = "select commentId, comment, userId, postId from comment where postId=?";
				
				ps = conn.prepareStatement(query);
				ps.setString(1, postId);
				
				rs =ps.executeQuery();
				while(rs.next()) {
					list.add(new Comment(rs.getString("commentId"), rs.getString("comment"), userId, postId));
				}
			}else {
				throw new RecordNotFoundException(postId+"가 존재하지않습니다.");
			}
		
		}finally {
			closeAll(rs, ps, conn);
		}
		return list;
	}
	
	@Override
	public ArrayList<Comment> getCommentsUserWrite(String userId, String postId) throws SQLException, RecordNotFoundException {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Comment> list = new ArrayList<>();
		
		try {
			conn = getConnect();
			if(isExistPostId(postId, conn)) {
				String query = "select commentId, comment, userId, postId from comment where userId=? and postId=?";
				
				ps = conn.prepareStatement(query);
				ps.setString(1, userId);
				ps.setString(2, postId);
				
				rs =ps.executeQuery();
				while(rs.next()) {
					list.add(new Comment(rs.getString("commentId"), rs.getString("comment"), userId, postId));
				}
			}else {
				throw new RecordNotFoundException(postId+"가 존재하지않습니다.");
			}
		
		}finally {
			closeAll(rs, ps, conn);
		}
		return list;
	}


	@Override
	public void addPost(String userId, Post post) throws SQLException {
		
		
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
