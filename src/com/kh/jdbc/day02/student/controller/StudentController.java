package com.kh.jdbc.day02.student.controller;

import java.util.List;

import com.kh.jdbc.day01.student.model.dao.StudentDAO;
import com.kh.jdbc.day01.student.model.vo.Student;

//DAO에 정보 요청
public class StudentController {
	private StudentDAO studentDao;
	
	public StudentController() {
		studentDao = new StudentDAO(); 
	}

	public List<Student> printAllStudent() {
		List<Student> sList = studentDao.selectAll();
		return sList; //sList를 리턴
	}

	public Student printStudentById(String studentId) {
		Student student = studentDao.selectOneById(studentId);
		return student;
	}

	public List<Student> printStudentByName(String studentName) {
		List<Student> sList = studentDao.selectAllByName(studentName);
		return sList;
	}

	//학생정보를 갖고 DAO에 전달함
	public int insertStudent(Student student) {
		int result = studentDao.insertStudent(student); 
		//sql에 학생정보를 insert하게 되는데 그 인서트한 결과의 결과값은 int 1로 반환됨 : 1행이 삽입됐기 때문
		//따라서 결과값을 담을 변수result도 int여야 함
		return result;
	}

	public int modifyStudent(Student student) {
		int result = studentDao.updateStudent(student);
		return result;
	}

	public int deleteStudent(String studentId) {
		int result = studentDao.deleteStudent(studentId);
		return result;
	}

}
