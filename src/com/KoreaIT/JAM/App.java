package com.KoreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
	public void run() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
			conn = DriverManager.getConnection(url, "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		
		System.out.println("== 프로그램 시작 ==");
		while (true) {
			List<Article> articles = new ArrayList<>();
			System.out.printf("명령어) ");
			String cmd = sc.nextLine();

			if (cmd.equals("exit")) {
				break;
			}

			if (cmd.equals("article write")) {
				
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();
				
				try {
					String sql = "insert into article";
					sql += " set regDate = now(),";
					sql += " updateDate = now(),";
					sql += " title = '" + title + "'";
					sql += " , `body` = '" + body + "'";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.executeUpdate();
					System.out.println("글이 등록되었습니다.");
					
				} catch (SQLException e) {
					System.out.println("에러: " + e);
				}

			} else if (cmd.equals("article list")) {
				try {
					String sql = "select * from article";
					pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						int id = rs.getInt("id");
						String title = rs.getString("title");
						
						Article article = new Article();
						article.setId(id);
						article.setTitle(title);
						articles.add(article);
					}
					rs.close();
				} catch (SQLException e) {
					System.out.println("에러: " + e);
				} 
				
				if (articles.size() == 0) {
					System.out.println("존재하는 게시물이 없습니다");
					continue;
				}

				System.out.println("번호	|	제목	");

				for (int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);

					System.out.printf("%d	|	%s	\n", article.id, article.title);
				}
				
			} else if (cmd.startsWith("article modify ")){
//				String[] cmdBits = cmd.split(" ");
//				int id = Integer.parseInt(cmdBits[2]);
				int id = Integer.parseInt(cmd.split(" ")[2]);
				System.out.println(id + "번 게시글 수정중...");
				
				System.out.printf("수정할 제목 : ");
				String modTitle = sc.nextLine();
				System.out.printf("수정할 내용 : ");
				String modBody = sc.nextLine();
				try {
					String sql = "update article set title = '";
					sql += modTitle + "', `body` = '";
					sql += modBody + "' ";
					sql += "where id = ";
					sql += + id;
					
					pstmt = conn.prepareStatement(sql);
					pstmt.executeUpdate();

				} catch (SQLException e) {
					System.out.println("에러: " + e);
				} 
				System.out.println(id + "번 글이 수정되었습니다.");
			}
		}//end while
		
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
		
		sc.close();
		}//end finally(자원 끄는 거-> pstmt랑 conn)
		System.out.println("== 프로그램 끝 ==");
	}

}
