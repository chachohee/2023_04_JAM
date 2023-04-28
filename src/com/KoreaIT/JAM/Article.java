package com.KoreaIT.JAM;

public class Article {
	int id;
	String regDate;
	String updateDate;
	String title;
	String body;
	
	public Article() {
		// TODO Auto-generated constructor stub
	}
	
	public Article(String title, String body) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.body = body;
	}
	
	public Article(int id2, String regDate2, String updateDate2, String title2, String body2) {
		// TODO Auto-generated constructor stub
		this.id = id2;
		this.regDate = regDate2;
		this.updateDate = updateDate2;
		this.title = title2;
		this.body = body2;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", title=" + title
				+ ", body=" + body + "]";
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
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
