package com.KoreaIT.JAM;

public class Article {
	int id;
	String title;
	String body;

	public Article(String title, String body) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.body = body;
	}

	public Article() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
}
