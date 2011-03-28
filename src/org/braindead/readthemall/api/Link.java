package org.braindead.readthemall.api;

public class Link {
	private String link;
	private String title;
	
	public Link(String link, String title) {
		this.link = link;
		this.title = title;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	public String getLink() {
		return link;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
}
