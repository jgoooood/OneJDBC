package com.kh.jdbc.day01.basic;

//sql 모든 클래스 임포트
import java.sql.*;
//DAO에 삽입할 코드
public class JDBCRun {

	public static void main(String[] args) {
		// 오라클 쿼리먼저 실행->오류없으면 JDBC코딩
		/*
		 * JDBC 코딩 절차
		 * 1. 드라이버 등록 : ojdbc6.jar 등록
		 * 2. DBMS 연결 생성 (DriverManager 사용) -> 리소스 많이 먹기 때문에 반드시 close()사용
		 * 3. Statement 객체 생성(쿼리문 실행 준비)
		 * 	- new Statement();가 아니라 연결을 통해 객체 생성(Statement는 인터페이스이기 때문)
		 * 4. SQL 전송 (쿼리문 실행)
		 * 5. 결과 받기 (ResultSet 으로 받아버림)
		 * 6. 자원해제(close())
		 * 
		 */
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; //:(콜론) 3개 입력
			String user = "KH";
			String password = "KH";
			String query = "SELECT EMP_NAME, SALARY FROM EMPLOYEE"; //sql 쿼리문 *세미콜론빼고 복사* 
			// 1. 드라이브등록forName()
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. DBMS 연결 생성 : DriverManager.getConnection();
			// Connection 인터페이스 타입 참조변수에 저장
			Connection conn = DriverManager.getConnection(url, user, password); //생성완료
			// 3. 쿼리문 실행준비 (Statement 객체생성 : Statement는 인터페이스->메소드연결을 통해 생성)
			Statement stmt = conn.createStatement();
			// 4. 쿼리문 실행 executeQuery() 
			// 5. 결과출력 쿼리문 select=>ResultSet인터페이스의 참조변수 rset에 저장
			ResultSet rset = stmt.executeQuery(query);
			//후처리필요(반복문) - DB에서 가져온 데이터 사용하기 위함
			while(rset.next()) { //.next()다음 데이터 있는지 여부 확인
				System.out.printf("직원명 : %s, 급여 : %s\n"
						, rset.getString("EMP_NAME") //컬럼데이터 타입에 따라 getString(), getInt()로 나뉨
						, rset.getInt(2));
			}
			//================================출력완료================================
			// 6. 자원해제
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
