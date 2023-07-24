package com.kh.jdbc.day03.student.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day03.student.model.vo.Student;

public class StudentDAO {
	
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USER = "student";
	private final String PASSWORD = "student";
	
	//select all은 전달 값이 없기 때문에 보통 Statement사용해도 됨
	public List<Student> selectAllStudent() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		List<Student> sList = new ArrayList<Student>();
		String query = "SELECT * FROM STUDENT_TBL";
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			while(rset.next()) {
				Student student = rsetToStudent(rset);
				sList.add(student);
			}
//			만약 코드 중간에 예외가 발생할 경우 close메소드가 작동안하기 때문에 
//			finally에서 코드를 종료함->변수들은 모두 전역변수로 선언되어야함.
//			rset.close();
//			stmt.close();
//			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally { //close메소드는 finally에 작성->예외처리도 진행해야함
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sList;
	}

	public Student selectOneById(String studentId) {
		//1. 위치홀더 세팅
		//2. PreparedStatement 객체 생성 with query
		//3. 입력값 세팅
		//4. 쿼리문 실행 및 결과 받기(feat.method())
		//String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = '"+studentId+"'";
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ?";
		Student student = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			//Statement stmt = conn.createStatement();
			//ResultSet rset = stmt.executeQuery(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				student = rsetToStudent(rset);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return student;
	}

	public List<Student> selectAllByName(String studentName) {
		//String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '"+studentName+"'";
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = ?";
		List<Student> sList = new ArrayList<Student>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			//Statement stmt = conn.createStatement();
			//ResultSet rset = stmt.executeQuery(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentName);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Student student = rsetToStudent(rset);
				sList.add(student);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		return sList;
	}

	public Student selectLoginInfo(Student student) {
		//System.out.println(student.toString());
		//SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'admin' AND STUDENT_PWD = 'admin';
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ? AND STUDENT_PWD = ?";
		Student result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			//Statement stmt = conn.createStatement(); -> Statement는 쿼리문을 컴파일하지 않기 때문에 매개변수로 전달해주어야함.
			//ResultSet rset = stmt.executeQuery(query);
			pstmt = conn.prepareStatement(query); //쿼리를 먼저 컴파일하고 준비
			pstmt.setString(1, student.getStudentId()); // 물음표에 넣을 값을 세팅해줌
			pstmt.setString(2, student.getStudentPwd()); // 첫번째 인자는 물음표 순서, 두번째인자는 넣어줄 값
			//시작은 1, 마지막은 물음표의 개수와 같음(물음표->위치홀더라고 부름)
			rset = pstmt.executeQuery(); //쿼리문이 이미 실행되었기 때문에 전달값 필요없음->실행만 하면됨
			if(rset.next()) {
				result = rsetToStudent(rset);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}

	public int insertStudent(Student student) {
//		INSERT INTO STUDENT_TBL VALUES('admin', 'admin', '관리자', 'M', 30, 'admin@iei.or.kr', '01012345678', '서울시 강남구 역삼동 테헤란로 7', '기타,독서,운동', SYSDATE)
//		String query = "INSERT INTO STUDENT_TBL VALUES('"+student.getStudentId()+"', '"+student.getStudentPwd()+"', '"+student.getStudentName()+"', '"+student.getGender()+"', "+student.getAge()+", '"+student.getEmail()+"', '"+student.getPhone()+"' , '"+student.getAddress()+"', '"+student.getHobby()+"', SYSDATE)";
		String query = "INSERT INTO STUDENT_TBL VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		int result = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			Statement stmt = conn.createStatement();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentId());
			pstmt.setString(2, student.getStudentPwd());
			pstmt.setString(3, student.getStudentName());
			pstmt.setString(4, student.getGender()+""); //String변환은 보통 간단하게 ""을 붙임
			//pstmt.setString(4, String.valueOf(student.getGender()));
			pstmt.setInt(5, student.getAge());
			pstmt.setString(6, student.getEmail());
			pstmt.setString(7, student.getPhone());
			pstmt.setString(8, student.getAddress());
			pstmt.setString(9, student.getHobby());
			result = pstmt.executeUpdate(); //**쿼리문 실행 빼먹지 않기**
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int updateStudent(Student student) {
//		String query = "UPDATE STUDENT_TBL SET "
//				+ "STUDENT_PWD ='"+student.getStudentPwd()+"', "
//				+ "EMAIL = '"+student.getEmail()+"', "
//				+ "PHONE='"+student.getPhone()+"', "
//				+ "ADDRESS = '"+student.getAddress()+"', "
//				+ "HOBBY = '"+student.getHobby()+"' "
//				+ "WHERE STUDENT_ID = '"+student.getStudentId()+"'";
		String query = "UPDATE STUDENT_TBL SET STUDENT_PWD = ?, EMAIL = ?, PHONE= ?, ADDRESS = ?, HOBBY = ? WHERE STUDENT_ID = ?";
		int result = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			Statement stmt = conn.createStatement();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentPwd());
			pstmt.setString(2, student.getEmail());
			pstmt.setString(3, student.getPhone());
			pstmt.setString(4, student.getAddress());
			pstmt.setString(5, student.getHobby());
			pstmt.setString(6, student.getStudentId());
			result = pstmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}

	public int deleteStudent(String studentId) {
//		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = '"+studentId+"'";
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = ?";
		//DELETE FROM STUDENT_TBL WHERE STUDENT_ID = '';
		int result = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			//Statement stmt = conn.createStatement();
			//result = stmt.executeUpdate(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			result = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}

	private Student rsetToStudent(ResultSet rset) {
		Student student = new Student();
		try {
			student.setStudentId(rset.getString("STUDENT_ID"));
			student.setStudentPwd(rset.getString("STUDENT_PWD"));
			student.setStudentName(rset.getString("STUDENT_Name"));
			student.setGender(rset.getString("GENDER").charAt(0));
			student.setAge(rset.getInt("AGE"));
			student.setEmail(rset.getString("EMAIL"));
			student.setPhone(rset.getString("PHONE"));
			student.setGender(rset.getString("GENDER").charAt(0)); //문자는 문자열에서 문자로 잘라서 사용
			student.setAddress(rset.getString("ADDRESS"));
			student.setHobby(rset.getString("HOBBY"));
			student.setEnrollDate(rset.getDate("ENROLL_DATE"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}




}
