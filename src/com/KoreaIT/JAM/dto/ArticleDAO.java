package com.KoreaIT.JAM.dto;

import java.sql.Connection;
import java.util.Map;

import com.KoreaIT.JAM.util.DBUtil;
import com.KoreaIT.JAM.util.SecSql;

public class ArticleDAO {
	private Connection conn;
	private SecSql sql;

	public ArticleDAO(Connection conn) {
		
	}
	//게시글 존재 여부
	public boolean existngArticle(int id) {
		sql = SecSql.from("SELECT COUNT(*) > 0");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		return DBUtil.selectRowBooleanValue(conn, sql);
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
	
	public void deleteArticle(int id) {
		sql = SecSql.from("delete from article");
		sql.append("where id =?", id);
	}
	

}
