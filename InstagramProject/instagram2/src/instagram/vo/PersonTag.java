package instagram.vo;

import java.io.Serializable;

public class PersonTag implements Serializable{
	private int personTagIdx;
	private String userId;
	private String postId;
	
	public PersonTag() {}

	public PersonTag(int personTagIdx, String userId, String postId) {
		super();
		this.personTagIdx = personTagIdx;
		this.userId = userId;
		this.postId = postId;
	}

	public int getPersonTagIdx() {
		return personTagIdx;
	}

	public void setPersonTagIdx(int personTagIdx) {
		this.personTagIdx = personTagIdx;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	@Override
	public String toString() {
		return "PersonTag [personTagIdx=" + personTagIdx + ", userId=" + userId + ", postId=" + postId + "]";
	}
	
	
}
