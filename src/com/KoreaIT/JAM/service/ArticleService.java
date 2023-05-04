package com.KoreaIT.JAM.service;

import java.sql.Connection;
import java.util.Map;

import com.KoreaIT.JAM.dto.ArticleDAO;

public class ArticleService {
	ArticleDAO articleDAO;

	public ArticleService(Connection conn) {
		articleDAO = new ArticleDAO(conn);
	}
	
	public boolean existingArticle(int id) {
		return articleDAO.existngArticle(id);
	}
	
	public int doWrite(String title, String body) {
		return articleDAO.insertArticle(title, body);
	}
	
	public Map<String, Object> showDetail(int id) {
		return articleDAO.selectById(id);
	}
	
	public void showList() {
	
	}
	public void doModify() {
		
	}
	
	public void doDelete(int id) {
		articleDAO.deleteArticle(id);
	}

}
