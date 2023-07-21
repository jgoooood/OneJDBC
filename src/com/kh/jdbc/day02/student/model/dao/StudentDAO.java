package com.kh.jdbc.day02.student.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day02.student.model.vo.Student;

//DAO : DB의 데이터를 객체화 함->VO에 넘겨주어야 함
public class StudentDAO {
	//db연결에 필요한 정보들을 변수에 초기화->필드에 초기화해서 전역변수 됨
	//모든 메소드가 접근해서 사용가능->db정보는 바뀌지 않기 때문에 상수로 선언
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; //xe는 대소문자 구분 안함
	private final String USER = "student"; //오라클 계정 아이디, 비번 대소문자 구분함
	private final String PASSWORD = "student";
	Student student = null;
	
	public List<Student> selectAll() {
		/*
		 * 1. 드라이버 등록 
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행
		 * 5. 결과받기
		 * 6. 자원해제(close())
		 */
		String query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = null; //리스트변수 선언
		try {
			// 1. 드라이버 등록 
			// Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName(DRIVER_NAME);
			// 2. DB 연결 생성 (DriverManager)
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			// 4. 쿼리문 실행 : stmt.executeQuery(query) / 5. 결과받기 : ResultSet rset
			// 쿼리문이 select라 *ResultSet* 
			ResultSet rset = stmt.executeQuery(query); //rset은 데이터정보를 갖고있음->후처리를 통해 출력준비
			//리스트에 값을 넣어줌 while문에서 제외시켜야함
			sList = new ArrayList<Student>();  //위에서 변수선언하고 아래에서 객체생성완료
			// 후처리 -> 반복문을 통해 Student객체에 넣어줌
			while(rset.next()) { //rset에 다음 값이 있으면 반복
				student = rsetToStudent(rset); //객체생성
				sList.add(student); //한명의 정보 추가 완료->한명이 하나의 객체가 되는 것
			} // ========= 모든 사람의 정보를 불러와서 각 변수에 값을 대입
			  // ->값이 없을 때까지 반복 -> 리스트에 한명씩 정보 추가함
			// 6. 자원해제
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sList; //전체정보를 가지고 있는 sList 출력
	}

	public Student selectOneById(String studentId) {
		// 쿼리문 : SELECT * FROM STUDENT_TBL WHERE STUDENT_ID='admin';
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID='"+studentId+"'";
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 -> 5. 결과받기
		 * 6. 자원해제
		 */
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement(); //쿼리문실행준비
			ResultSet rset = stmt.executeQuery(query);
			//while(rset.netxt()){} : 결과가 여러개면 while문 사용
			//결과한개면 if문 사용함
			if(rset.next()) {
				student = rsetToStudent(rset);
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	public List<Student> selectAllByName(String studentName) {
		// 쿼리문 : SELECT * FROM STUDENT_TBL WHERE STUDENT_ID='admin';
				String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME ='"+studentName+"'";
				List<Student> sList = new ArrayList<Student>(); //리턴용 변수선언
				/*
				 * 1. 드라이버 등록
				 * 2. DB 연결 생성
				 * 3. 쿼리문 실행 준비
				 * 4. 쿼리문 실행 -> 5. 결과받기
				 * 6. 자원해제
				 */
				try {
					Class.forName(DRIVER_NAME);
					Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
					Statement stmt = conn.createStatement(); //쿼리문실행준비
					ResultSet rset = stmt.executeQuery(query);
					//while(rset.netxt()){} : 결과가 여러개면 while문 사용
					//결과한개면 if문 사용함
					while(rset.next()) {
						student = rsetToStudent(rset);
						sList.add(student);
					}
					rset.close();
					stmt.close();
					conn.close();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
		return sList;
	}

	//학생정보를 갖고 sql에 인서트할 준비
	public int insertStudent(Student student) {
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 -> 5. 결과받기
		 * 6. 자원해제
		 */
		//쿼리문 변수선언
		//INSERT INTO STUDENT_TBL VALUES('admin', 'admin', '관리자', 'M', 30, 'admin@iei.or.kr', '01012345678', '서울시 강남구 역삼동 테헤란로 7', '기타,독서,운동', SYSDATE);
		// ' ' <- 보내야하는값이기 때문->'"+get값1+"', '"+get값2+"', '"+get값3+"','"+get값4+"'로 사용
		String query = "INSERT INTO STUDENT_TBL VALUES('"+student.getStudentId()+"', '"+student.getStudentPwd()+"', '"+student.getStudentName()+"', '"+student.getGender()+"', "+student.getAge()+", '"+student.getEmail()+"', '"+student.getPhone()+"' , '"+student.getAddress()+"', '"+student.getHobby()+"', SYSDATE)";
		//쿼리문 실행 결과를 받을 변수 result선언(숫자로 반환되기 때문에 int로 선언해야함)
		int result = -1;
		try {
			// 1. 드라이버 등록
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName(DRIVER_NAME);
			// 2. DB연결생성메소드 : DriverManager.getConnection -> Connection인터페이스 타입의 변수로 저장함
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			// 3. 쿼리문 실행 준비메소드 : createStatement -> Statement인터페이스의 변수에 저장
			Statement stmt = conn.createStatement();
			// 4. 쿼리문 실행하고 결과 받기 
			//stmt.execute(query); -> SELECT용 실행 메소드
			//stmt.executeUpdate(query) -> DML(INSERT, UPDATE, DELETE)용 실행메소드
			//either (1) the row count for SQL Data Manipulation Language (DML) 
			//statementsor (2) 0 for SQL statements that return nothing
			//1또는 2를 반환하기 때문에 int타입의 결과값을 받아야함
			result = stmt.executeUpdate(query);
			// 6. 자원해제
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result; //result 리턴->int값으로 리턴함
	}

	public int updateStudent(Student student) {
	//		UPDATE STUDENT_TBL 
	//		SET STUDENT_PWD = 'pass11', EMAIL = 'khuser04@iei.co.kr', PHONE = '01011114444'
	//		, ADDRESS = '서울시 강남구' , HOBBY = '코딩,수영' 
	//		WHERE STUDENT_ID = 'khuser04';
	//		String query = "UPDATE STUDENT_TBL SET STUDENT_PWD = '"+student.getStudentPwd()+"', EMAIL = '"+student.getEmail()+"', PHONE = '"+student.getPhone()+"', ADDRESS = '"+student.getAddress()+"' , HOBBY = '"+student.getHobby()+"' WHERE STUDENT_ID = '"+student.getStudentId()+"'";
	//		쿼리문 오류는 자바가 잡아내지 못함->쿼리문 입력 잘하기
			String query = "UPDATE STUDENT_TBL SET "
					+ "STUDENT_PWD = '"+student.getStudentPwd()+"', "
							+ "EMAIL = '"+student.getEmail()+"', "
									+ "PHONE = '"+student.getPhone()+"', "
											+ "ADDRESS = '"+student.getAddress()+"' , "
													+ "HOBBY = '"+student.getHobby()+"' "
															+ "WHERE STUDENT_ID = '"+student.getStudentId()+"'";
	
			int result = -1;
			try {
				Class.forName(DRIVER_NAME);
				Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement();
				result = stmt.executeUpdate(query);
				
				stmt.close();
				conn.close();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

	public int deleteStudent(String studentId) {
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID ='"+studentId+"'";
		int result = -1;
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
	
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	//rset을 student객체로 만들어주는 메소드->rset을 전달받아서 사용
	//rset.getString("STUDENT_ID") 예외처리 throws로
	private Student rsetToStudent(ResultSet rset) throws SQLException {
		Student student = new Student();
		student.setStudentId(rset.getString("STUDENT_ID"));
		student.setStudentPwd(rset.getString("STUDENT_PWD"));
		student.setStudentName(rset.getString("STUDENT_NAME"));
		student.setAge(rset.getInt("AGE"));
		student.setEmail(rset.getString("EMAIL"));
		student.setPhone(rset.getString("PHONE"));
		student.setGender(rset.getString("GENDER").charAt(0)); 
		student.setAddress(rset.getString("ADDRESS"));
		student.setHobby(rset.getString("HOBBY"));
		student.setEnrollDate(rset.getDate("ENROLL_DATE"));
		return student;
	}
}
