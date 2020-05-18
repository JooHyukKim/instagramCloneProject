package instagram.vo;

import java.io.Serializable;

public class HashGroup implements Serializable{
	private int HashGroupIdx;
	private String postId;
	private String hashtag;
	
	public HashGroup() {}

	public HashGroup(int hashGroupIdx, String postId, String hashtag) {
		super();
		HashGroupIdx = hashGroupIdx;
		this.postId = postId;
		this.hashtag = hashtag;
	}

	public int getHashGroupIdx() {
		return HashGroupIdx;
	}

	public void setHashGroupIdx(int hashGroupIdx) {
		HashGroupIdx = hashGroupIdx;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	@Override
	public String toString() {
		return "HashGroup [HashGroupIdx=" + HashGroupIdx + ", postId=" + postId + ", hashtag=" + hashtag + "]";
	}
	
	
	
}
