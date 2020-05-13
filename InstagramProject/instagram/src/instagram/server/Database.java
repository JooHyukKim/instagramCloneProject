package instagram.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import instagram.vo.Comment;
import instagram.vo.Hashtag;
import instagram.vo.PersonTag;
import instagram.vo.Post;
import instagram.vo.User;

public class Database implements DatabaseTemplate{

	@Override
	public Connection getConnect() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUser(User user) throws SQLException {
		// TODO Auto-generated method stub
		
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
