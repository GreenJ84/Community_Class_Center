package com.jessegreenough.javabelt.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jessegreenough.javabelt.models.Course;
import com.jessegreenough.javabelt.models.Student;
import com.jessegreenough.javabelt.repositories.StudentRepository;

@Service
public class StudentService {

		@Autowired
		private StudentRepository studentRepo;
		
		@Autowired
		private CourseService courseService;
		
		public List<Student> getAll(){
			return studentRepo.findAll();
		}
		
		public Student findById(Long id) {
			Optional<Student> isStudent = studentRepo.findById(id);
			if (isStudent == null) {
				return null;
			}
			Student thisStudent = isStudent.get();
			return thisStudent;
		}
		
		public Student newStudent(Student s) {
			return studentRepo.save(s);
		}
		
		public Course addNewStudent(Student s, Long id) {
			
			
			
			
			Course c = courseService.getCourseById(id);
			List<Student> activeIn = new ArrayList<Student>();
			
			activeIn = c.getStudentsAttending();
			
			activeIn.add(s);
			c.setStudentsAttending(activeIn);
			
			
			return courseService.updateCourse(c);
		}
		
		public void addStudent(Long studentId, Long courseId) {
			
			Course c = courseService.getCourseById(courseId);
			List<Student> activeIn  = c.getStudentsAttending();
			Boolean alreadyAssigned = false;
			
			Student s = this.findById(studentId);
			for (int i = 0; i < activeIn.size(); i++) {
				Student x = activeIn.get(i);
				if (x.getId() == studentId) {
					alreadyAssigned = true;
				}
			}
			if (!alreadyAssigned) {
			
				activeIn.add(s);
				c.setStudentsAttending(activeIn);
				
				courseService.updateCourse(c);
			}
				
		}
}
