package com.KoreaIT.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.KoreaIT.JAM.dto.Member;
import com.KoreaIT.JAM.service.MemberService;
import com.KoreaIT.JAM.session.Session;

public class MemberController {
	private Scanner sc;
	private MemberService memberService;
	
	public MemberController(Connection conn, Scanner sc) {
		this.sc = sc;
		memberService = new MemberService(conn);
	}

	public void doJoin() {
		if(Session.isLogined()) {
			System.out.println("로그아웃 후 이용해주세요.");
			return;
		}
		
		System.out.println("== 회원가입 ==");
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
				System.out.println(loginId + "(은)는 이미 사용중인 아이디입니다.");
				continue;
			}
			System.out.println(loginId + "(은)는 사용 가능합니다.");
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
			if (!loginPw.equals(_loginPw)) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
			System.out.println("비밀번호가 일치합니다.");
			break;
		}
		
		while(true) {
			System.out.printf("이름: ");
			name = sc.nextLine().trim();	
			if (loginId.isEmpty() || loginPw.isEmpty() || name.isEmpty()) {
				System.out.println("이름은 필수입니다.");
				continue;
			}
			int id = memberService.doJoin(loginId, loginPw, name);
			System.out.printf("%d번째 회원이 생성되었습니다\n", id);
			System.out.printf("%s님 환영합니다~\n", name);
			System.out.println();
			break;
		}
	}

	public void doLogin() {
		//로그인 검증(이미 로그인한 상태라면 로그아웃하고 오도록.
		if(Session.isLogined()) {
			String loginedId = Session.loginedMember.loginId;
			System.out.println(loginedId + "님이 로그인한 상태입니다.");
			System.out.println();
			return;
		}
		System.out.println("== 로그인 ==");
		while(true) {
			System.out.printf("로그인 아이디 : ");
			String loginId = sc.nextLine().trim();
			if(loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요.");
				continue;
			}
			
			Member member = memberService.selectMember(loginId);
			if(member == null) {
				System.out.println(loginId + "(은)는 존재하지 않는 아이디입니다.");
				System.out.println();
				return;//다시 명령어 받으러 감.
			}
			
			while(true) {
				System.out.printf("로그인 비밀번호 : ");
				String loginPw = sc.nextLine().trim();
				if(loginPw.length() == 0) {
					System.out.println("비밀번호를 입력해주세요.");
					continue;
				}
				if(!member.loginPw.equals(loginPw)) {
					System.out.println("비밀번호가 일치하지 않습니다.");
					continue;
				}
				break;
			}
			System.out.println(member.loginId + "님 환영합니다. ^^");
			System.out.println();

			Session.login(member);
			
			break;
		}
	}

	public void doLogout() {
		if(!Session.isLogined()) {
			System.out.println("이미 로그아웃한 상태입니다.");
			System.out.println();
			return;
		}
		Session.logout();
		System.out.println("로그아웃 되었습니다.");
		System.out.println();
	}
}

