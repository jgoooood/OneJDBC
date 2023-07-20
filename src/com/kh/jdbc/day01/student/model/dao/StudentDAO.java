package com.kh.jdbc.day01.student.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day01.student.model.vo.Student;

//DAO : DB의 데이터를 객체화 함->VO에 넘겨주어야 함
public class StudentDAO {

	public List<Student> selectAll() {
		/*
		 * 1. 드라이버 등록 
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행
		 * 5. 결과받기
		 * 6. 자원해제(close())
		 */
		//db연결에 필요한 정보들을 변수에 초기화
		String driverName = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; //xe는 대소문자 구분 안함
		String user = "student"; //오라클 계정 아이디, 비번 대소문자 구분함
		String password = "student";
		String query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = null; //리스트변수 선언
		try {
			// 1. 드라이버 등록 
			// Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName(driverName);
			// 2. DB 연결 생성 (DriverManager)
			Connection conn = DriverManager.getConnection(url, user, password);
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			// 4. 쿼리문 실행 : stmt.executeQuery(query) / 5. 결과받기 : ResultSet rset
			// 쿼리문이 select라 *ResultSet* 
			ResultSet rset = stmt.executeQuery(query); //rset은 데이터정보를 갖고있음->후처리를 통해 출력준비
			//리스트에 값을 넣어줌 while문에서 제외시켜야함
			sList = new ArrayList<Student>();  //위에서 변수선언하고 아래에서 객체생성완료
			// 후처리 -> 반복문을 통해 Student객체에 넣어줌
			while(rset.next()) { //rset에 다음 값이 있으면 반복
				Student student = new Student(); //객체생성
				//Student
				student.setStudentId(rset.getString("STUDENT_ID"));
				student.setStudentPwd(rset.getString("STUDENT_PWD"));
				student.setStudentName(rset.getString("STUDENT_NAME"));
				student.setAge(rset.getInt("AGE"));
				student.setEmail(rset.getString("EMAIL"));
				student.setPhone(rset.getString("PHONE"));
				// 문자는 문자열에서 문자로 잘라서 사용 -> charAt()메소드사용
				student.setGender(rset.getString("GENDER").charAt(0)); 
				student.setAddress(rset.getString("ADDRESS"));
				student.setHobby(rset.getString("HOBBY"));
				student.setEnrollDate(rset.getDate("ENROLL_DATE"));
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

}
