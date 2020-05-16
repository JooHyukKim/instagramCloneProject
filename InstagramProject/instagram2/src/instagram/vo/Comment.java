package instagram.vo;

import java.io.Serializable;

public class Comment implements Serializable{
	private int commentId;
	private String comment;
	private int likeNum;
	private String userId;
	private String postId;

	
	public Comment() {}

	public Comment(int commentId, String comment, int likeNum, String userId, String postId) {
		super();
		this.commentId = commentId;
		this.comment = comment;
		this.likeNum = likeNum;
		this.userId = userId;
		this.postId = postId;
	}
	
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
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
