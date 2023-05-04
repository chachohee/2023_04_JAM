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
	//1. db 연결
	//2. 데이터 write, list, modify, delete
	public void run() {
		Connection conn = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); //ClassNotFoundException
			String url = "jdbc:mysql://localhost:3306/jdbc_article_manager";
			conn = DriverManager.getConnection(url, "root", ""); //SQLException
			System.out.println("DB 연결 성공!");
			
			MemberController memberController = new MemberController(conn, sc);
			ArticleController articleController = new ArticleController(conn, sc);
			
			System.out.println("=====프로그램 시작=====");
			while (true) {
				List<Member> members;
				
				System.out.printf("명령어) ");
				String cmd = sc.nextLine().trim();
				cmd= cmd.toLowerCase();
				
				if (cmd.equals("exit")) {
					System.out.println("=====프로그램 종료=====");
					break;	
				}
				
				//글 작성
				if (cmd.equals("article write")) {
					articleController.doWrite();
					
				//글 조회
				} else if (cmd.equals("article list")) {
					List<Article> articles = new ArrayList<>();
					
					SecSql sql = new SecSql();
					
					sql.append("SELECT *");
					sql.append("FROM article");
					sql.append("ORDER BY id DESC");
					
					List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
					
					for(Map<String, Object> articleMap : articleListMap) {
						articles.add(new Article(articleMap));
					}

					if (articles.size() == 0) {
						System.out.println("존재하는 게시물이 없습니다");
						continue;
					}
					System.out.println("== 게시물 리스트 ==");
					System.out.println("번호	|	제목	|	날짜");

					for (Article article : articles) {
						System.out.printf("%d	|	%s	|	%s\n", article.id, article.title, article.regDate);
					}
					
					System.out.println();
					
				//글 수정
				} else if (cmd.startsWith("article modify ")) {
					// String[] cmdBits = cmd.split(" ");
					// int id = Integer.parseInt(cmdBits[2]);
					int id = Integer.parseInt(cmd.split(" ")[2]);//공백을 기준으로 나눠서 배열 3번째(012중 2)에 있는 거 가져옴
					System.out.printf("== %d번 게시글 수정 ==\n", id);

					SecSql sql = SecSql.from("SELECT COUNT(*)");
					sql.append("FROM article");
					sql.append("WHERE id = ?", id);
					
					int articleCount = DBUtil.selectRowIntValue(conn, sql);
					//articleCount == 0 으로 비교한 이유는 count함수를 썼기 때문
					//articleCount에는 컬럼에 해당하는 데이터를 가져옴.
					//id로 조회했다면 조회하려는 id 번호를 가져왔을 것임.
					if (articleCount == 0) {
						System.out.printf("%d번 게시글은 존재하지 않습니다\n", id);
						continue;
					}
					
					System.out.printf("수정할 제목 : ");
					String title = sc.nextLine();
					System.out.printf("수정할 내용 : ");
					String body = sc.nextLine();

					sql = SecSql.from("UPDATE article");
					sql.append("SET updateDate = NOW()");
					sql.append(", title = ?", title);
					sql.append(", `body` = ?", body);
					sql.append("WHERE id = ?", id);
					
					DBUtil.update(conn, sql);
					
					System.out.printf("%d번 게시글이 수정되었습니다\n", id);
					System.out.println();
					
				//글 삭제
				} else if (cmd.startsWith("article delete ")) {
					int id = Integer.parseInt(cmd.split(" ")[2]); 
					boolean existArticle = articleController.existingArticle(id);
					
					if (!existArticle) {
						System.out.printf("%d번 게시글은 존재하지 않습니다\n", id);
						continue;
					}
					
					System.out.printf("%d번 글이 삭제되었습니다.", id);
					System.out.println();
					
				//글 상세
				} else if (cmd.startsWith("article detail ")) {
					
					int id = Integer.parseInt(cmd.split(" ")[2]);	
					Map<String, Object> articleMap = articleController.showDetail(id);
					//DBUtil.selectRow는 받아올 데이터가 없을 때 new HashMap<>()을 리턴함.
					if (articleMap.isEmpty()) {
						System.out.printf("%d번 게시글은 존재하지 않습니다\n", id);
						continue;
					}
					
					Article article = new Article(articleMap);
					
					System.out.printf("== %d번 게시글 상세보기 ==\n", id);
					System.out.println("아이디: " + article.id);
					System.out.println("제목: " + article.title);
					System.out.println("내용: " + article.body);
					System.out.println("등록일: " + article.regDate);
					System.out.println("수정일: " + article.updateDate);
					System.out.println();
				
				//회원가입
				} else if (cmd.equals("member join")) {
					memberController.doJoin();

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
