package com.KoreaIT.JAM.dao;

import java.sql.Connection;
import java.util.Map;

import com.KoreaIT.JAM.util.DBUtil;
import com.KoreaIT.JAM.util.SecSql;

public class MemberDAO {
	private Connection conn;
	SecSql sql;
	
	public MemberDAO(Connection conn) {
		this.conn = conn;
	}
	
	public boolean isLoginIdDup(String loginId) {
		sql = new SecSql();
		sql.append("SELECT COUNT(*) > 0");
		sql.append("FROM `member`");
		sql.append("WHERE loginId=?", loginId);
		return DBUtil.selectRowBooleanValue(conn, sql);
	}

	public int doJoin(String loginId, String loginPw, String name) {
		sql = new SecSql();
		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", name = ?", name);
		return DBUtil.insert(conn, sql); 
	}

	public Map<String, Object> selectMember(String loginId) {
		sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		return DBUtil.selectRow(conn, sql);
	}
}
