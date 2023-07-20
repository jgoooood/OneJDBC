package com.kh.jdbc.day01.student.controller;

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
	

}
