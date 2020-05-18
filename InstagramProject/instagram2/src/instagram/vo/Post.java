package instagram.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

public class Post implements Serializable{
	private String postId;
	private String caption;
	private String imageSrc;
	private String userId;
	private int likeNum;;
	private ArrayList<Comment> comments;
	private String date;
	private String commentAL = "";
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Post() {}

	public Post(String postId) {
		super();
		this.postId = postId;
	}
	
	public Post(String postId, String caption, String imageSrc) {
		super();
		this.postId = postId;
		this.caption = caption;
		this.imageSrc = imageSrc;
	}
	
	public Post(String postId, String caption, String imageSrc, int likeNum, String userId) {
		super();
		this.postId = postId;
		this.caption = caption;
		this.imageSrc = imageSrc;
		this.likeNum = likeNum;
		this.userId = userId;
	}
	
	public Post(String postId, String caption, String imageSrc, int likeNum, ArrayList<Comment> comments) {
		super();
		this.postId = postId;
		this.caption = caption;
		this.imageSrc = imageSrc;
		this.likeNum = likeNum;
		this.comments = comments;
	}
	
	public Post(String postId, String caption, String imageSrc, int likeNum, String date, ArrayList<Comment> comments) {
		super();
		this.postId = postId;
		this.caption = caption;
		this.imageSrc = imageSrc;
		this.likeNum = likeNum;
		this.comments = comments;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	@Override
	public String toString() {
		if (comments == null) { 
			return "Post [postId=" + postId + ", caption=" + caption + ", imageSrc=" + imageSrc + ", likeNum=" + likeNum
				+ ", userId=" + userId+ ", comments=" + comments + ", date=" + date + "]";}
		else {
			Iterator<Comment> clickedUserPostsIT = comments.iterator();
			while (clickedUserPostsIT.hasNext()) {commentAL += clickedUserPostsIT.next();}
			return "Post [postId=" + postId + ", caption=" + caption + ", imageSrc=" + imageSrc + ", likeNum=" + likeNum
					+ ", comments=" + commentAL + ", date=" + date + "]";
		}
	}
	
	
	
	


	
	
	
}
