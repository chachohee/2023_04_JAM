package com.KoreaIT.JAM.service;

import java.sql.Connection;

import com.KoreaIT.JAM.dao.MemberDAO;

public class MemberService {

	MemberDAO memberDAO;
	
	public MemberService(Connection conn) {
		this.memberDAO = new MemberDAO(conn);
	}
	//아이디 중복 체크
	public boolean isLoginIdDup(String loginId) {
		return memberDAO.isLoginIdDup(loginId);
	}
	
	public int doJoin(String loginId, String loginPw, String name) {
		return memberDAO.doJoin(loginId, loginPw, name);
	}

}
