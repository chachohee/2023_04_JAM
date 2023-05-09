package com.KoreaIT.JAM.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.KoreaIT.JAM.util.DBUtil;
import com.KoreaIT.JAM.util.SecSql;

public class ArticleDAO {
	private Connection conn;
	private SecSql sql;

	public ArticleDAO(Connection conn) {
		this.conn = conn;
	}
	
	//게시글 존재 여부(boolean)
	public boolean existingArticle(int id) {
		sql = new SecSql();
		sql.append("SELECT COUNT(*) > 0");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		return DBUtil.selectRowBooleanValue(conn, sql);
	}
	
	//게시글 존재 여부(int)
	public int existingArticleInt(int id) {
		sql = new SecSql();
		sql.append("SELECT COUNT(*)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		
		return DBUtil.selectRowIntValue(conn, sql);
	}
	
	public int insertArticle(int memberId, String title, String body) {
		sql = new SecSql();
		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", memberId = ?", memberId);
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		
		return DBUtil.insert(conn, sql);
	}
	
	public Map<String, Object> selectArticleById(int id) {
		sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		return DBUtil.selectRow(conn, sql);
	}
	
	public int deleteArticle(int id) {
		sql = new SecSql();
		sql.append("DELETE FROM article");
		sql.append("WHERE id =?", id);
		DBUtil.delete(conn, sql);
		return id;
	}
	
	public List<Map<String, Object>> selectAllArticles() {
		sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC");
		
		List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
		
		return articleListMap;
	}
	
	public int updateArticle(int id, String title, String body) {
		sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", id);
		
		DBUtil.update(conn, sql);
		return id;
	}
}
