package com.KoreaIT.JAM.session;

import com.KoreaIT.JAM.dto.Member;

public class Session {
	public static int loginedMemberId;
	public static Member loginedMember;
	
	static {
		loginedMemberId = -1;
		loginedMember = null;
	}

	public static void login(Member member) {
		loginedMember = member;
		loginedMemberId = member.id;		
	}
	
	public static void logout() {
		loginedMemberId = -1;
		loginedMember = null;
	}

	public static boolean isLogined() {
		return loginedMemberId != -1;
	}
}
