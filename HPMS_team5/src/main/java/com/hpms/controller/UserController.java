package com.hpms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hpms.model.User;
import com.hpms.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/add_user")
	public ModelAndView addUserForm(HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    if (isUserLoggedIn(session)) {
	        User loggedInUser = (User) session.getAttribute("loggedUser");
	        if(loggedInUser.getRole()!=1) {
	            return new ModelAndView("redirect:/login");
	        }
	        
	        ModelAndView mv = new ModelAndView("layout");
	        request.setAttribute("title", "Add User");
	        request.setAttribute("content", "add_user_form"); 
	        return mv;
	    }
	    return new ModelAndView("redirect:/login");
	}

	@PostMapping("/add_user")
	public String addUser(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {
	    User loggedInUser = (User) session.getAttribute("loggedUser");
	    if (loggedInUser == null || loggedInUser.getRole() != 1) {
	        return "redirect:/login";
	    }

	    String phoneNumber = user.getPhoneNumber();
	    if (phoneNumber == null || phoneNumber.length() < 4) {
	        redirectAttributes.addFlashAttribute("error", "Invalid phone number");
	        return "redirect:/add_user";
	    }
	    user.setPassword(phoneNumber.substring(phoneNumber.length() - 4));

	    try {
	        userService.addUser(user);
	        redirectAttributes.addFlashAttribute("successMessage", "User added successfully");
	        return "redirect:/userlisting";
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	        return "redirect:/add_user";
	    }
	}

	@GetMapping("/edit_user/{id}")
	public ModelAndView editUserForm(@PathVariable Long id, HttpSession session, HttpServletRequest request) {
	    User loggedInUser = (User) session.getAttribute("loggedUser");
	    if (loggedInUser == null || loggedInUser.getRole() != 1) {
	        return new ModelAndView("redirect:/login");
	    }

	    User user = userService.getUserById(id);
	    if (user == null) {
	        return new ModelAndView("redirect:/userlisting");
	    }

	    ModelAndView mv = new ModelAndView("layout");
	    mv.addObject("user", user);
        request.setAttribute("title", "Edit User");
        request.setAttribute("content", "edit_user_form"); 
	    return mv;
	}

	@PostMapping("/edit_user/{id}")
	public String updateUser(@PathVariable Long id, @ModelAttribute User user, 
	                        HttpSession session, RedirectAttributes redirectAttributes) {
	    User loggedInUser = (User) session.getAttribute("loggedUser");
	    if (loggedInUser == null || loggedInUser.getRole() != 1) {
	        return "redirect:/login";
	    }

	    try {
	        user.setId(id);
	        userService.updateUser(user);
	        redirectAttributes.addFlashAttribute("successMessage", "User updated successfully");
	        return "redirect:/userlisting";
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	        return "redirect:/edit_user/" + id;
	    }
	}

	@PostMapping("/delete_user/{id}")
	public String deleteUser(@PathVariable Long id, HttpSession session, 
	                        RedirectAttributes redirectAttributes) {
	    User loggedInUser = (User) session.getAttribute("loggedUser");
	    if (loggedInUser == null || loggedInUser.getRole() != 1) {
	        return "redirect:/login";
	    }

	    try {
	        userService.deleteUser(id);
	        redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	    }
	    return "redirect:/userlisting";
	}

	private boolean isUserLoggedIn(HttpSession session) {
		if (session.getAttribute("loggedUser")!=null) {
			return true;
		}
		return false;
	}

}