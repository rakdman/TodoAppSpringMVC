package com.todoproject.controller;


import com.todoproject.model.User;
import com.todoproject.repository.TodoRepository;
import com.todoproject.repository.UserRepository;
import com.todoproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {


//    @Autowired
//    AuthenticationManager authenticationManager;

//    @Autowired
//    UserService userService;


    @Autowired
    UserRepository userRepository;

    @Autowired
    TodoRepository todoRepository;

    @GetMapping("/registration")
    public String inputRegistrationForm() {
        return "registration";
    }

    @GetMapping("/contact")
    public String contactForm() {
        return "contact";
    }

//    @GetMapping("/login")
//    public String loginForm() {
//        return "loginform";
//    }
//
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("todoactions");
        mv.addObject("todos", todoRepository.findAll());
        return mv;
    }

    @GetMapping({"/"})
    public String loginform() {
        return "loginform";
    }

    PasswordEncoder passwordEncoder;

    @PostMapping("/saveuser")
    public String registerUserAccount(@ModelAttribute("userEntity") User userEntity){
        System.out.println(userEntity);
        ModelAndView mv=new ModelAndView("registration");
        mv.addObject  ("userEntity",userEntity);

        passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
        System.out.println("Registering user:"+userEntity);
        userRepository.save(userEntity);
        return "redirect:/login";
    }



}
