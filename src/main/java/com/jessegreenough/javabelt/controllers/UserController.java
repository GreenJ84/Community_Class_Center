package com.jessegreenough.javabelt.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jessegreenough.javabelt.models.Course;
import com.jessegreenough.javabelt.models.LoginUser;
import com.jessegreenough.javabelt.models.User;
import com.jessegreenough.javabelt.services.CourseService;
import com.jessegreenough.javabelt.services.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CourseService courseService;
	
	@RequestMapping("/")
	public String register(
				@ModelAttribute("newUser") User user,
				BindingResult results,
				HttpSession session){
		if (session.getAttribute("userId") != null) {
			return "redirect:/classes";
		}
		return "registration.jsp";
	}
	
	@PostMapping("/createUser")
	public String newUser(
				@Valid @ModelAttribute("newUser") User user,
				BindingResult results,
				Model model,
				HttpSession session) {
		
		session.removeAttribute("errors");
		
		if ( !user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("newUser", user);
			results.rejectValue("confirmPassword", "NoMatches", "The Confirm Password must match Password!");
			return "registration.jsp";
		}
		
		if (userService.getUserByEmail(user.getEmail()) != null) {
			session.setAttribute("errors", "User with this email already registered. Please login.");
			model.addAttribute("newUser", user);
			return "redirect:/login";
		} 
		
		else {

			if( results.hasErrors() ) {
				model.addAttribute("newUser", user);
				return "registration.jsp";
			}
		
			userService.save(user);
			
			session.setAttribute("userId", user.getId());
			session.setAttribute("userFirstName", user.getFirstName());
			session.setAttribute("userLastName", user.getLastName());
			return "redirect:/classes";
		}
	}
	
	@RequestMapping("/login")
	public String login(
				@ModelAttribute("loginEvent") LoginUser login,
				BindingResult results,
				HttpSession session) {
		if (session.getAttribute("userId") != null) {	
			return "redirect:/classes";
		}
		return "login.jsp";
	}
	
	@PostMapping("/login/check")
	public String checkLogin(
				@Valid @ModelAttribute("loginEvent") LoginUser login,
				BindingResult results,
				Model model,
				HttpSession session) {
		
		session.removeAttribute("errors");
		
		if (userService.getUserByEmail(login.getEmail()) == null) {
			
			session.setAttribute("errors", "Email does not exist in system. Please Register!");
			model.addAttribute("loginEvent", login);
			return "redirect:/";
		} 
		
		else {
			
			User userCheck = userService.getUserByEmail(login.getEmail());
			
			if (!BCrypt.checkpw(login.getPassword(), userCheck.getPassword())) {
				
				results.rejectValue("password", "NotMatches", "Invalid Password!");
				model.addAttribute("loginEvent", login);
				return "login.jsp";
			}
			
			else {
				
				session.setAttribute("userId", userCheck.getId());
				session.setAttribute("userName", userCheck.getFirstName());
				session.setAttribute("userLastName", userCheck.getLastName());
				return "redirect:/classes";
			}
		}
	}
	
	@RequestMapping("/classes")
	public String displayUser(
				Model model,
				HttpSession session,
				RedirectAttributes flash) {
		
		if (session.getAttribute("userId") == null) {
			
			flash.addFlashAttribute("noLogin", "You are not logged in! Loggin or Register.");
			return "redirect:/login";
		}
				
		List<Course> allCourses = courseService.getAll();
		model.addAttribute("allCourses", allCourses);
		return "dashboard.jsp";
	}
	
	@GetMapping("/clear")
	public String logOut(
				@ModelAttribute("loginEvent") LoginUser login,
				BindingResult results,
				HttpSession session) {
		
		session.invalidate();
		return "redirect:/login";
	}
	
	
	
	
}
