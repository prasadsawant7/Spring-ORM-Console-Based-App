package com.spring.orm.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.orm.hibernate5.HibernateTemplate;

import com.spring.orm.entities.Student;

@Transactional
public class StudentDao {
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
//	CRUD
//	Create
	public int create(Student student) {
		int r = (int) this.hibernateTemplate.save(student);
		return r;
	}
	
//	Read Single Row
	public Student read(int studentId) {
		Student student = this.hibernateTemplate.get(Student.class, studentId);
		return student;
	}
	
//	Read Entire Table
	public List<Student> readAll() {
		List<Student> students = this.hibernateTemplate.loadAll(Student.class);
		return students;
	}
	
//	Update
	public void update(boolean[] checker, int studentId, String... students) {
		Student student = read(studentId);
		if(checker[0] == true && checker[1] == false && checker[2] == false) {
			student.setStudentName(students[0]);
		}
		else if(checker[0] == false && checker[1] == true && checker[2] == false) {
			student.setStudentCity(students[1]);
		}
		else if((checker[0] == false && checker[1] == false && checker[2] == true) || (checker[0] == true && checker[1] == true && checker[2] == false) || (checker[0] == true && checker[1] == true && checker[2] == true)) {
			student.setStudentName(students[0]);
			student.setStudentCity(students[1]);
		}
		this.hibernateTemplate.update(student);
	}
	
//	Delete
	public void delete(int studentId) {
		Student student = this.hibernateTemplate.get(Student.class, studentId);
		this.hibernateTemplate.delete(student);
	}
}