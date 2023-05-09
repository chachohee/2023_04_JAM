package com.KoreaIT.JAM.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.KoreaIT.JAM.dto.Article;
import com.KoreaIT.JAM.dao.ArticleDAO;

public class ArticleService {
	ArticleDAO articleDAO;

	public ArticleService(Connection conn) {
		articleDAO = new ArticleDAO(conn);
	}
	
	public boolean existingArticle(int id) {
		return articleDAO.existingArticle(id);
	}
	
	public int existingArticleInt(int id) {
		int articleCount = articleDAO.existingArticleInt(id);
		return articleCount;
	}
	
	public int doWrite(String title, String body) {
		return articleDAO.insertArticle(title, body);
	}
	
	public Article getArticle(int id) {
		Map<String, Object> articleMap = articleDAO.selectArticleById(id);
		//DBUtil.selectRow는 받아올 데이터가 없을 때 new HashMap<>()을 리턴함.
		if (articleMap.isEmpty()) {
			return null;
		}
		return new Article(articleMap);
	}
	
	public List<Article> getArticles() {
		List<Map<String, Object>> articleListMap = articleDAO.selectAllArticles();
		List<Article> articles = new ArrayList<>();
		for(Map<String, Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}
		return articles;
	}
	
	public int doModify(int id, String title, String body) {
		return articleDAO.updateArticle(id, title, body);
		
	}
	
	public int doDelete(int id) {
		return articleDAO.deleteArticle(id);
	}

}
