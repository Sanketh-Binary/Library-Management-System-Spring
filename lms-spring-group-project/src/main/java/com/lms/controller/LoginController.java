package com.lms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lms.bean.User;
import com.lms.model.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public ModelAndView loginPageController() {
        return new ModelAndView("index", "command", new User());
    }
    
    @RequestMapping("/adminLand")
    public ModelAndView adminLander() {
        return new ModelAndView("adminLanding");
    }
    
    @RequestMapping("/userLand")
    public ModelAndView userLander() {
        return new ModelAndView("userLanding");
    }

    @RequestMapping("/login")
    public ModelAndView loginController(@ModelAttribute User user,HttpSession session) {

        ModelAndView modelAndView=new ModelAndView();
        if (userService.checkUser(user)) {
            modelAndView.addObject("user", user);  //user object added at request scope
           session.setAttribute("user", user);
            if(userService.checkAdmin(user)==1) {
            	modelAndView.setViewName("adminLanding");
            }else {
            	modelAndView.setViewName("userLanding");
            }
          
        }
        else {
            modelAndView.addObject("message", "Invalid Credentials");
            modelAndView.addObject("command", new User());
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }
}