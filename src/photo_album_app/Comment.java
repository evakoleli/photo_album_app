package photo_album_app;

/**
 * Comment class
 * Users can leave comments to photos
 */
public class Comment {
	private Photo photo;
	private User user;
	private String text;
	//this should be DateTime, but I am just using it as a string in this version of the project
	private String created_at;
	private String user_email;
	private int photo_id;
	private int user_id;
	
	public Comment(Photo photo, User user, String text) {
		this.photo = new Photo(photo);
		this.user = new User(user);
		this.text = text;
	}
	
	public Comment(int photo_id, String user_email, String text, String created_at) {
		this.photo_id = photo_id;
		this.user_email = user_email;
		this.text = text;
		this.created_at = created_at;
	}
	
	public void setPhotoId(int photo_id) {
		this.photo_id = photo_id;
	}
	
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setUserEmail(String user_email) {
		this.user_email = user_email;
	}
	
	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	
	public String getCreatedAt() {
		return this.created_at;
	}
	
	public String getUserEmail() {
		return this.user_email;
	}
	
	public int getPhotoId() {
		return this.photo_id;
	}
	
	public int getUserId() {
		return this.user_id;
	}
	
	public String getText() {
		return this.text;
	}
}
