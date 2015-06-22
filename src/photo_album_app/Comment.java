package photo_album_app;

import org.joda.time.DateTime;

public class Comment {
	private int photo_id;
	private int user_id;
	private String user_email;
	private String text;
	private String created_at;
	
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
