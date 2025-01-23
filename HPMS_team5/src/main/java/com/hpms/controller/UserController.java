package com.hpms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hpms.model.User;
import com.hpms.service.IUserService;

@Controller
public class UserController {
	@Autowired
	private IUserService userService;
	
	@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add_user")
    public ModelAndView addUserForm() {
        ModelAndView mv = new ModelAndView("layout");
        mv.addObject("currentPage", "userlisting");
        mv.addObject("title", "Add User");
        mv.addObject("content", "add_user_form");
        return mv;
    }

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add_user")
	public ModelAndView addUser(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("layout");
		mv.addObject("currentPage", "userlisting");
		mv.addObject("title", "Edit User");
		mv.addObject("content", "edit_user_form");
		mv.addObject("user", user);

		String phoneNumber = user.getPhoneNumber();
		if (phoneNumber == null || phoneNumber.length() < 4) {
			mv.addObject("errorMessage", "Invalid phone number");
			return mv;
		}

		user.setPassword(phoneNumber.substring(phoneNumber.length() - 4));

		User existingUser = userService.getUserByEmail(user.getEmail());
		if (existingUser != null) {
			mv.addObject("errorMessage", "Email already exists");
			return mv;
		}

		try {
			userService.addUser(user);
			redirectAttributes.addFlashAttribute("successMessage", "User updated successfully");
			return new ModelAndView("redirect:/userlisting");
		} catch (Exception e) {
			mv.addObject("errorMessage", e.getMessage());
			return mv;
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/edit_user/{id}")
	public ModelAndView editUserForm(@PathVariable int id, HttpSession session, HttpServletRequest request) {
		User user = userService.getUserById(id);
		if (user == null) {
			return new ModelAndView("redirect:/userlisting");
		}

		ModelAndView mv = new ModelAndView("layout");
		mv.addObject("currentPage", "userlisting");
		mv.addObject("user", user);
		request.setAttribute("title", "Edit User");
		request.setAttribute("content", "edit_user_form"); 
		return mv;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/edit_user/{id}")
	public ModelAndView updateUser(@PathVariable int id, @ModelAttribute User user, HttpSession session) {
		ModelAndView mv = new ModelAndView("layout");
		mv.addObject("currentPage", "userlisting");
		mv.addObject("title", "Edit User");
		mv.addObject("content", "edit_user_form");
		mv.addObject("user", user);

		String phoneNumber = user.getPhoneNumber();
		if (phoneNumber == null || phoneNumber.length() < 4) {
			mv.addObject("errorMessage", "Invalid phone number");
			return mv;
		}

		User existingUser = userService.getUserByEmail(user.getEmail());
		if (existingUser != null) {
		    mv.addObject("errorMessage", "Email already exists");
		    return mv;
		}

		try {
			user.setId(id);
			userService.updateUser(user);
			return new ModelAndView("redirect:/userlisting");
		} catch (Exception e) {
			mv.addObject("errorMessage", e.getMessage());
			return mv;
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/delete_user/{id}")
	public String deleteUser(@PathVariable int id, HttpSession session, 
			RedirectAttributes redirectAttributes) {
		try {
			userService.deleteUser(id);
			redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/userlisting";
	}

}