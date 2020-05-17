package instagram.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	private String userId;
	private String userName;
	private String password;
	private int followerNum;
	private int followingNum;
	private int postNum;
	private String email;
	private String gender;
	private ArrayList<Follow> follow;
	
	public User() {}
	
	public User(String userId, String userName, String password, int followerNum, int followingNum, int postNum,
			String email, String gender) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.followerNum = followerNum;
		this.followingNum = followingNum;
		this.postNum = postNum;
		this.email = email;
		this.gender = gender;
	}
	
	public User(String userId, String userName, int followerNum, int followingNum, int postNum,
			String email) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.followerNum = followerNum;
		this.followingNum = followingNum;
		this.postNum = postNum;
		this.email = email;
	}

	public User(String userId, String userName, String password, int followerNum, int followingNum, int postNum,
			String email, String gender, ArrayList<Follow> follow) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.followerNum = followerNum;
		this.followingNum = followingNum;
		this.postNum = postNum;
		this.email = email;
		this.gender = gender;
		this.follow = follow;
	}
	
	public User(String userId, int followerNum, int followingNum, int postNum, String email) {
		super();
		this.userId = userId;
		this.followerNum = followerNum;
		this.followingNum = followingNum;
		this.postNum = postNum;
		this.email = email;
	}
	
	

	public User(String userId, String userName, String password, String email, String gender) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.gender = gender;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getFollowerNum() {
		return followerNum;
	}

	public void setFollowerNum(int followerNum) {
		this.followerNum = followerNum;
	}

	public int getFollowingNum() {
		return followingNum;
	}

	public void setFollowingNum(int followingNum) {
		this.followingNum = followingNum;
	}

	public int getPostNum() {
		return postNum;
	}

	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	
	
	public ArrayList<Follow> getFollow() {
		return follow;
	}

	public void setFollow(ArrayList<Follow> follow) {
		this.follow = follow;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", followerNum="
				+ followerNum + ", followingNum=" + followingNum + ", postNum=" + postNum + ", email=" + email
				+ ", gender=" + gender + ", follow=" + follow + "]";
	}

	
	
}
