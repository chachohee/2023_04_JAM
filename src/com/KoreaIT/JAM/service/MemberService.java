package com.KoreaIT.JAM.service;

import java.sql.Connection;
import java.util.Map;

import com.KoreaIT.JAM.dto.Member;
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

	public Member selectMember(String loginId) {
		Map<String, Object> memberMap = memberDAO.selectMember(loginId);
		if(memberMap.isEmpty()) {
			return null;
		}
		return new Member(memberMap);
	}
}
