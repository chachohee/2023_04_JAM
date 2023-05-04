package com.KoreaIT.JAM;

import java.time.LocalDateTime;

public class Member {
	int id;
	LocalDateTime regDate;
	LocalDateTime updateDate;
	String loginId;
	String loginPw;
	String name;
	
	public Member(int id, LocalDateTime regDate, String loginId, String loginPw, String name) {
		this.id = id;
		this.regDate = regDate;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.name = name;
	}
}
