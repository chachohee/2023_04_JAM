package com.KoreaIT.JAM.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import com.KoreaIT.JAM.dto.Article;
import com.KoreaIT.JAM.service.ArticleService;
import com.KoreaIT.JAM.session.Session;
import com.KoreaIT.JAM.util.Util;

public class ArticleController {
	private Scanner sc;
	private ArticleService articleService;
	
	public ArticleController(Connection conn, Scanner sc) {
		this.sc = sc;
		articleService = new ArticleService(conn);
	}
	
	public void doWrite() {
		if(!Session.isLogined()) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}
		int memberId = Session.loginedMemberId;
		System.out.println("== 게시물 작성 ==");
		System.out.print("제목: ");
		String title = sc.nextLine();
		System.out.print("내용: ");
		String body = sc.nextLine();
		int id = articleService.doWrite(memberId, title, body);
		System.out.printf("%d번 게시글이 생성되었습니다.\n", id);
	}
	
	public void showDetail(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);
		int afftectedRow = articleService.updateCount(id);//조회수 증가
		Article article = articleService.getArticle(id);
		if (afftectedRow == 0) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}
		
		System.out.printf("== %d번 게시글 상세보기 ==\n", id);
		System.out.println("글번호: " + article.id);
		System.out.println("조회수: " + article.count);
		System.out.println("작성자: " + article.writerName);
		System.out.println("제목: " + article.title);
		System.out.println("내용: " + article.body);
		System.out.println("등록일: " + Util.datetimeFormat(article.regDate));
		System.out.println("수정일: " + Util.datetimeFormat(article.updateDate));
	}
	
	public void showList(String cmd) {
		//article list 다음에 검색어 오면 검색한 거 보여주고 없으면 그냥 전체 리스트 보여주기.
		String keyword = cmd.substring("article list".length()).trim();
		
		List<Article> articles = articleService.getArticles(keyword);
		if (articles.size() == 0) {
			System.out.println("존재하는 게시물이 없습니다.");
			return;
		}
		System.out.println("== 게시물 리스트 ==");
		if(keyword.length() > 0) {
			System.out.println("검색어: " + keyword);
		}
		System.out.println("번호	|	제목		|	작성자	|	조회수	|	날짜");
		for (Article article : articles) {
			System.out.printf("%d	|	%s	|	%s	|	%s	|	%s\n", article.id, article.title, article.writerName, article.count, Util.datetimeFormat(article.regDate));
		}
	}
	
	public void doModify(String cmd) {
		if(!Session.isLogined()) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}
		// String[] cmdBits = cmd.split(" ");
		// int id = Integer.parseInt(cmdBits[2]);
		int id = Integer.parseInt(cmd.split(" ")[2]);//공백을 기준으로 나눠서 배열 3번째(012중 2)에 있는 거 가져옴.
		//권한 체크
		Article article = articleService.getArticle(id);
		if (article == null) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}
		if(Session.loginedMemberId != article.memberId) {
			System.out.println("작성자가 일치하지 않습니다.");
			return;
		}
		System.out.printf("== %d번 게시글 수정 ==\n", id);
		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine();

		id = articleService.doModify(id, title, body);
		System.out.printf("%d번 게시글이 수정되었습니다.\n", id);
	}
	
	public void doDelete(String cmd) {
		if(!Session.isLogined()) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}
		int id = Integer.parseInt(cmd.split(" ")[2]); 
		//권한 체크
		Article article = articleService.getArticle(id);
		if(article == null) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}
		if(Session.loginedMemberId != article.memberId) {
			System.out.println("작성자가 일치하지 않습니다.");
			return;
		}
		id = articleService.doDelete(id);
		System.out.printf("%d번 글이 삭제되었습니다.", id);
	}
}
