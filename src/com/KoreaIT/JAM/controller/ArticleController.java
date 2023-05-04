package com.KoreaIT.JAM.controller;

import java.sql.Connection;
import java.util.Map;
import java.util.Scanner;

import com.KoreaIT.JAM.service.ArticleService;

public class ArticleController {
	private Scanner sc;
	private ArticleService articleService;
	
	public ArticleController(Connection conn, Scanner sc) {
		this.sc = sc;
		articleService = new ArticleService(conn);
	}
	
	public boolean existingArticle(int id) {
		return articleService.existingArticle(id);
	}
	
	public void doWrite() {
		System.out.println("== 게시물 작성 ==");
		System.out.print("제목: ");
		String title = sc.nextLine();
		System.out.print("내용: ");
		String body = sc.nextLine();
		int id = articleService.doWrite(title, body);
		System.out.printf("%d번 게시글이 생성되었습니다\n", id);
		System.out.println();
	}
	
	public Map<String, Object> showDetail(int id) {
		return articleService.showDetail(id);
	}
	
	public void showList() {
		articleService.showList();
	}
	
	public void doModify() {
		articleService.doModify();
	}
	
	public void doDelete(int id) {
		articleService.doDelete(id);
	}
}
