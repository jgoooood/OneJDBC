package com.kh.jdbc.day04.student.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate {
	//아래 정보는 파일에 저장하여 따로 관리함->Properties를 사용해 상수 코드 제거
//	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
//	private final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
//	private final String USER = "student";
//	private final String PASSWORD = "student";
	// 보안이 필요한 내용을 파일에 저장하여 노출없이도 Properties를 통해 값을 가져올 수 있음
	private Properties prop;
	/* 
	 * 디자인 패턴 : 각기 다른 소프트웨어 모듈이나 기능을 가진 응용 SW를 개발할 때
	 * 공통되는 설계 문제를 해결하기 위해서 사용하는 패턴
	 * -> 효율적인 방식을 위함
	 * 패턴의 종류 : 생성패턴, 구조패턴, 행위패턴, ...
	 * 1. 생성패턴 : 싱글톤 패턴, 추상팩토리, 팩토리 메서드, ...
	 * 2. 구조패턴 : 컴포지트, 데코레이트, ...
	 * 3. 행위패턴 : 옵저버, 스테이드, 전략, 템플릿 메서드, ...
	 */
	
	/*-----------------------------------------싱글톤패턴----------------------------------
	 * public calss Singletone {
	 * 		private static Singletone instance;         //생성할 싱글톤패턴타입의 변수선언
	 * 	
	 * 		private Singletone() {}						//싱글톤패턴의 생성자
	 * 
	 *  	public static Singletone getInstance() {    //싱글톤패턴의 객체생성 메소드(메소드명은 알아서)
	 *  		if(instance == null) {
	 *  			instance = new Singletone();
	 *  		}
	 *  		return instance;
	 *  	}
	 */
	// 싱글톤패턴 목적 : 만들어져있는것을 재사용->새로 만들지 않음
	// 싱글톤패턴으로 JDBCTemplate을 만듬
	// static : 객체생성없이 메소드를 사용할 수 있음-> new JDBCTemplate 객체생성없이 메소드 사용
	//무조건 딱 한번만 생성되고 없을 때에만 생성한다
	//이미 존재하면 존재하는 객체를 사용함
	private static JDBCTemplate instance; //JDBC탬플릿타입의 변수 instance선언
	private static Connection conn;
	
	private JDBCTemplate() {} //jdbc탬플릿의 생성자는 private-> getInstance메소드를 통해서만 생성할 수 있게함 -> 외부에서는 생성못함
	
	public static JDBCTemplate getInstance() {
		// 1. 이미 만들어져 있는지 체크하고
		if(instance == null) {
			// 3. 없으면 만들어서 사용
			instance = new JDBCTemplate();
		}
		// 2. 만들어져있으면 재사용
		return instance;
	}
	//-------------------------------------------------------------------------------------
	
	//DBCP(DataBase Connection Pool) : DB생성을 딱 한번만->이후 가져다 재사용 : 싱글톤패턴
	public Connection createConnection() {
		try {
			prop = new Properties();
			Reader reader = new FileReader("resources/dev.properties");
			prop.load(reader);
			String driverName = prop.getProperty("driverName"); //파일에 저장된 키값을 입력해 값을 가져오기
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			if(conn == null || conn.isClosed()) {
				Class.forName(driverName);
				conn = DriverManager.getConnection(url, user, password);
				conn.setAutoCommit(false); // 오토커밋 풀기
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//전역변수 conn을 사용하는것이 아니고 commit할 시점의 conn을 넘겨받아서 진행
	//static을 사용해서 객체생성없이 메소드 사용가능->service에서 처리
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void close() { 		//conn연결을 닫아주는 메소드
		if(conn != null) {
			try {
				if(!conn.isClosed()) { //conn이 닫혀있지 않으면
					conn.close();		//닫아주기
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

