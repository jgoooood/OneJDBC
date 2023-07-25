package com.kh.jdbc.day04.student.controller;

import java.util.List;

import com.kh.jdbc.day04.student.model.dao.StudentDAO;
import com.kh.jdbc.day04.student.model.service.StudentService;
import com.kh.jdbc.day04.student.model.vo.Student;

public class StudentController {
	private StudentService sService;
	
	public StudentController() {
		sService = new StudentService();
	}
	
	public List<Student> selectAll() {
		List<Student> sList = sService.selectAll();
		return sList;
	}

	public Student selectOneById(String studentId) {
		Student student = sService.selectOneById(studentId);
		return student;
	}

	public List<Student> selectAllByName(String studentName) {
		List<Student> sList = sService.selectAllByName(studentName);
		return sList;
	}

	public int deleteStudent(String studentId) {
		int result = sService.deleteStudent(studentId);
		return result;
	}

	public int insertStudent(Student student) {
		int result = sService.insertStudent(student);
		return result;
	}

	public int updateStudent(Student student) {
		int result = sService.updateStudent(student);
		return result;
	}

}
