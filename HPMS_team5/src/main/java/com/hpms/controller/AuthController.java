package com.hpms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hpms.enums.UserRole;
import com.hpms.model.User;
import com.hpms.service.IUserService;

@Controller
public class AuthController {
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(IUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user,
                             @RequestParam String confirmPassword,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (userService.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Email already registered");
            return "register";
        }

        if (!user.getPassword().equals(confirmPassword)) {
            result.rejectValue("password", "error.user", "Passwords do not match");
            return "register";
        }

        user.setRole(UserRole.USER.getValue());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.save(user);
        return "redirect:/login?registered";
    }
}