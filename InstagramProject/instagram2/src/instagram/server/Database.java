package instagram.server;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import instagram.config.ServerInfo;
import instagram.exception.DuplicateRercordException;
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
	
	// 오버라이드 메소드 1
	public boolean isExist(String userId, Connection conn)throws SQLException { // 존재 유무 확인
		String sql ="SELECT * FROM user WHERE useId=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,userId);
		ResultSet rs = ps.executeQuery();
		System.out.println("user Exists...");
		return rs.next();
	}
	// 오버라이드 메소드 2 
	public boolean isExist(String userId, Post post, Connection conn)throws SQLException { // 존재 유무 확인
		String sql ="SELECT * FROM post WHERE userId=? AND postId=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,userId);
		ps.setString(2, post.getPostId());
		ResultSet rs = ps.executeQuery();
		return rs.next();
	} 
	
	// 오버라이드 메소드 3
	public boolean isExist(Hashtag hashtag ,Connection conn)throws SQLException { // 존재 유무 확인
		String sql ="SELECT * FROM hashtag WHERE hashtagId=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,hashtag.getHashtagId());
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	
	@Override
	public void authenticateUser(String userId, String password) throws SQLException, RecordNotFoundException {
		Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		boolean authentication = false;
		try {
			conn = getConnect();
			String query ="select * FROM user WHERE userId=? AND password=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			ps.setString(2, password);			
			rs = ps.executeQuery();
			if(rs.next()) {return;}
			else throw new RecordNotFoundException("존재하지 않는 ID 또는 PASSWORD 입니다.");	 
		}finally {closeAll(rs, ps, conn);}
		
	}
	
	@Override
	public void addUser(User user) throws SQLException, DuplicateRercordException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
		conn =getConnect();
		conn.setAutoCommit(false);
		String query = "INSERT INTO user(userId, userName, password, email, gender) values(?,?,?,?,?)";
		ps = conn.prepareStatement(query);
		ps.setString(1, user.getUserId());
		ps.setString(2, user.getUserName());
		ps.setString(3, user.getPassword());
		ps.setString(4, user.getEmail());
		ps.setString(5, user.getGender());
		if (ps.executeUpdate()==1){
			 conn.commit();
			 return;}
		else throw new DuplicateRercordException("이미 존재하는 ID 또는 PASSWORD 입니다.");	 
		}
		catch (Exception e) {
			conn.rollback();
			throw new SQLException();}
		finally{
			 closeAll(ps, conn);
			 conn.setAutoCommit(true);}
	}
	
	public String getUserByEmail(String email) throws RecordNotFoundException, SQLException {
		Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		String id = "";
		try {
			conn = getConnect();
			String query = "SELECT userId FROM user WHERE email=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
			while(rs.next()) {id = rs.getString(1);} //있다 
			if (id=="") {throw new RecordNotFoundException("존재하지 않는 EMAIL 입니다.");} //없다
		}finally {closeAll(rs, ps, conn);}
		return id;
	}
	
	public String getUserById(String id) throws RecordNotFoundException, SQLException  {
		Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		String idResult = "";
		try {
			conn = getConnect();
			String query = "SELECT password FROM user WHERE userId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {idResult = rs.getString(1);} //있다
			if (id=="") {throw new RecordNotFoundException("존재하지 않는 ID 입니다.");} //없다
		}finally {closeAll(rs, ps, conn);}
		return idResult;
	}
	
	@Override
	public void updateUser(User user) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;		
		 try{
			 conn = getConnect();
			 conn.setAutoCommit(false);
			 String query ="update user set userName =?, password=?, email =?, gender =? where userId=?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1, user.getUserName());
			 ps.setString(2, user.getPassword());
			 ps.setString(3, user.getEmail());
			 ps.setString(4, user.getGender());
			 ps.setString(5, user.getUserId());
			
			 if (ps.executeUpdate()==1){
				 conn.commit();
				 return;}
			 else throw new RecordNotFoundException("수정할 대상이 없습니다.");
			 
		 }catch (Exception e) {
			conn.rollback();
			throw new SQLException();}
		 finally{
			 closeAll(ps, conn);
			 conn.setAutoCommit(true);}
	}
	
	@Override
	public void deleteUser(String userId, String password) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;	
		try{
			conn = getConnect();
			conn.setAutoCommit(false);
			String query = "DELETE FROM user WHERE userId = ? AND password = ?";			
			ps = conn.prepareStatement(query);
			ps.setString(1,userId);
			ps.setString(2, password);
			if (ps.executeUpdate()==1){
				 conn.commit();
				 return;}
			else throw new RecordNotFoundException("수정할 대상이 없습니다.");
			}catch (Exception e) {
				conn.rollback();
				throw new SQLException();}
			 finally{
				 closeAll(ps, conn);
				 conn.setAutoCommit(true);}
		
	}
	
	@Override
	public void addPost(String userId, Post post) throws SQLException, DuplicateRercordException {
		Connection conn = null;
		PreparedStatement ps = null;	
		try{
			conn = getConnect();conn.setAutoCommit(false);
			String query = "INSERT INTO post(userId, postId, caption, imageSrc) VALUES(?,?,?,?)";			
			ps = conn.prepareStatement(query);
			ps.setString(1,userId);
			ps.setString(2, post.getPostId());
			ps.setString(3, post.getCaption());
			ps.setString(4, post.getImageSrc());
			if (ps.executeUpdate()==1){
				 conn.commit();
				 return;}
			else throw new DuplicateRercordException("수정할 대상이 없습니다.");
		
		}catch (Exception e) {
			conn.rollback();
			throw new SQLException();}
		 finally{
			 closeAll(ps, conn);
			 conn.setAutoCommit(true);}
	}
	
	@Override
	public void updatePost(String userId, Post post) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;		
		 try{
			 conn = getConnect();conn.setAutoCommit(false);
			 String query ="UPDATE post SET caption =? , imageSrc =? WHERE userId =? AND postId =?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1, post.getCaption());
			 ps.setString(2, post.getImageSrc());
			 ps.setString(3, userId);
			 ps.setString(4, post.getPostId());
			 if (ps.executeUpdate()==1){
				 conn.commit();
				 return;}
			 else throw new RecordNotFoundException("수정할 대상이 없습니다.");	 
		 }catch (Exception e) {
				conn.rollback();
				throw new SQLException();}
			 finally{
				 closeAll(ps, conn);
				 conn.setAutoCommit(true);}
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
	public void deletePost(String userId, Post post) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;		
		 try{
			 conn = getConnect();
			 conn.setAutoCommit(false);
			 if (isExist(userId, post, conn)) {
				 deletePostSub(conn, ps, post.getPostId());
				 String query ="DELETE FROM post WHERE userId = ? AND postId = ?";
				 ps = conn.prepareStatement(query);
				 ps.setString(1, userId);
				 ps.setString(2, post.getPostId());
				 if (ps.executeUpdate()==1){
					 conn.commit();
					 return;}
				else throw new RecordNotFoundException("수정할 대상이 없습니다.");
			}
			 // RDBMS의 특성상 가장 child한 table에서부터 지우고 올라가야되서 추가적인 execution들은 옮김.
			 	 
		 }catch (Exception e) {
				conn.rollback();
				throw new SQLException();}
			 finally{
				 closeAll(ps, conn);
				 conn.setAutoCommit(true);}		
	}
	
	@Override
	public void likePost(String userId, String postId) throws RecordNotFoundException, SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();conn.setAutoCommit(false);
			String query = "UPDATE post SET likeNum = likeNum+1 WHERE postId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, postId);
			if (ps.executeUpdate()==1){
				 conn.commit();
				 return;}
			else throw new RecordNotFoundException("수정할 대상이 없습니다.");
		}catch (Exception e) {
			conn.rollback();
			throw new SQLException();}
		 finally{
			 closeAll(ps, conn);
			 conn.setAutoCommit(true);}
		
	}
	
	@Override
	public ArrayList<Post> getPostsByHashTag(Hashtag hashtag) throws RecordNotFoundException, SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Post> posts = new ArrayList<>();
		try {
			conn = getConnect();
			String query = "SELECT * FROM hashgroup WHERE hashtagId=?"; // 달려있는 포스트 id들을 전부 가지고온다.
			ps = conn.prepareStatement(query);
			ps.setString(1, hashtag.getHashtagId());
			rs = ps.executeQuery();
			if (rs==null) throw new RecordNotFoundException("가져올 대상이 없습니다.");
			while (rs.next()) posts.add(getPost(rs.getString(2)));
			if (posts.size()==0) throw new RecordNotFoundException("가져올 대상이 없습니다.");
		} finally {closeAll(rs, ps, conn);}
		
		return posts;
	}
	
	
	@Override
	public Post getPost(String postId) throws SQLException, RecordNotFoundException {
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
			if (post==null) throw new RecordNotFoundException("가져올 대상이 없습니다.");
		}finally {closeAll(rs, ps, conn);}
		
		return post;
	}
	
	@Override
	public ArrayList<Post> getAllPostsOfPerson(String userId) throws SQLException, RecordNotFoundException {
		ArrayList<Post> plist = new ArrayList<>();Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		try {
			conn = getConnect();		
			String query = "SELECT * FROM post WHERE userId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while(rs.next()) {
				plist.add(new Post(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4),rs.getString(6)));} 
			if (plist.size()==0) throw new RecordNotFoundException("가져올 대상이 없습니다.");
			return plist;
		}finally {
			closeAll(rs, ps, conn);}
	}
	
	@Override
	public ArrayList<Hashtag> getHashtagsOnPost(String postId) throws SQLException, RecordNotFoundException {
		Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		ArrayList<Hashtag> hlist = new ArrayList<>();
		try {
			conn = getConnect();		
			String query = "SELECT * FROM hashgroup WHERE postId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, postId);
			rs = ps.executeQuery();
			while(rs.next()) {
				hlist.add(new Hashtag(rs.getString(3)));}
			if (hlist.size()==0) throw new RecordNotFoundException("가져올 대상이 없습니다.");
			return hlist;
		}finally {
			closeAll(rs, ps, conn);}
	}
	
	@Override
	public void addComment(String userId, String postId, String comment) throws SQLException, DuplicateRercordException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();conn.setAutoCommit(false);
			String query = "INSERT INTO comment (userId, postId, comment) VALUES (?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			ps.setString(2, postId);
			ps.setString(3, comment);
			if (ps.executeUpdate()==1){
				 conn.commit();
				 return;}
			else throw new DuplicateRercordException("가져올 대상이 없습니다. 오류");
		}catch (Exception e) {
			conn.rollback();
			throw new SQLException();}
		 finally{
			 closeAll(ps, conn);
			 conn.setAutoCommit(true);}
	}

	@Override
	public void updateComment(String userId, int commentid, String postId, String comment) throws  SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			 conn = getConnect();conn.setAutoCommit(false);
			 String query ="UPDATE comment SET comment =? WHERE commentId=? AND userId =? AND postId =?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1, comment);
			 ps.setInt(2, commentid);
			 ps.setString(3, userId);
			 ps.setString(4, postId);
			 if (ps.executeUpdate()==1){
				 conn.commit();
				 return;}
			 else throw new RecordNotFoundException("수정할 대상이 없습니다.");
		}catch (Exception e) {
			conn.rollback();
			throw new SQLException();}
		 finally{
			 closeAll(ps, conn);
			 conn.setAutoCommit(true);}
	}

	@Override
	public void deleteComment(String userId, String postId, int commentId) throws SQLException, RecordNotFoundException  {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			 conn = getConnect();conn.setAutoCommit(false);
			 String query ="DELETE FROM comment WHERE userid=? AND postid=? AND commentId=?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1, userId);
			 ps.setString(2, postId);
			 ps.setInt(3, commentId);
			 if (ps.executeUpdate()==1) return;
			 else throw new RecordNotFoundException("수정할 대상이 없습니다.");
		}catch (Exception e) {
			conn.rollback();
			throw new SQLException();}
		 finally{
			 closeAll(ps, conn);
			 conn.setAutoCommit(true);}
	}
	
	@Override
	public ArrayList<Comment> getCommentsOnPost(String postId) throws SQLException, RecordNotFoundException {
		ArrayList<Comment> clist = new ArrayList<>();Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		try {
			conn = getConnect();		
			String query = "select * FROM comment WHERE postId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, postId);
			rs = ps.executeQuery();
			while(rs.next()) {
				clist.add(new Comment(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5)));} 
			if (clist.size()==0) throw new RecordNotFoundException("수정할 대상이 없습니다.");
			return clist;
		}finally {
			closeAll(rs, ps, conn);}
	}
	

	@Override
	public void likeComment(String userId, int commentId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			 conn = getConnect();conn.setAutoCommit(false);
			 String query ="UPDATE comment SET likeNum = likeNum+1 WHERE commentId=?";
			 ps = conn.prepareStatement(query);
			 ps.setInt(1, commentId);
			 if (ps.executeUpdate()==1){
				 conn.commit();
				 return;}
			 else throw new RecordNotFoundException("수정할 대상이 없습니다.");
		}catch (Exception e) {
			conn.rollback();
			throw new SQLException();}
		 finally{
			 closeAll(ps, conn);
			 conn.setAutoCommit(true);}
	}
	
	@Override
	public ArrayList<Comment> getAllCommentsOfPerson(String userId) throws RecordNotFoundException, SQLException {
		ArrayList<Comment> clist = new ArrayList<>();Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		try {
			conn = getConnect();		
			String query = "SELECT * FROM comment WHERE userId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while(rs.next()) {
				clist.add(new Comment(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5)));} 
			if (clist.size()==0) {
				throw new RecordNotFoundException("수정할 대상이 없습니다.");
			}
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
			if (user==null) throw new RecordNotFoundException("유저를 찾을수 없습니다.");
		}
		finally {closeAll(rs, ps, conn);}
		
		return user;
	}
	
	@Override
	public User getUser(User U) throws SQLException, RecordNotFoundException {
		Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;User user = null;
		try {
			conn =getConnect();
			String query = "select * FROM user WHERE userName = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, U.getUserName());
			rs = ps.executeQuery();
			while(rs.next()) {
				user = new User(rs.getString("userId"), rs.getString("userName"), rs.getInt("followerNum"), 
						rs.getInt("followingNum"), rs.getInt("postNum"), rs.getString("email"), rs.getString("gender"));}
			if (user==null) throw new RecordNotFoundException("유저를 찾을수 없습니다.");
		}
		
		finally {closeAll(rs, ps, conn);}
		
		return user;
	}
	
	@Override
	public ArrayList<User> getPersontagsOnPost(String postId) throws RecordNotFoundException, SQLException {
		ArrayList<User> ulist = new ArrayList<>();Connection conn = null;PreparedStatement ps = null;ResultSet rs = null;
		try {
			conn = getConnect();		
			String query = "SELECT * FROM persontag WHERE postId=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, postId);
			rs = ps.executeQuery();
			while(rs.next()) {
				ulist.add(getUser(rs.getString(2)));}
			if (ulist.size()==0) throw new RecordNotFoundException("유저를 찾을수 없습니다.");
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
			if (ulist.size()==0) throw new RecordNotFoundException("유저를 찾을수 없습니다.");
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
			if (ulist.size()==0) throw new RecordNotFoundException("유저를 찾을수 없습니다.");
			return ulist;}
		finally {closeAll(rs, ps, conn);}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
