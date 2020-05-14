package instagram.vo;

import java.io.Serializable;

public class Comment implements Serializable{
	public static int COMMENTIDNUM=0;
	private String commentId;
	private String comment;
	private int likeNum;
	private String userId;
	private String postId;

	
	public Comment() {}

	public Comment(String commentId, String comment, int likeNum, String userId, String postId) {
		super();
		this.commentId = commentId;
		this.comment = comment;
		this.likeNum = likeNum;
		this.userId = userId;
		this.postId = postId;
	}
	
	

	public String getCommentId() {
		return commentId;
	}



	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}



	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
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
		return "Comment [commentId=" + commentId + ", comment=" + comment + ", likeNum=" + likeNum + ", userId="
				+ userId + ", postId=" + postId + "]";
	}


	
}
