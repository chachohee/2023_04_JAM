package com.KoreaIT.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.KoreaIT.JAM.service.MemberService;
import com.KoreaIT.JAM.util.SecSql;

public class MemberController {
	private Scanner sc;
	private Connection conn;
	private MemberService memberService;
	
	public MemberController(Connection conn, Scanner sc) {
		this.sc = sc;
		this.conn = conn;
		memberService = new MemberService(conn);
	}

	public void doJoin() {
		System.out.println("== 회원가입 ==");
		SecSql sql = new SecSql();
		String loginId = null;
		String loginPw = null;
		String _loginPw = null;
		String name = null;
		
		while(true) {
			System.out.printf("로그인 아이디 : ");
			loginId = sc.nextLine().trim();
			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요.");
				continue;
			}
			
			boolean isloginIdDup = memberService.isLoginIdDup(loginId);
			
			if (isloginIdDup) {
				System.out.println(loginId + "는 이미 사용중인 아이디입니다.");
				continue;
			}
			System.out.println(loginId + "는 사용 가능합니다.");
			break;
		}
		
		while(true) {
			System.out.printf("로그인 비밀번호 : ");
			loginPw = sc.nextLine().trim();
			if (loginPw.length() == 0) {
				System.out.println("비밀번호를 입력해주세요.");
				continue;
			}
			System.out.printf("로그인 비밀번호 확인 : ");
			_loginPw = sc.nextLine().trim();
			if (loginPw.equals(_loginPw)) {
			System.out.printf("이름: ");
			name = sc.nextLine().trim();
			if (loginId.isEmpty() || loginPw.isEmpty() || name.isEmpty()) {
				System.out.println("이름은 필수입니다.");
			} else {
				sql = new SecSql();
				sql.append("INSERT INTO `member`");
				sql.append("SET regDate = NOW()");
				sql.append(", updateDate = NOW()");
				sql.append(", loginId = ?", loginId);
				sql.append(", loginPw = ?", loginPw);
				sql.append(", `name` = ?", name);
			
				int id = memberService.doJoin(loginId, _loginPw, name);
				System.out.printf("%d번째 회원이 생성되었습니다\n", id);
				System.out.println();
				break;
			}
		} else if (!loginPw.equals(_loginPw)) {
			System.out.println("비밀번호가 일치하지 않습니다.");
		}
		continue;	
		}
	}

}