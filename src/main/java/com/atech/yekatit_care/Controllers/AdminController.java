package com.atech.yekatit_care.Controllers;

import com.atech.yekatit_care.Domains.User;
import com.atech.yekatit_care.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Id;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @GetMapping("/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.addObject("doctorList", userService.getDoctors());
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }


    @GetMapping("/EmployeeProfile")
    public String profile(Map<String, Object>map) {
        map.put("userList", userService.getAllUsers());
        return "admin/EmployeeProfile";
    }

    @GetMapping("/edit/{email}")
    public String editPage(@PathVariable String email, Model model){
        User user = userService.findUserByEmail(email);

        model.addAttribute("user", user);

        return "Admin/editUser";
    }

    @PutMapping("/edit/{email}")
    public String editUser(@PathVariable String email, @Valid User user,  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "Admin/editUser";
        }
        else {
            User userToBeSaved = userService.findUserByEmail(email);

            userToBeSaved.setName(user.getName());
            userToBeSaved.setLastName(user.getLastName());
            userToBeSaved.setEmail(user.getEmail());
            userToBeSaved.setSelectedRole(user.getSelectedRole());
            userToBeSaved.setPassword(user.getPassword());

            userService.saveUser(userToBeSaved, user.getSelectedRole());

            model.addAttribute("successMessage", "User has been updated successfully");

            return "Admin/editUser";
        }

    }

    @DeleteMapping("/EmployeeProfile/{id}")
    public String delete(@PathVariable int id){
        userService.deleteUser(id);
        return "redirect:/admin/EmployeeProfile";

    }

    }


