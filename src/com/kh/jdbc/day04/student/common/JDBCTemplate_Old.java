package com.kh.jdbc.day04.student.common;

import java.sql.*;

public class JDBCTemplate_Old {
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private final String USER = "student";
	private final String PASSWORD = "student";
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
	private static JDBCTemplate_Old instance; //JDBC탬플릿타입의 변수 instance선언
	private static Connection conn;
	
	private JDBCTemplate_Old() {} //jdbc탬플릿의 생성자는 private-> getInstance메소드를 통해서만 생성할 수 있게함 -> 외부에서는 생성못함
	
	public static JDBCTemplate_Old getInstance() {
		// 1. 이미 만들어져 있는지 체크하고
		if(instance == null) {
			// 3. 없으면 만들어서 사용
			instance = new JDBCTemplate_Old();
		}
		// 2. 만들어져있으면 재사용
		return instance;
	}
	//-------------------------------------------------------------------------------------
	
	//DBCP(DataBase Connection Pool) : DB생성을 딱 한번만->이후 가져다 재사용 : 싱글톤패턴
	public Connection createConnection() {
		try {
			if(conn == null || conn.isClosed()) {
				Class.forName(DRIVER_NAME);
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
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

