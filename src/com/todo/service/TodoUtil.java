package com.todo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== 아이템 생성\n"
				+ "카테고리를 입력하시오\n");
		
		category = sc.nextLine();
		
		System.out.println("제목을 입력하시오\n");
		
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("제목은 중복이 불가합니다");
			return;
		}
		
		System.out.println("내용을 입력하시오");
		desc = sc.nextLine();
		
		System.out.println("마감일자를 입력하시오");
		due_date = sc.nextLine();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t);
		Date date = new Date();
		t.setCurrent_date(date);
		System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		ArrayList<TodoItem> list = l.getList();
		String title = sc.nextLine();
		
		System.out.println("\n"
				+ "========== 아이템 삭제\n"
				+ "지울 아이템의 번호를 입력하시오\n"
				+ "\n");
		int num = sc.nextInt();
		
		if (list.size() < num || num < 1) {
			System.out.println("해당 번호가 존재하지 않습니다.t");
			return;
		}
		
		TodoItem myitem = list.get(num-1);
		System.out.println(num + ". [" + myitem.getCategory() + "] " + myitem.getTitle() + " - " + myitem.getDesc() + " - " + myitem.getDue_date() + " - " + myitem.getCurrent_date());
		
		l.deleteItem(myitem);
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		ArrayList<TodoItem> list = l.getList();
		
		System.out.println("\n"
				+ "========== 수정 부분\n"
				+ "수정할 항목의 번호를 입력하시오\n"
				+ "\n");
		int num = sc.nextInt();
		if (list.size() < num || num < 1) {
			System.out.println("해당 번호가 존재하지 않습니다.t");
			return;
		}
		
		TodoItem myitem = list.get(num-1);
		System.out.println(num + ". [" + myitem.getCategory() + "] " + myitem.getTitle() + " - " + myitem.getDesc() + " - " + myitem.getDue_date() + " - " + myitem.getCurrent_date());
		
		System.out.println("수정할 아이템의 새 카테고리를 입력하시오");
		String new_category = sc.nextLine().trim();

		System.out.println("아이템의 새로운 제목을 입력하시오");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복이 불가합니다");
			return;
		}
		
		System.out.println("수정할 아이템의 새 내용을 입력하시오");
		String new_description = sc.nextLine().trim();
		
		System.out.println("수정할 아이템의 새 마감 일자를 입력하시오");
		String new_due_date = sc.nextLine().trim();
		
		for (int i = 0; i < list.size(); i++) {
			if (i == num-1) {
				l.deleteItem(list.get(i));
				TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
		}
	}
	
	public static void findItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String keyword = sc.nextLine().trim();
		int count = 0;
		
		for (TodoItem myitem : l.getList()) {
			if (myitem.getTitle().contains(keyword) || myitem.getDesc().contains(keyword)) {
				System.out.println((l.indexOf(myitem)+1) + ". [" + myitem.getCategory() + "] " + myitem.getTitle() + " - " + myitem.getDesc() + " - " + myitem.getDue_date() + " - " + myitem.getCurrent_date());
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}

	public static void listAll(TodoList l) {
		ArrayList<TodoItem> list = l.getList();
		System.out.println("[전체 목록, 총  " + list.size() + "개]");
		
		for (TodoItem item : list) {
			System.out.println("제목: " + item.getTitle() + "  내용: " + item.getDesc());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(new File(filename));
			
			for (TodoItem item : l.getList()) {
				fw.write(item.toSaveString());
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
					String category = st.nextToken();
					String title = st.nextToken();
					String desc = st.nextToken();
					String date = st.nextToken();
					String due_date = st.nextToken();

					SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date to = null;
					try {
						to = transFormat.parse(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					TodoItem t = new TodoItem(category, title, desc, due_date);
					t.setCurrent_date(to);
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
