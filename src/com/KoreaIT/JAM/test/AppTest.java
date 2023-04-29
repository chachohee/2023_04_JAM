package com.KoreaIT.JAM.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.JAM.Article;

public class AppTest {
	//1. db 연결
	//2. 데이터 write, list, modify, delete
	
	public void run() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); //ClassNotFoundException
			String url = "jdbc:mysql://localhost:3306/jdbc_article_manager";
			conn = DriverManager.getConnection(url, "root", ""); //SQLException
			System.out.println("DB 연결 성공!");
			
			System.out.println("=====프로그램 시작=====");
			while (true) {
				System.out.printf("명령어) ");
				String cmd = sc.nextLine().trim();
				
				if (cmd.equals("exit")) {
					System.out.println("=====프로그램 종료=====");
					break;	
				}
				
				//글 작성
				if (cmd.equals("article write")) {
					System.out.print("제목: ");
					String title = sc.nextLine();
					System.out.print("내용: ");
					String body = sc.nextLine();
					String query = "insert into article set "
							+ "regDate = now()"
							+ ", updateDate = now()"
							+ ", title = '" + title + "'"
							+ ", `body` = '" + body + "'" ;
					pstmt = conn.prepareStatement(query);
					pstmt.executeUpdate();
					System.out.println("글이 등록되었습니다.");
					System.out.println();
					
				//글 조회
				} else if (cmd.equals("article list")) {
					List<Article> articles = new ArrayList<>();
					String query = "select * from article";
					ResultSet rs = null;
					
					try {
						pstmt = conn.prepareStatement(query);
						rs = pstmt.executeQuery();
						
						while (rs.next()) {
							int id = rs.getInt("id");
							String regDate = rs.getString("regDate");
							String updateDate = rs.getString("updateDate");
							String title = rs.getString("title");
							String body = rs.getString("body");

							Article article = new Article(id, regDate, updateDate, title, body);
							articles.add(article);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							if (rs != null && !rs.isClosed()) {
								rs.close();
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if (articles.size() == 0) {
						System.out.println("존재하는 게시물이 없습니다");
						continue;
					}
					
					System.out.println("번호	|	제목");

					for (Article article : articles) {
						int id = article.getId();
						String title = article.getTitle();
						System.out.printf("%d	|	%s\n", id, title);
					}
					
					System.out.println();
					
				//글 수정
				} else if (cmd.startsWith("article modify ")) {
//					String[] cmdBits = cmd.split(" ");
//					int id = Integer.parseInt(cmdBits[2]);
					int id = Integer.parseInt(cmd.split(" ")[2]);//공백을 기준으로 나눠서 배열 3번째(012중 2)에 있는 거 가져옴
					System.out.println(id + "번 게시글 수정중...");
					System.out.print("수정할 제목 : ");
					String title = sc.nextLine();
					System.out.print("수정할 내용 : ");
					String body = sc.nextLine();
					
					String query = "update article set "
							+ "title = '" + title + "'"
							+ ", `body` = '" + body + "'"
							+ " where id = " + id;
					pstmt = conn.prepareStatement(query);
					pstmt.executeUpdate();
					System.out.println(id + "번 글이 수정되었습니다.");
					System.out.println();
					
				//글 삭제
				} else if (cmd.startsWith("article delete ")) {
					int id = Integer.parseInt(cmd.split(" ")[2]); 
					String query = "delete from article where id = " + id;
					pstmt = conn.prepareStatement(query);
					pstmt.executeUpdate();
					System.out.println(id + "번 글이 삭제되었습니다.");
					System.out.println();
				}
			} //end while(데이터 삽입, 조회, 수정, 삭제)
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
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