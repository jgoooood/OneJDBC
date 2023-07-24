package com.kh.jdbc.day03.student.view;

import java.util.List;
import java.util.Scanner;

import com.kh.jdbc.day03.student.controller.StudentController;
import com.kh.jdbc.day03.student.model.vo.Student;

public class StudentView {
	private StudentController controller = null;
	public StudentView() {
		controller = new StudentController();
	}
	
	public void studentProgram() {
		List<Student> sList = null;
		Student student = null;
		int result = -1;
		String studentId = "";
		String studentName = "";
		theEnd:
		while(true) {
			int input = printMenu();
			switch(input) {
				case 1: 
					sList = controller.selectAllStudent();
					printAllStudent(sList);
					break;
				case 2: 
					//아이디 입력받는 메소드
					studentId = inputStdId("검색");
					student = controller.selectOneById(studentId);
					printStudentById(student);
					break;
				case 3: 
					//이름으로 입력받는 메소드
					studentName = inputStdName();
					sList = controller.selectAllByName(studentName);
					printStudentById(student);
					break;
				case 4: 
					//학생정보등록
					student = inputStudent();
					result = controller.insertStudent(student);
					if(result > 0) System.out.println("학생정보 등록에 성공했습니다.");
					else System.out.println("학생정보 등록에 실패했습니다.");
					break;
				case 5: 
					//학생정보수정
					//SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'admin';
					studentId = inputStdId("수정"); //->아이디가 있으면 조회됨
					student = controller.selectOneById(studentId);
					if(student != null) {
						student = modifyStudent();
						student.setStudentId(studentId);
						result = controller.updateStudent(student);						
					} else {
						System.out.println("일치하는 아이디가 없습니다.");
					}
					if(result > 0) System.out.println("학생정보 수정에 성공했습니다.");
					else System.out.println("학생정보 수정에 실패했습니다.");
					break;
				case 6: 
					//학생정보삭제
					studentId = inputStdId("삭제");
					result = controller.deleteStudent(studentId);
					if(result > 0) System.out.println("학생정보 삭제에 성공했습니다.");
					else System.out.println("학생정보 삭제에 실패했습니다.");
					break;
				case 9: 
					// SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'admin' AND SUTDENT_PWD = 'admin'
					student = inputLoginInfo();
					//DAO에 Statement인터페이스 사용 -> 로그인 정보 입력시 아이디에 'or'1'='1'--를 넣으면
					//입력검증을 거치지 않아 맞지 않은 비번을 입력하지 않아도 로그인성공됨->preparedstatement사용
					//preparedstatement 사용시 인젝션공격을 막을 수 있음
					student = controller.studentLogin(student); //학생정보가 재할당되면서 넘어옴
					if(student != null) {
						//로그인 성공
						displaySuccess("로그인 성공");
					}else {
						//로그인 실패
						displayError("해당 정보가 존재하지 않습니다.");
					}
					break;
				case 0:
					System.out.println("프로그램을 종료합니다.");
					break theEnd;
			}
		}
	}


	private void displaySuccess(String message) {
		System.out.println("[서비스 성공] : " + message);
	}

	private void displayError(String message) {
		System.out.println("[서비스 실패] : " + message);
	}


	private Student inputLoginInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("======= 학생 로그인 =======");
		System.out.print("아이디 : ");
		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPW = sc.next();
		Student student = new Student(studentId, studentPW);
		//System.out.println(student.toString());
		return student;
	}

	private Student modifyStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.print("비밀번호 : ");
		String studentPW = sc.next();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine(); //전화번호 치고 난 엔터를 제거(공백제거)
		String address = sc.nextLine();
		System.out.print("취미(,로 구분) : ");
		String hobby = sc.next();
		Student student = new Student(studentPW, email, phone, address, hobby);
		return student;
	}

	private Student inputStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.print("아이디 : ");
		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPW = sc.next();
		System.out.print("이름 : ");
		String studentName = sc.next();
		System.out.print("성별 : ");
		char gender = sc.next().charAt(0);
		System.out.print("나이 : ");
		int age = sc.nextInt();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine(); //전화번호 치고 난 엔터를 제거(공백제거)
		String address = sc.nextLine();
		System.out.print("취미(,로 구분) : ");
		String hobby = sc.next();
		Student student = new Student(studentId, studentPW, studentName, gender, age, email, phone, address, hobby);
		//set메소드로 저장하지 않고 생성자의 매개변수로 데이터를 저장함
		return student;
	}

	private String inputStdName() {
		Scanner sc = new Scanner(System.in);
		System.out.print("검색할 이름 입력 : ");
		String studentName = sc.next();
		return studentName;
	}

	private void printStudentById(Student student) {
		System.out.println("======= 학생 정보 출력(아이디로 조회) =======");
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

	private String inputStdId(String category) {
		Scanner sc = new Scanner(System.in);
		System.out.print(category+"할 아이디 입력 : ");
		String studentId = sc.next();
		return studentId;
	}

	private void printAllStudent(List<Student> sList) {
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


	private int printMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("========= 학생관리 프로그램 =========");
		System.out.println("9. 학생 로그인");
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
