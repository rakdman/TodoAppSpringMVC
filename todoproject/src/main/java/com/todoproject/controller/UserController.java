package com.todoproject.controller;


import com.todoproject.model.User;
import com.todoproject.repository.TodoRepository;
import com.todoproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

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

    @GetMapping("/login")
    public String loginForm() {
        System.out.println("into login login form");
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        System.out.println("logged out successfully");
        return "redirect:/login";
    }

    @GetMapping ("/")
    public ModelAndView login() {
        System.out.println("Into post mapping login");
        ModelAndView mv = new ModelAndView("todoactions");
        mv.addObject("todos", todoRepository.findAll());
        System.out.println("into login api");
        return mv;
    }

    @GetMapping({"/accessdenied"})
    public String accessdenied() {
        return "redirect:/accessdenied";
    }



    PasswordEncoder passwordEncoder;

    @PostMapping("/saveuser")
    public String registerUserAccount(@ModelAttribute("userEntity") User userEntity){
        String roles[]= {"ROLE_ADMIN"};
        userEntity.setRoles(roles);
        userEntity.setActive(true);
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

    @DeleteMapping("/deleteUser")
    public void deleteUser(@PathVariable String username) {
        User user= userRepository.findByUsername(username).get();

        if(!user.equals("") && !user.equals(null)) {
            userRepository.delete(user);
        }

    }

}
