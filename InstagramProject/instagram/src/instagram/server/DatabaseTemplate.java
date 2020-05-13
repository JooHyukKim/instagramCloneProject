package instagram.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import instagram.vo.*;

public interface DatabaseTemplate {
	
	//DB Connection...
	Connection getConnect() throws SQLException;
	void closeAll(PreparedStatement ps, Connection conn)throws SQLException;
	void closeAll(ResultSet rs, PreparedStatement ps, Connection conn)throws SQLException;
	//user CRUD
	void addUser(User user) throws SQLException;    //회원가입
	User getUser(String userId) throws SQLException; //회원정보 검색
	void updateUser(User user) throws SQLException;	//회원정보 변경
	void deleteUser(String userId) throws SQLException; //사용자 삭제
	
	ArrayList<User> getFolloerUsers(String userId) throws SQLException;   //자신이 팔로우하는 사용자 검색
	ArrayList<User> getFollowingUsers(String userId) throws SQLException; //자신을 팔로우하는 사용자 검색
	
	void addComment(Comment comment) throws SQLException; //사용자가 게시물에 댓글 작성
	void updateComment(Comment comment) throws SQLException; //사용자가 자신의 게시물 수정
	void deleteComment(String commentId) throws SQLException; //사용자가 자신의 게시물 삭제
	ArrayList<Post> getPostsOfPerson(String userId) throws SQLException;  //사용자가 작성한 게시물들 검색
	ArrayList<Post> getPostsOfOtherPerson(String userId) throws SQLException; //사용자를 팔로우하는 사용자들의 게시물 검색
	

	ArrayList<Comment> getCommentsOnPost(String postId)throws SQLException; //게시물에 있는 댓글들 리턴
	ArrayList<User> getUsersByPersonTag(String postId) throws SQLException; //게시물에 태그되어 있는 사용자들 검색
	ArrayList<PersonTag> getPersontagsOnPost(String userId, String postId)throws SQLException; //사용자 게시물에 있는 사용자태그 검색
	ArrayList<Hashtag> getHashtagsOnPost(String postId) throws SQLException; //사용자 게시물에 있는 해시태그 검색
	
	User getUserByPersonTag(int personTagIdx) throws SQLException; //사용자 태그로 사용자 검색???
	ArrayList<Post> getPostsByHashTag(String hashtagId)throws SQLException;  //해시태그로 게시물들 검색
	
	Comment getComment(String commentId) throws SQLException; //사용자
	
	//post CRUD
	void addPost(Post post)throws SQLException;
	Post getPost(String postId) throws SQLException;
	void updatePost(Post post) throws SQLException;
	void deletePost(String postId) throws SQLException;

	
	
	
	
	

}
