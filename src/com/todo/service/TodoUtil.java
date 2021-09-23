package com.todo.service;

import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== 아이템 생성\n"
				+ "제목을 입력하시오\n");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("제목은 중복이 불가합니다");
			return;
		}
		
		System.out.println("내용을 입력하시오");
		desc = sc.next();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title = sc.next();
		
		System.out.println("\n"
				+ "========== 아이템 삭제\n"
				+ "지울 아이템의 제목을 입력하시오\n"
				+ "\n");
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== 수정 부분\n"
				+ "수정할 이이템의 새 제목을 입력하시오\n"
				+ "\n");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("해당 제목이 존재하지 않습니다.t");
			return;
		}

		System.out.println("아이템의 새로운 제목을 입력하시오");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복이 불가합니다");
			return;
		}
		
		System.out.println("수정할 아이템의 새 내용을 입력하시오");
		String new_description = sc.next().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println("제목: " + item.getTitle() + "  내용: " + item.getDesc());
		}
	}
}
