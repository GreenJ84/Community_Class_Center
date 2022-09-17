package com.jessegreenough.javabelt.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jessegreenough.javabelt.models.Course;
import com.jessegreenough.javabelt.models.Student;
import com.jessegreenough.javabelt.services.CourseService;
import com.jessegreenough.javabelt.services.StudentService;

@Controller
public class StudentController {
		
		@Autowired
		private StudentService studentService;
		
		@Autowired
		private CourseService courseService;
		
		@PostMapping("/classes/{id}/createStudent")
		public String createStudent(
				@PathVariable("id") Long id,
				@Valid @ModelAttribute("student") Student student,
				BindingResult results,
				Model model) {
			if (results.hasErrors()) {
				Course thisCourse = courseService.getCourseById(id);
				model.addAttribute("course", thisCourse);
				
				List<Student> allStudents = studentService.getAll();
				model.addAttribute("allStudents", allStudents);
				model.addAttribute("student", student);
				return "displayCourse.jsp";
			}
			student.setId(null);
			studentService.newStudent(student);
			
			studentService.addNewStudent(student, id);
			
			return"redirect:/classes/"+id;
		}
		
		@PostMapping("/classes/{id}/addStudent")
		public String addStudent(
					@PathVariable("id") Long id,
					@RequestParam("studentId") Long studentId) {
			studentService.addStudent(studentId, id);
			
			return"redirect:/classes/"+id;
		}
}
