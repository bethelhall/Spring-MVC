package com.atech.yekatit_care.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/aboutus")
public class AboutController {

    @GetMapping
    public String aboutus() {
        return "aboutus";
    }
}
