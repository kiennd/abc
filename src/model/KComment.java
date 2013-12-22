package model;

import java.io.Serializable;
import java.util.Vector;

public class KComment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2792566863605046482L;
	private String user; // owner of status
	private String content; // content
	private int status; // pos or neg
	private Vector<KComment> subComment = new Vector<>();
	
	
	
	
	public Vector<KComment> getSubComment() {
		return subComment;
	}

	public void setSubComment(Vector<KComment> subComment) {
		this.subComment = subComment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public void addSubComment(KComment cm){
		this.subComment.add(cm);
	}

}
