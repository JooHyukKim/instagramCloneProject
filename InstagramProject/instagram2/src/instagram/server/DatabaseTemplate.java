package instagram.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import instagram.exception.DuplicateRercordException;
import instagram.exception.RecordNotFoundException;
import instagram.vo.*;

public interface DatabaseTemplate {
	
	//DB Connection...
	Connection getConnect() throws SQLException;
	void closeAll(PreparedStatement ps, Connection conn)throws SQLException;
	void closeAll(ResultSet rs, PreparedStatement ps, Connection conn)throws SQLException;
	//user CRUD
	void addUser(User user) throws SQLException, DuplicateRercordException;    //회원가입
	User getUser(String userId) throws SQLException, RecordNotFoundException; //회원정보 검색
	User getUser(User user) throws SQLException, RecordNotFoundException;
	void updateUser(User user) throws SQLException, RecordNotFoundException;	//회원정보 변경
	void deleteUser(String userId, String password) throws SQLException, RecordNotFoundException; //회원탈퇴
	void authenticateUser(String userId, String password) throws Exception, SQLException; //사용자로그인 + 관리자여부 Boolean값 리턴
	
	ArrayList<User> getFollowerUsers(String userId) throws SQLException, Exception, RecordNotFoundException;   //타겟유저가 팔로우하는 사용자 검색
	ArrayList<User> getFollowingUsers(String userId) throws SQLException, Exception, RecordNotFoundException; //타겟유저가 팔로우하는 사용자 검색
	
	void addComment(String userId, String postId, String comment) throws SQLException, DuplicateRercordException; //사용자가 게시물에 댓글 작성
	void updateComment(String userId, int commentid, String postId, String comment) throws Exception, SQLException, RecordNotFoundException; //사용자가 자신의 댓글 수정
	void deleteComment(String userId, String postId, int commentId) throws Exception, SQLException; //사용자가 자신의 댓글 삭제
	ArrayList<Comment> getCommentsOnPost(String postId)throws Exception, SQLException; //게시물에 있는 댓글들 조회
	ArrayList<Comment> getAllCommentsOfPerson(String userId) throws Exception, SQLException; //해당 유저가 쓴 모든 댓글들 조회
	void likeComment(String userId, int commentId) throws Exception, SQLException;
	
	void addPost(String userId, Post post)throws SQLException, DuplicateRercordException; //사용자가 게시물 작성
	Post getPost(String userId, String postId) throws Exception, SQLException; //사용자가 특정 게시물 검색
	Post getPost(String postId) throws Exception, SQLException;
	ArrayList<Post> getAllPostsOfPerson(String userId) throws Exception, SQLException;  //사용자가 작성한 게시물들 검색
	ArrayList<Post> getSomePostsOfOtherPerson(String userId) throws Exception, SQLException; //사용자를 팔로우하는 사용자들의 게시물 검색(개수 제한)
	void updatePost(String userId, Post post) throws RecordNotFoundException, SQLException; //사용자가 자신의 게시물 수정
	void deletePost(String userId, Post post) throws SQLException, RecordNotFoundException ; //사용자가 특정 게시물 삭제
	void likePost(String userId, String postId) throws RecordNotFoundException, SQLException;
	ArrayList<Comment> getAllComments(String userId) throws SQLException;
	ArrayList<User> getUsersByPersonTag(String postId) throws Exception, SQLException; //게시물에 사용자태그되어 있는 사용자들 검색
	ArrayList<User> getPersontagsOnPost(String postId) throws Exception, SQLException; //게시물에 있는 사용자들을 검색
	User getUserByPersonTag(int personTagIdx) throws Exception, SQLException; //사용자 태그로 사용자 검색???
	
	ArrayList<Hashtag> getHashtagsOnPost(String postId) throws Exception, SQLException; //게시물에 있는 해시태그 검색
	ArrayList<Post> getPostsByHashTag(Hashtag hashtag)throws Exception, SQLException;  //해시태그로 게시물들 검색

}