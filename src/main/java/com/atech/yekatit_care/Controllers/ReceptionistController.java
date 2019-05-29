package com.atech.yekatit_care.Controllers;


import com.atech.yekatit_care.Domains.LabResult;
import com.atech.yekatit_care.Domains.LabTest;
import com.atech.yekatit_care.Domains.Patient;
import com.atech.yekatit_care.Domains.PatientHistory;
import com.atech.yekatit_care.Repositories.LabResultRepository;
import com.atech.yekatit_care.Repositories.LabTestRepository;
import com.atech.yekatit_care.Repositories.PatientRepository;
import com.atech.yekatit_care.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistController {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    LabTestRepository labTestRepository;

    @Autowired
    LabResultRepository labResultRepository;

    @Autowired
    UserService userService;

    @GetMapping("/home")
    public ModelAndView Home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patient",new Patient());
        modelAndView.addObject("doctorList", userService.getDoctors());
        modelAndView.setViewName("Receptionist/home");
        return modelAndView;
    }
    @GetMapping("/patientlist")
    public String Profile(Map<String, Object>map) {
        map.put("patients", patientRepository.findPatientsByOrderByName());
        return "Receptionist/patientlist";
    }
    @GetMapping("/editPatient/{id}")
    public String editPage(@PathVariable int id, Model model){

        Patient patient = patientRepository.findById(id);

        model.addAttribute("patient", patient);

        return "Receptionist/editPatient";
    }

    @PutMapping("/editPatient/{id}")
    public String edit(@PathVariable int id, @Valid Patient patient, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "Receptionist/editPatient";
        }

        else {
            Patient patientToBeSaved = patientRepository.findById(id);

            patientToBeSaved.setName(patient.getName());
            patientToBeSaved.setAddress(patient.getAddress());
            patientToBeSaved.setAge(patient.getAge());
            patientToBeSaved.setDate(patient.getDate());
            patientToBeSaved.setGender(patient.getGender());
            patientToBeSaved.setPhone_no(patient.getPhone_no());
            patientToBeSaved.setAssignedDoctor(patient.getAssignedDoctor());

            patientRepository.save(patientToBeSaved);

            model.addAttribute("successMessage", "Patient has been updated successfully");

            return "Receptionist/editPatient";
        }

    }

    @DeleteMapping("/patientlist/{id}")
    public String deletePatient(@PathVariable int id){
        patientRepository.deleteById(id);

        LabTest labTest = labTestRepository.findByPatient_id(id);
        if(labTest != null) {
            labTestRepository.delete(labTest);
        }

        LabResult labResult = labResultRepository.findByTestRequest_id(labTest.getTest_id());
        if(labResult != null) {
            labResultRepository.delete(labResult);
        }

        return "redirect:/receptionist/patientlist";
    }





}
