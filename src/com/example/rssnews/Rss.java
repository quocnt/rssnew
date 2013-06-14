package com.example.rssnews;

public class Rss {
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	private String content;
	private String date;
	private String image;

	public Rss(String title, String content, String date, String image) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.content = content;
		this.date = date;
		this.image = image;
	}

	public Rss() {

	}

}
