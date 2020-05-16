package instagram.server;

import java.security.interfaces.RSAKey;
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
import instagram.vo.HashGroup;
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
		String sql ="SELECT * FROM user WHERE useId=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,userId);
		ResultSet rs = ps.executeQuery();
		System.out.println("user Exists...");
		return rs.next();
	}
	
	public boolean isExist(String userId, Post post, Connection conn)throws SQLException { // 존재 유무 확인
		String sql ="SELECT * FROM post WHERE userId=? AND postId=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,userId);
		ps.setString(2, post.getPostId());
		ResultSet rs = ps.executeQuery();
		return rs.next();
	} 

	public boolean isExist(Hashtag hashtag ,Connection conn)throws SQLException { // 존재 유무 확인
		String sql ="SELECT * FROM hashtag WHERE hashtagId=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,hashtag.getHashtagId());
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	
	@Override
	public boolean authenticateUser(String userId, String password) throws SQLException {
		boolean authentication = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			System.out.println("DB : Connected for AUTHO");
			String query ="select * FROM user WHERE userId=? AND password=?";
			ps = conn.prepareStatement(query);
			System.out.println("statement prepared....");
			ps.setString(1, userId);
			ps.setString(2, password);			
			rs = ps.executeQuery();
			System.out.println("database.authenticateUse()....checking userId and password ");
			while(rs.next()) {
				System.out.println("Correct id and password, logging in....");
				authentication = true;
			}
		}finally {
			closeAll(rs, ps, conn);
		}
		return authentication;
	}
	
	@Override
	public void addUser(User user) throws SQLException, DuplicateUserIdException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
		conn =getConnect();
		String query = "INSERT INTO user(userId, userName, password, email, gender) values(?,?,?,?,?)";
		ps = conn.prepareStatement(query);
		ps.setString(1, user.getUserId());
		ps.setString(2, user.getUserName());
		ps.setString(3, user.getPassword());
		ps.setString(4, user.getEmail());
		ps.setString(5, user.getGender());
		ps.executeUpdate();
		}finally {
			closeAll(ps, conn);
		}
	}
	
	public String getUserByEmail(String email) throws SQLException {
		Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		String id = "";
		try {
			conn = getConnect();
			String query = "SELECT userId FROM user WHERE email=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
			while(rs.next()) {
				id = rs.getString(1);
			}
		}finally {
			closeAll(rs, ps, conn);
		}
		return id;
	}
	
	public String getUserById(String id) throws SQLException {
		Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		String idResult = "";
		try {
			conn = getConnect();
			String query = "SELECT password FROM user WHERE userId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {idResult = rs.getString(1);}
		}finally {
			closeAll(rs, ps, conn);
		}
		return idResult;
	}
	
	@Override
	public boolean updateUser(User user) throws SQLException, RecordNotFoundException {
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
			
			 if (ps.executeUpdate()==1) return true;
			 else throw new RecordNotFoundException("수정할 대상이 없습니다.");
			 
		 }finally{closeAll(ps, conn);}
	}
	
	@Override
	public boolean deleteUser(String userId, String password) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;	
		try{
			conn = getConnect();
			String query = "DELETE FROM user WHERE userId = ? AND password = ?";			
			ps = conn.prepareStatement(query);
			ps.setString(1,userId);
			ps.setString(2, password);
			ps.executeUpdate();
			return true;
			}
		finally{closeAll(ps, conn);}
		
	}
	
	@Override
	public boolean addPost(String userId, Post post) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;	
		try{
			conn = getConnect();
			String query = "INSERT INTO post(userId, postId, caption, imageSrc) VALUES(?,?,?,?)";			
			ps = conn.prepareStatement(query);
			ps.setString(1,userId);
			ps.setString(2, post.getPostId());
			ps.setString(3, post.getCaption());
			ps.setString(4, post.getImageSrc());
			if (ps.executeUpdate()==1) return true;
			else throw new Exception("입력값 오류");
			}
		finally{closeAll(ps, conn);}
	}
	
	@Override
	public boolean updatePost(String userId, Post post) throws SQLException, Exception {
		Connection conn = null;
		PreparedStatement ps = null;		
		 try{
			 conn = getConnect();
			 String query ="UPDATE post SET caption =? , imageSrc =? WHERE userId =? AND postId =?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1, post.getCaption());
			 ps.setString(2, post.getImageSrc());
			 ps.setString(3, userId);
			 ps.setString(4, post.getPostId());
			 if (ps.executeUpdate()==1) return true;
			 else throw new Exception("수정할 대상이 없습니다.");	 
		 }finally{closeAll(ps, conn);}
	}
	
	public void deletePostSub(Connection conn, PreparedStatement ps, String postId) throws SQLException {
		 String query1 ="DELETE FROM hashgroup WHERE postId = ?";
		 ps = conn.prepareStatement(query1);
		 ps.setString(1, postId);
		 ps.executeUpdate();
		 String query2 ="DELETE FROM persontag WHERE postId = ?";
		 ps = conn.prepareStatement(query2);
		 ps.setString(1, postId);
		 ps.executeUpdate();
		 String query3 ="DELETE FROM comment WHERE postId = ?";
		 ps = conn.prepareStatement(query3);
		 ps.setString(1, postId);
		 ps.executeUpdate();
	}
	
	@Override
	public boolean deletePost(String userId, Post post) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;		
		 try{
			 conn = getConnect();
			 if (isExist(userId, post, conn)) {
				 deletePostSub(conn, ps, post.getPostId());
				 String query ="DELETE FROM post WHERE userId = ? AND postId = ?";
				 ps = conn.prepareStatement(query);
				 ps.setString(1, userId);
				 ps.setString(2, post.getPostId());
				 if (ps.executeUpdate()==1) return true;
				 else throw new Exception("DB_failure.deletePost() 입력값 오류");
			 }
			 // RDBMS의 특성상 가장 child한 table에서부터 지우고 올라가야되서 추가적인 execution들은 옮김.
			 	 
		 }finally{closeAll(ps, conn);}
		
		 return false;
		
	}
	
	@Override
	public boolean likePost(String userId, String postId) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			String query = "UPDATE post SET likeNum = likeNum+1 WHERE postId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, postId);
			if (ps.executeUpdate()==1) return true;
			else throw new Exception("DB_failure.deletePost() 입력값 오류");
		}finally {
			closeAll(ps, conn);
		}
		
	}
	
	@Override
	public ArrayList<Post> getPostsByHashTag(Hashtag hashtag) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Post> posts = new ArrayList<>();
		try {
			conn = getConnect();
			if (isExist(hashtag, conn)) { //isExist 오버로딩 w/ hashtag // hashgroup 테이블에서 해당 hashtag가
				String query = "SELECT * FROM hashgroup WHERE hashtagId=?"; // 달려있는 포스트 id들을 전부 가지고온다.
				ps = conn.prepareStatement(query);
				ps.setString(1, hashtag.getHashtagId());
				rs = ps.executeQuery();
				while (rs.next()) posts.add(getPost(rs.getString(2)));
				//포스트들을 찾아서 추가한다.
			}	
			else throw new Exception("DB_failure.getPostsByHashTag() 입력값 오류");
		} finally {closeAll(rs, ps, conn);}
		
		return posts;
	}
	
	
	@Override
	public Post getPost(String postId) throws Exception, SQLException {
		Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		Post post = null;
		try {
			conn = getConnect();
			String query = "SELECT * FROM post WHERE postId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, postId);
			rs = ps.executeQuery();
			while(rs.next()) {post = new Post(rs.getString(1), rs.getString(2), rs.getString(3),
					rs.getInt(4),rs.getString(6));}
		}finally {closeAll(rs, ps, conn);}
		
		return post;
	}
	
	@Override
	public ArrayList<Post> getAllPostsOfPerson(String userId) throws SQLException {
		ArrayList<Post> plist = new ArrayList<>();Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		try {
			conn = getConnect();		
			String query = "SELECT * FROM post WHERE userId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			System.out.println("getAllPostsOfPerson(String userId)....SENT TO DB Server");
			while(rs.next()) {
				plist.add(new Post(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4),rs.getString(6)));} 
			return plist;
		}finally {
			System.out.println("finally closes");
			closeAll(rs, ps, conn);}
	}
	
	@Override
	public ArrayList<Hashtag> getHashtagsOnPost(String postId) throws SQLException {
		ArrayList<Hashtag> hlist = new ArrayList<>();Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		try {
			conn = getConnect();		
			String query = "SELECT * FROM hashgroup WHERE postId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, postId);
			rs = ps.executeQuery();
			System.out.println("getHashTagsOnPost....SENT TO DB Server");
			while(rs.next()) {
				hlist.add(new Hashtag(rs.getString(3)));} 
			return hlist;
		}finally {
			System.out.println("finally closes");
			closeAll(rs, ps, conn);}
	}
	
	@Override
	public boolean addComment(String userId, String postId, String comment) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			String query = "INSERT INTO comment (userId, postId, comment) VALUES (?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			ps.setString(2, postId);
			ps.setString(3, comment);
			if (ps.executeUpdate()==1) return true;
			else throw new Exception("입력값 오류");
		}finally {closeAll(ps, conn);}
	}

	@Override
	public boolean updateComment(String userId, int commentid, String postId, String comment) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			 conn = getConnect();
			 String query ="UPDATE comment SET comment =? WHERE commentId=? AND userId =? AND postId =?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1, comment);
			 ps.setInt(2, commentid);
			 ps.setString(3, userId);
			 ps.setString(4, postId);
			 if (ps.executeUpdate()==1) return true;
			 else throw new Exception("수정할 대상이 없습니다.");
		}finally{
			closeAll(ps, conn);			 
		}
	}

	@Override
	public boolean deleteComment(String userId, String postId, int commentId) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			 conn = getConnect();
			 String query ="DELETE FROM comment WHERE userid=? AND postid=? AND commentId=?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1, userId);
			 ps.setString(2, postId);
			 ps.setInt(3, commentId);
			 if (ps.executeUpdate()==1) return true;
			 else throw new Exception("수정할 대상이 없습니다.");
		}finally{closeAll(ps, conn);}
	}
	
	@Override
	public ArrayList<Comment> getCommentsOnPost(String postId) throws SQLException {
		ArrayList<Comment> clist = new ArrayList<>();Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		try {
			conn = getConnect();		
			String query = "select * FROM comment WHERE postId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, postId);
			rs = ps.executeQuery();
			System.out.println("GETCommentsOnPost....SENT TO DB Server");
			while(rs.next()) {
				clist.add(new Comment(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5)));} 
			return clist;
		}finally {
			System.out.println("finally closes");
			closeAll(rs, ps, conn);}
	}
	

	@Override
	public boolean likeComment(String userId, int commentId) throws Exception, SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			 conn = getConnect();
			 String query ="UPDATE comment SET likeNum = likeNum+1 WHERE commentId=?";
			 ps = conn.prepareStatement(query);
			 ps.setInt(1, commentId);
			 if (ps.executeUpdate()==1) return true;
			 else throw new Exception("수정할 대상이 없습니다.");
		}finally{closeAll(ps, conn);}
	}
	
	@Override
	public ArrayList<Comment> getAllCommentsOfPerson(String userId) throws Exception, SQLException {
		ArrayList<Comment> clist = new ArrayList<>();Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		try {
			conn = getConnect();		
			String query = "SELECT * FROM comment WHERE userId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while(rs.next()) {
				clist.add(new Comment(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5)));} 
			return clist;
		}finally {
			System.out.println("finally closes");
			closeAll(rs, ps, conn);}
	}
	
	@Override
	public User getUser(String userId) throws SQLException, RecordNotFoundException {
		Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		User user = null;
		try {
			conn =getConnect();
			String query = "select * FROM user WHERE userId = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while(rs.next()) {
				user = new User(rs.getString("userId"), rs.getString("userName"), rs.getInt("followerNum"), 
						rs.getInt("followingNum"), rs.getInt("postNum"), rs.getString("email"), rs.getString("gender"));}
			}
		finally {closeAll(rs, ps, conn);}
		
		return user;
	}
	
	@Override
	public User getUser(User U) throws SQLException, RecordNotFoundException {
		Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		User user = null;
		try {
			conn =getConnect();
			String query = "select * FROM user WHERE userName = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, U.getUserName());
			rs = ps.executeQuery();
			while(rs.next()) {
				user = new User(rs.getString("userId"), rs.getString("userName"), rs.getInt("followerNum"), 
						rs.getInt("followingNum"), rs.getInt("postNum"), rs.getString("email"), rs.getString("gender"));}
			}
		finally {closeAll(rs, ps, conn);}
		
		return user;
	}
	
	@Override
	public ArrayList<User> getPersontagsOnPost(String postId) throws Exception, SQLException {
		ArrayList<User> ulist = new ArrayList<>();Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		try {
			conn = getConnect();		
			String query = "SELECT * FROM persontag WHERE postId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, postId);
			rs = ps.executeQuery();
			while(rs.next()) {
				ulist.add(getUser(rs.getString(2)));} 
			return ulist;
		}finally {
			System.out.println("finally closes");
			closeAll(rs, ps, conn);}
	}
	
	@Override
	public ArrayList<User> getFollowingUsers(String userId) throws SQLException, RecordNotFoundException {
		ArrayList<User> ulist = new ArrayList<>();Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "SELECT u.userId, u.userName, u.followerNum, u.followingNum, u.postNum, u.email, u.gender "
					+ "FROM user u "
					+ "LEFT JOIN follow f "
					+ "ON u.userId = f.userId "
					+ "WHERE f.followingId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			rs =ps.executeQuery();
			while(rs.next()) {
				ulist.add(new User(rs.getString("userId"), rs.getString("userName"),rs.getInt("followerNum"), 
						rs.getInt("followingNum"), rs.getInt("postNum"), rs.getString("email"), rs.getString("gender")));}
			return ulist;}
		finally {closeAll(rs, ps, conn);}
	}
	
	@Override
	public ArrayList<User> getFollowerUsers(String userId) throws SQLException, RecordNotFoundException {
		ArrayList<User> ulist = new ArrayList<>();Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "SELECT f.followingId "
					+ "FROM user u "
					+ "LEFT JOIN follow f "
					+ "ON u.userId = f.userId "
					+ "WHERE u.userId=?";;
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			rs =ps.executeQuery();
			while(rs.next()) {
				ulist.add(getUser(rs.getString("followingId")));}
			return ulist;}
		finally {closeAll(rs, ps, conn);}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ArrayList<Post> getPostsMain() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Post> list = new ArrayList<>();
		try {
			conn = getConnect();
			System.out.println("DB : getPost(int postNum)");
			String query = "SELECT * FROM post ORDER BY postId";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println("picked up A post for getPostMain");
//				list.add(new Post(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
			}
		}finally {
			closeAll(rs, ps, conn);
		}
		return list;
	}
//	public User getUser(String userName) throws SQLException {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		User getuser = new User();
//		try {
//			conn = getConnect();
//			String query = "SELECT * FROM user WHERE userName=?";
//			ps = conn.prepareStatement(query);
//			ps.setString(1, userName);
//			rs = ps.executeQuery();
////			while(rs.next()) {
////				System.out.println("picked up A post for getPostMain");
////				getuser = new User(rs.getString(1),rs.getString(2),rs.getString(3),
////							rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getString(7),rs.getString(8));
////			}
//		}finally {
//			closeAll(rs, ps, conn);
//		}
//		return getuser;
//	}
	

	
	
	
	
	
	
	
	
	@Override
	public ArrayList<Comment> getAllComments(String userId) throws SQLException {
		ArrayList<Comment> clist = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "select * from comment WHERE userId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			System.out.println("getAllPostsOfPerson(String userId)....SENT TO DB Server");
			while(rs.next()) {
				clist.add(new Comment(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
				
			}
		}finally {
			closeAll(rs, ps, conn);
		}
		return clist;
	}
	
	

//	@Override
//	public User getUser(String userId) throws SQLException, RecordNotFoundException {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		User user = null;
//		
//		try {
//			conn =getConnect();
//
//			if(isExist(userId, conn)) { // userId가 있다. 
//				String query = "select userId, userName, followerNum, followingNum, postNum, email from user where userId = ?";
//				ps = conn.prepareStatement(query);
//				ps.setString(1, userId);
//								
//				rs = ps.executeQuery();
//				if(rs.next()) {
//					user = new User(rs.getString("userId"), rs.getString("userName"), rs.getInt("followerNum"), 
//							rs.getInt("followingNum"), rs.getInt("postNum"), rs.getString("email"));
//				}
//			}else { // 같은 userId가 없다.
//				throw new RecordNotFoundException(userId +"는 존재하지않습니다.");
//			}
//			}finally {
//				closeAll(rs, ps, conn);
//			}
//		return user;
//	}

	

	

	

	

	

	



	

	@Override
	public Post getPost(String userId, String postId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public ArrayList<Post> getSomePostsOfOtherPerson(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	

	

	@Override
	public ArrayList<User> getUsersByPersonTag(String postId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public User getUserByPersonTag(int personTagIdx) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	

	

	


	

	

	

	

	

	

	

}
