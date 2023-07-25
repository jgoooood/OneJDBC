package com.kh.jdbc.day04.student.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.jdbc.day04.student.common.JDBCTemplate;
import com.kh.jdbc.day04.student.model.dao.StudentDAO;
import com.kh.jdbc.day04.student.model.vo.Student;

//CONTROLLER - SERVICE - DAO 순으로 연결 
public class StudentService {
	private StudentDAO sDao;
	private JDBCTemplate jdbcTemplate;
	//다른곳에서 StudentService객체를 생성하면 생성자에 초기화된 내용으로 인해
	//sDao = new StudentDAO();
	//jdbcTemplate = new JDBCTemplate();
	//객체 생성될때마다 DAO와 템플릿 객체가 생성됨
	//JDBCTemplate->생성될 수록 속도가 느려짐->DBCP(자주사용하는 연결을 POOL로 만듬)
	public StudentService() {
		sDao = new StudentDAO();
		//jdbcTemplate = new JDBCTemplate(); <- 생성자가 private으로 만들어졌기 때문에 사용 못함(싱글톤패턴적용)
		//싱글톤패턴을 적용하면 메소드를 통해 만들어진 탬플릿을 사용할 수 있음
		jdbcTemplate = JDBCTemplate.getInstance();
	}

	public List<Student> selectAll() {
		Connection conn = jdbcTemplate.createConnection();
		List<Student> sList = sDao.selectAll(conn);
		jdbcTemplate.close();
		return sList;
	}

	public Student selectOneById(String studentId) {
		Connection conn = jdbcTemplate.createConnection();
		Student student = sDao.selectOneById(conn, studentId);
		jdbcTemplate.close();
		return student;
	}

	public List<Student> selectAllByName(String studentName) {
		Connection conn = jdbcTemplate.createConnection();
		List<Student> sList = sDao.selectAllByName(conn, studentName);
		jdbcTemplate.close();
		return sList;
	}

	public int deleteStudent(String studentId) {
		Connection conn = jdbcTemplate.createConnection();
		int result = sDao.deleteStudent(conn, studentId);
		jdbcTemplate.close();
		return result;
	}

	public int insertStudent(Student student) {
		Connection conn = jdbcTemplate.createConnection();
		int result = sDao.insertStudent(conn, student);
		jdbcTemplate.close();
		return result;
	}

	public int updateStudent(Student student) {
		Connection conn = jdbcTemplate.createConnection();
		int result = sDao.updateStudent(conn, student);
		jdbcTemplate.close();
		return result;
	}

}
