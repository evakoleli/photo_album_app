package photo_album_app;

import org.joda.time.DateTime;

public class Comment {
	private int photo_id;
	private int user_id;
	private String text;
	private DateTime created_at;
	
	public Comment(int photo_id, int user_id, String text) {
		this.photo_id = photo_id;
		this.user_id = user_id;
		this.text = text;
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
