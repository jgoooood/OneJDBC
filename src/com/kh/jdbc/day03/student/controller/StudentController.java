package com.kh.jdbc.day03.student.controller;

import java.util.List;

import com.kh.jdbc.day03.student.model.dao.StudentDAO;
import com.kh.jdbc.day03.student.model.vo.Student;

public class StudentController {
	StudentDAO sDao = null;
	
	public StudentController() {
		sDao = new StudentDAO();
	}
	
	// SELECT * FROM STUDENT_TBL;
	public List<Student> selectAllStudent() {
		List<Student> sList = sDao.selectAllStudent();
		return sList;
	}

	public Student selectOneById(String studentId) {
		Student student = sDao.selectOneById(studentId);
		return student;
	}

	public List<Student> selectAllByName(String studentName) {
		List<Student> sList = sDao.selectAllByName(studentName);
		return sList;
	}

	public Student studentLogin(Student student) {
		Student result = sDao.selectLoginInfo(student);
		return result;
	}

	public int insertStudent(Student student) {
		int result = sDao.insertStudent(student);
		return result;
	}

	public int updateStudent(Student student) {
		int result = sDao.updateStudent(student);
		return result;
	}

	public int deleteStudent(String studentId) {
		int result = sDao.deleteStudent(studentId);
		return result;
	}



}
