package com.kh.jdbc.day01.student.run;

import com.kh.jdbc.day01.student.view.StudentView;

public class Run {

	public static void main(String[] args) {
		//뷰 객체생성 후 스타트프로그램 메소드 호출
		StudentView view = new StudentView();
		view.startProgram(); 
	}

}
