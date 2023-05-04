package com.KoreaIT.JAM.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.KoreaIT.JAM.Article;
import com.KoreaIT.JAM.dao.ArticleDAO;
import com.KoreaIT.JAM.util.DBUtil;
import com.KoreaIT.JAM.util.SecSql;

public class ArticleService {
	ArticleDAO articleDAO;

	public ArticleService(Connection conn) {
		articleDAO = new ArticleDAO(conn);
	}
	
	public boolean existingArticle(int id) {
		return articleDAO.existingArticle(id);
	}
	
	public void existingArticleInt(int id) {
		int articleCount = articleDAO.existingArticleInt(id);
		//articleCount == 0 으로 비교한 이유는 count함수를 썼기 때문
		//articleCount에는 컬럼에 해당하는 데이터를 가져옴.
		//id로 조회했다면 조회하려는 id 번호를 가져왔을 것임.
		if (articleCount == 0) {
			System.out.printf("%d번 게시글은 존재하지 않습니다\n", id);
			return;
		}
	}
	
	public int doWrite(String title, String body) {
		return articleDAO.insertArticle(title, body);
	}
	
	public Map<String, Object> showDetail(int id) {
		return articleDAO.selectById(id);
	}
	
	public List<Article> getArticles() {
		List<Map<String, Object>> articleListMap = articleDAO.getArticles();
		
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
