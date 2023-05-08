package com.KoreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.KoreaIT.JAM.controller.ArticleController;
import com.KoreaIT.JAM.controller.MemberController;
import com.KoreaIT.JAM.util.DBUtil;
import com.KoreaIT.JAM.util.SecSql;

public class App {

	public void run() {
		Connection conn = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.println("=====프로그램 시작=====");
			
			Class.forName("com.mysql.cj.jdbc.Driver"); //ClassNotFoundException
			String url = "jdbc:mysql://localhost:3306/jdbc_article_manager";
			conn = DriverManager.getConnection(url, "root", ""); //SQLException
			System.out.println("DB 연결 성공!");
			
			MemberController memberController = new MemberController(conn, sc);
			ArticleController articleController = new ArticleController(conn, sc);
			
			while (true) {
				
				System.out.printf("명령어) ");
				String cmd = sc.nextLine().trim();
				cmd = cmd.toLowerCase();
				
				if (cmd.equals("exit")) {
					System.out.println("=====프로그램 종료=====");
					break;	
				}
				//회원가입
				if (cmd.equals("member join")) {
					memberController.doJoin();
				//로그인	
				} else if (cmd.equals("member login")) {
					memberController.doLogin();
				//글 작성
				} else if (cmd.equals("article write")) {
					articleController.doWrite();
				//글 조회
				} else if (cmd.equals("article list")) {
					articleController.showList();
				//글 수정
				} else if (cmd.startsWith("article modify ")) {
					articleController.doModify(cmd);
				//글 삭제
				} else if (cmd.startsWith("article delete ")) {
					articleController.doDelete(cmd);	
				//글 상세
				} else if (cmd.startsWith("article detail ")) {
					articleController.showDetail(cmd);
				} else {
					System.out.println("존재하는 명령어가 없습니다.");
				}
			} //end while(데이터 삽입, 조회, 수정, 삭제, 상세)
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}//end finally(사용한 자원 종료)
		sc.close();
	} //end run()
}
