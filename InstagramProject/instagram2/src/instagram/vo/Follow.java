package instagram.vo;

import java.io.Serializable;

public class Follow  implements Serializable{
	private int followidx;
	private String userId;
	private String following;
	
	public Follow() {}

	public Follow(int followidx, String userId, String following) {
		super();
		this.followidx = followidx;
		this.userId = userId;
		this.following = following;
	}

	public int getFollowidx() {
		return followidx;
	}

	public void setFollowidx(int followidx) {
		this.followidx = followidx;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFollowing() {
		return following;
	}

	public void setFollowing(String following) {
		this.following = following;
	}

	@Override
	public String toString() {
		return "Follow [followidx=" + followidx + ", userId=" + userId + ", following=" + following + "]";
	}
	
	
}
