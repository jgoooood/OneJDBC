package com.kh.jdbc.day02.student.view;

import java.util.List;
import java.util.Scanner;

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
		Student student = null;
		List<Student> sList = null;
		String studentId = null;
		finish:
		while(true) {
			int choice = printMenu();
			switch(choice) {
				case 1 : 
					// 스튜던트 리스트 가져오기 
					// 쿼리문 : SELECT * FROM STUDENT_TBL 전제정보이기 때문에 전달값 따로 없음
					sList = controller.printAllStudent();
					//정보가 있으면 출력 : isEmpty()
					if(!sList.isEmpty()) {
						showAllStudent(sList);						
					} else {
						displayError("학생 정보가 조회되지 않습니다.");
					}
					break;
				case 2 : 
					//아이디로 조회하는 쿼리문 생각해보기(리턴형과 매개변수 생각)
					//SELECT * FROM STUDENT_TBL WHERE STUDENT_ID='admin';
					//학생정보 하나니 리스트x Student 객체를 사용함
					studentId = inputStudentId(); // 아이디 입력받는 메소드
					student = controller.printStudentById(studentId);
					//null체크
					if(student != null) {
						showStudent(student);
					} else {
						displayError("학생 정보가 존재하지 않습니다.");
					}
					//printStudentById()메소드가 학생정보를 조회->dao메소드 : selectOneById()
					//showStudent()메소드로 학생 정보를 출력
					break;
				case 3 : 
					// 리턴형? 매개변수? 
					// 쿼리문 : SELECT * FROM STUDENT_TBL WHERE STUDENT_ID='삼용자';
					// printStudentByName / selectAllByName
					String studentName = inputStudentName();
					sList = controller.printStudentByName(studentName);
					if(!sList.isEmpty()) {
						showAllStudent(sList);
					} else {
						displayError("학생 정보가 조회되지 않습니다.");
					}
					break;
				case 4 : 
					//INSERT INTO STUDENT_TBL VALUES('admin', 'admin', '관리자', 'M', 30, 'admin@iei.or.kr'
					//, '01012345678', '서울시 강남구 역삼동 테헤란로 7', '기타,독서,운동', SYSDATE);
					student = inputStudent(); // 입력을 받을 메소드 추가->Student객체에 추가
					//추가한 학생정보를 가진 return값이 student변수에 저장됨
					int result = controller.insertStudent(student); //input정보를 담은 student변수를 매개변수로 받아서 sql에 등록->쿼리문으로
					// insert시 sql완료쿼리 : '1행이 삽입되었습니다' 1을 받기위해 결과값을 int를 사용->후처리 사용할 필요없음
					if(result > 0) { //result가 0보다 크면 삽입 성공
						//성공메시지 출력
						displaySuccess("학생 정보 등록 성공");
					} else {
						//실패메시지 출력
						displayError("학생 정보 등록 실패");
					}
					break;
				case 5 : 
//					UPDATE STUDENT_TBL 
//					SET STUDENT_PWD = 'pass11', EMAIL = 'khuser04@iei.co.kr', PHONE = '01011114444'
//					, ADDRESS = '서울시 강남구' , HOBBY = '코딩,수영' 
//					WHERE STUDENT_ID = 'khuser04';
					student = modifyStudent();
					result = controller.modifyStudent(student);
					if(result > 0) { //result가 0보다 크면 수정 성공
						//성공메시지 출력
						displaySuccess("학생 정보가 변경되었습니다.");
					} else {
						//실패메시지 출력
						displayError("학생 정보 수정되지 않았습니다.");
					}
					break;
				case 6 : 
					//삭제 : 쿼리문, 매개변수 유무, 반환형
					//
					studentId = inputStudentId(); // 입력을 받을 메소드 추가->Student객체에 추가
					result = controller.deleteStudent(studentId);
					if(result>0) {
						//성공메시지
						displaySuccess("학생 정보 삭제 성공");
					}else {
						//실패메시지
						displayError("삭제가 완료되지 않았습니다.");
					}
					break;
				case 0 : 
					System.out.println("프로그램을 종료합니다");
					break finish;
			}
		}
	}
	
	

	private Student modifyStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 정보 수정 =====");
		System.out.print("아이디 : ");
		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPW = sc.next();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine(); 
		String address = sc.nextLine();
		System.out.print("취미(,로 구분) : ");
		String hobby = sc.next();
		Student student = new Student(studentId, studentPW, email, phone, address, hobby);
		return student;
	}

	private String inputStudentName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("======= 학생 이름으로 조회 =======");
		System.out.print("학생 이름 입력 : ");
		String studentName = sc.next();
		return studentName;
	}

	private String inputStudentId() {
		Scanner sc = new Scanner(System.in);
		System.out.println("======= 학생 아이디로 조회 =======");
		System.out.print("학생 아이디 입력 : ");
		String studentId = sc.next();
		return studentId;
	}

	//학생정보를 입력받는 메소드->Student객체를 생성해서 데이터를 전달함
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

	private void displaySuccess(String message) {
		System.out.println("[서비스 성공] : " + message);
	}

	private void displayError(String message) {
		System.out.println("[서비스 실패] : " + message);
	}

	private void showStudent(Student student) {
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
