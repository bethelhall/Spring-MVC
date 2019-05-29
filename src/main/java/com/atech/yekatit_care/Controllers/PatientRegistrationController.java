package com.atech.yekatit_care.Controllers;

import com.atech.yekatit_care.Domains.Patient;
import com.atech.yekatit_care.Domains.User;
import com.atech.yekatit_care.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/receptionist/home")
public class PatientRegistrationController {
    @Autowired
    PatientRepository patientRepository;

    @PostMapping
    public ModelAndView CreateNewPatient(@Valid Patient patient, BindingResult bindingResult){
        ModelAndView modelAndView=new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("Receptionist/home");
        }
        else{
            patientRepository.save(patient);
            modelAndView.addObject("successMessag", "Patient registered successfully");
            modelAndView.addObject("patient", new Patient());
            modelAndView.setViewName("Receptionist/home");

        }
        return modelAndView;
    }
}

