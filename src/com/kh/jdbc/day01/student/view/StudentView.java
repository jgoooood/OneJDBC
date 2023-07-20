package com.kh.jdbc.day01.student.view;

import java.util.*;

import com.kh.jdbc.day01.student.controller.StudentController;
import com.kh.jdbc.day01.student.model.vo.Student;

//컨트롤러 정보요청
public class StudentView {
	//필드
	private StudentController controller;
	//생성자
	public StudentView() {
		controller = new StudentController();
	}
	
	//프로그램 스타트 메소드
	public void startProgram() {
		finish:
		while(true) {
			int choice = printMenu();
			switch(choice) {
				case 1 : 
					// 스튜던트 리스트 가져오기 
					// 쿼리문 : SELECT * FROM STUDENT_TBL 전제정보이기 때문에 전달값 따로 없음
					List<Student> sList = controller.printAllStudent();
					showAllStudent(sList);
					break;
				case 2 : 
					break;
				case 3 : 
					break;
				case 4 : 
					break;
				case 5 : 
					break;
				case 6 : 
					break;
				case 0 : 
					System.out.println("프로그램을 종료합니다");
					break finish;
			}
		}
	}
	//학생 정보 출력 메소드
	private void showAllStudent(List<Student> sList) {
		System.out.println("========= 학생 전체 정보 출력 =========");
		for(Student student : sList) {
			System.out.printf("이름 : %s, 나이 : %d, 아이디 : %s, 성별 : %s, 이메일 : %s, 전화번호 : %s, 주소 : %s, 취미 : %s, 가입날짜 : %s\n"
					, student.getStudentName()
					, student.getAge()
					, student.getStudentId()
					, student.getGender()
					, student.getEmail()
					, student.getPhone()
					, student.getAddress()
					, student.getHobby()
					, student.getEnrollDate());
		}
	}

	//프린트메뉴 메소드
	public int printMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("========= 학생관리 프로그램 =========");
		System.out.println("1. 학생 전체 조회");
		System.out.println("2. 학생 아이디로 조회");
		System.out.println("3. 학생 이름으로 조회");
		System.out.println("4. 학생 정보 등록");
		System.out.println("5. 학생 정보 수정");
		System.out.println("6. 학생 정보 삭제");
		System.out.println("0. 프로그램 종료");
		System.out.print("메뉴선택 : ");
		int input = sc.nextInt();
		return input;
	}
}
