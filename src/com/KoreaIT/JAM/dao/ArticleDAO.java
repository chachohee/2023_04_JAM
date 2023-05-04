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
		sql = SecSql.from("SELECT COUNT(*) > 0");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		return DBUtil.selectRowBooleanValue(conn, sql);
	}
	
	//게시글 존재 여부(int)
	public int existingArticleInt(int id) {
		SecSql sql = SecSql.from("SELECT COUNT(*)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		
		return DBUtil.selectRowIntValue(conn, sql);
		//articleCount == 0 으로 비교한 이유는 count함수를 썼기 때문
		//articleCount에는 컬럼에 해당하는 데이터를 가져옴.
		//id로 조회했다면 조회하려는 id 번호를 가져왔을 것임.
	}
	
	public int insertArticle(String title, String body) {
		sql = new SecSql();
		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		
		return DBUtil.insert(conn, sql);
	}
	
	public Map<String, Object> selectById(int id) {
		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
		return articleMap;
	}
	
	public int deleteArticle(int id) {
		sql = SecSql.from("DELETE FROM article");
		sql.append("WHERE id =?", id);
		DBUtil.delete(conn, sql);
		return id;
	}
	
	public List<Map<String, Object>> getArticles() {
		sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC");
		
		List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
		
		return articleListMap;
	}
	
	public int updateArticle(int id, String title, String body) {

		sql = SecSql.from("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", id);
		
		DBUtil.update(conn, sql);
		return id;
	}
}
