package instagram.vo;

import java.io.Serializable;

public class Post implements Serializable{
	private String postId;
	private String caption;
	private String imageSrc;
	private int likeNum;
	private String date;
	private String userId;
	private static int POSTNUM=0;
	
	public Post() {}
	
	public Post(String caption, String imageSrc) {
		this.caption = caption;
		this.imageSrc = imageSrc;
	}
	
	public Post(String postId, String caption, String imageSrc, int likeNum) {
		super();
		this.postId = postId;
		this.caption = caption;
		this.imageSrc = imageSrc;
		this.likeNum = likeNum;
	}
	
	public Post(String postId, String caption, String imageSrc, int likeNum, String date) {
		super();
		this.postId = postId;
		this.caption = caption;
		this.imageSrc = imageSrc;
		this.likeNum = likeNum;
		this.date = date;
	}
	
	

	public Post(String postId, String caption, String imageSrc, int likeNum, String date, String userId) {
		super();
		this.postId = postId;
		this.caption = caption;
		this.imageSrc = imageSrc;
		this.likeNum = likeNum;
		this.date = date;
		this.userId = userId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		POSTNUM++;
		postId ="post";
		this.postId = postId+POSTNUM;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public int getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}
	
	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "postId=" + postId + ", caption=" + caption + ", imageSrc=" + imageSrc + ", likeNum=" + likeNum
				+ ", date=" + date + ", userId=" + userId +"\n" ;
	}

	

	
	
	
}
