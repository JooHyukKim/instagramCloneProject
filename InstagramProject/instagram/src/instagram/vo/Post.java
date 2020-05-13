package instagram.vo;

public class Post {
	private String postId;
	private String caption;
	private String imageSrc;
	private int likeNum;
	
	public Post() {}

	public Post(String postId, String caption, String imageSrc, int likeNum) {
		super();
		this.postId = postId;
		this.caption = caption;
		this.imageSrc = imageSrc;
		this.likeNum = likeNum;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
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
		return "Post [postId=" + postId + ", caption=" + caption + ", imageSrc=" + imageSrc + ", likeNum=" + likeNum
				+ "]";
	};
	
	
}
