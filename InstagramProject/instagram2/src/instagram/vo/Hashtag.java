package instagram.vo;

import java.io.Serializable;

public class Hashtag implements Serializable{
	private String hashtagId;
	
	public Hashtag() {}

	public Hashtag(String hashtagId) {
		super();
		this.hashtagId = hashtagId;
	}

	public String getHashtagId() {
		return hashtagId;
	}

	public void setHashtagId(String hashtagId) {
		this.hashtagId = hashtagId;
	}

	@Override
	public String toString() {
		return "Hashtag [hashtagId=" + hashtagId + "]";
	}
	
}
