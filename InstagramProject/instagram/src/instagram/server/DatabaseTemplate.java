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
	void deleteUser(String userId) throws SQLException; //회원탈퇴
	
	ArrayList<User> getFollowerUsers(String userId) throws SQLException;   //자신이 팔로우하는 사용자 검색
	ArrayList<User> getFollowingUsers(String userId) throws SQLException; //자신을 팔로우하는 사용자 검색
	
	void addComment(String userId, String postId, Comment comment) throws SQLException; //사용자가 게시물에 댓글 작성
	void updateComment(String userId, String postId, Comment comment) throws SQLException; //사용자가 자신의 댓글 수정
	void deleteComment(String userId, String postId, String commentId) throws SQLException; //사용자가 자신의 댓글 삭제
	ArrayList<Comment> getCommentsOnPost(String postId)throws SQLException; //게시물에 있는 댓글들 조회
	ArrayList<Comment> getAllComments(String userId) throws SQLException; //자신이 쓴 모든 댓글들 조회
	
	void addPost(String userId, Post post)throws SQLException; //사용자가 게시물 작성
	Post getPost(String userId, String postId) throws SQLException; //사용자가 특정 게시물 검색
	ArrayList<Post> getAllPostsOfPerson(String userId) throws SQLException;  //사용자가 작성한 게시물들 검색
	ArrayList<Post> getSomePostsOfOtherPerson(String userId) throws SQLException; //사용자를 팔로우하는 사용자들의 게시물 검색(개수 제한)
	void updatePost(String userId, Post post) throws SQLException; //사용자가 자신의 게시물 수정
	void deletePost(String userId, String postId) throws SQLException; //사용자가 특정 게시물 삭제

	ArrayList<User> getUsersByPersonTag(String postId) throws SQLException; //게시물에 사용자태그되어 있는 사용자들 검색
	ArrayList<PersonTag> getPersontagsOnPost(String userId, String postId)throws SQLException; //게시물에 있는 사용자태그 검색
	User getUserByPersonTag(int personTagIdx) throws SQLException; //사용자 태그로 사용자 검색???
	
	ArrayList<Hashtag> getHashtagsOnPost(String postId) throws SQLException; //사용자 게시물에 있는 해시태그 검색
	ArrayList<Post> getPostsByHashTag(String hashtagId)throws SQLException;  //해시태그로 게시물들 검색

}
