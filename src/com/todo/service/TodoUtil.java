package com.todo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
		
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("제목은 중복이 불가합니다");
			return;
		}
		
		System.out.println("내용을 입력하시오");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title = sc.nextLine();
		
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
		String title = sc.nextLine().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("해당 제목이 존재하지 않습니다.t");
			return;
		}

		System.out.println("아이템의 새로운 제목을 입력하시오");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복이 불가합니다");
			return;
		}
		
		System.out.println("수정할 아이템의 새 내용을 입력하시오");
		String new_description = sc.nextLine().trim();
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
	
	public static void saveList(TodoList l, String filename) {
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(new File(filename));
			
			for (TodoItem item : l.getList()) {
				fw.write(item.getTitle() + "##" + item.getDesc());
				fw.write("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					System.out.println("아이템을 모두 저장했습니다.");
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		File file = new File(filename);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		BufferedReader bfr = null;
		try {
			bfr = new BufferedReader(new FileReader(filename));
			String currentLine;
			
			while ((currentLine = bfr.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(currentLine,"##"); 
				while (st.hasMoreTokens()) {
					String title = st.nextToken();
					String desc = st.nextToken();
					
					TodoItem t = new TodoItem(title, desc);
					l.addItem(t);
				}
//				System.out.println(st);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			System.out.println("아이템을 모두 불러왔습니다.");
			try {
				if (bfr != null) {
					
					bfr.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}	
	}
}
