package com.jessegreenough.javabelt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jessegreenough.javabelt.models.Course;
import com.jessegreenough.javabelt.repositories.CourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepo;
	
	public List<Course> getAll(){
		
		return courseRepo.findAll();
	}
	
	public Course createCourse(Course c) {
		return courseRepo.save(c);
	}
	
	public Course getCourseById(Long id) {
		Optional<Course> isCourse = courseRepo.findById(id);
		if (isCourse == null) {
			return null;
		}
		Course thisCourse = isCourse.get();
		return thisCourse;
	}
	
	public Course updateCourse(Course c) {
		return courseRepo.save(c);
	}
	
	public void deleteCourse(Long id) {
		courseRepo.deleteById(id);
	}
}
