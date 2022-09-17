package com.jessegreenough.javabelt.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jessegreenough.javabelt.models.Course;
import com.jessegreenough.javabelt.models.Student;
import com.jessegreenough.javabelt.services.CourseService;
import com.jessegreenough.javabelt.services.StudentService;

@Controller
@RequestMapping("/classes")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/new")
	public String newCourse(
				@ModelAttribute("course") Course course,
				HttpSession session,
				RedirectAttributes flash) {
		if (session.getAttribute("userId") == null) {
			
			flash.addFlashAttribute("noLogin", "You are not logged in! Loggin or Register.");
			return "redirect:/login";
		}
		
		System.out.println(session.getAttribute("userId"));
		return "newCourse.jsp";
	}
	
	@PostMapping("/new/create")
	public String createCourse(
				@Valid @ModelAttribute("course") Course course,
				BindingResult results,
				Model model,
				HttpSession session) {
		if (results.hasErrors()) {
			model.addAttribute("course", course);
			return "newCourse.jsp";
		}
		
		courseService.createCourse(course);
		
		return"redirect:/classes";
	}
	
	@GetMapping("/{id}")
	public String displayCourse(
			@PathVariable("id") Long id,
			@ModelAttribute("student") Student student,
			Model model,
			HttpSession session,
			RedirectAttributes flash) {
		if (session.getAttribute("userId") == null) {
			
			flash.addFlashAttribute("noLogin", "You are not logged in! Loggin or Register.");
			return "redirect:/login";
		}
		
		Course thisCourse = courseService.getCourseById(id);
		model.addAttribute("course", thisCourse);
		
		List<Student> allStudents = studentService.getAll();
		model.addAttribute("allStudents", allStudents);

		return "displayCourse.jsp";
		
	}
	
	@GetMapping("/{id}/edit")
	public String editCourse(
			@PathVariable("id") Long id,
			@ModelAttribute("course") Course course,
			Model model,
			HttpSession session,
			RedirectAttributes flash) {
		if (session.getAttribute("userId") == null) {
			
			flash.addFlashAttribute("noLogin", "You are not logged in! Loggin or Register.");
			return "redirect:/login";
		}
		Course thisCourse = courseService.getCourseById(id);
		model.addAttribute("course", thisCourse);
		
		
		return "editCourse.jsp";
	}
	
	@PutMapping("/{id}/edit/update")
	public String updateCourse(
			@Valid @ModelAttribute("course") Course course,
			BindingResult results,
			Model model,
			HttpSession session) {
		if(results.hasErrors()) {
			model.addAttribute("course", course);
			return "editCourse.jsp";
		}
		courseService.updateCourse(course);
		return"redirect:/classes";
	}
	
	@DeleteMapping("/{id}/delete")
	public String deleteCourse(
				@PathVariable("id") Long id) {
		courseService.deleteCourse(id);
		return "redirect:/classes";
	}
	
}
