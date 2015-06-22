package photo_album_app;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class Photo {
	
	private String title;
	private String path;
	private DateTime created_at;
	private ArrayList<Comment> comments;
	
	public Photo(String title, String path) {
		this.title = title;
		this.path = path;
		comments = new ArrayList<Comment>();
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;		
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public ArrayList<Comment> getComments() {
		return this.comments;
	}

}
