package com.todoproject.controller;

import com.todoproject.model.Todos;
import com.todoproject.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class TodosController {

    @Autowired
    TodoRepository todoRepository;

    @GetMapping({"/getalltodo"})
    public ModelAndView getAllTodos() {
        ModelAndView mv= new ModelAndView("todoactions");
        List<Todos> todos= todoRepository.findAll();
        System.out.println(todos);
        if (todos.size()==0) {
            mv.addObject ("todos", null);
            return mv;
        }
        mv.addObject ("todos", todos);
        return mv;
    }

    @PostMapping("/savetodo")
    public String saveTodos(@ModelAttribute Todos todos){
        System.out.println("***************************"+todos);
        Date currentDate=new Date();
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy hh:m a");
        todos.setCreateDate(formatter.format(currentDate));
        System.out.println(todos.getUpdateDate());
        todoRepository.save(todos);
        System.out.println("Exiting savetodo method");
        return "redirect:/getalltodo";
    }

    @GetMapping("/deleteTodo")
    public String deleteTodo(@RequestParam long id) {
        todoRepository.deleteById(id);
        return "redirect:/getalltodo";
    }

    @GetMapping("/updateTodo")
    public ModelAndView updateTodo(@RequestParam long id) {
        ModelAndView mv= new ModelAndView("updatetodo");
        Todos todos= todoRepository.findById(id).get();
        System.out.println(todos);
        mv.addObject ("todos", todos);
        return mv;
    }
}
