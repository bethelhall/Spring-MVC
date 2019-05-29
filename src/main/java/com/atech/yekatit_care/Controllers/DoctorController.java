package com.atech.yekatit_care.Controllers;


import com.atech.yekatit_care.Domains.*;
import com.atech.yekatit_care.Repositories.*;
import com.atech.yekatit_care.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sun.util.calendar.BaseCalendar;

import javax.validation.Valid;
import javax.xml.crypto.Data;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientHistoryRepository patientHistoryRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private LabTestRepository labTestRepository;

    @Autowired
    private LabResultRepository labResultRepository;

    @Autowired
    private UserService userService;


    @GetMapping("/home")
    public String doctorHome(Model model, Principal principal) {
        User doctor = userService.findUserByEmail(principal.getName());
        Iterable<Patient> allPatients = patientRepository.findByAssignedDoctor(doctor.getName());

        model.addAttribute("patients", allPatients);

        return "Doctor/home";
    }

    @GetMapping("/sentrequests")
    public String sentRequests(Model model, Principal principal){

        Iterable<LabTest> allLabTests = labTestRepository.findAll();
        List<LabTest> labTests = new ArrayList<>();
        HashMap<Integer, Patient> patientNames = new HashMap<>();
        HashMap<Integer, LabResult> labResults = new HashMap<>();
        User doctor = userService.findUserByEmail(principal.getName());

        for (LabTest labTest:
             allLabTests) {

            if(labTest.getDoctor_email().equals(doctor.getEmail())) {
                Patient patient = patientRepository.findById(labTest.getPatient_id());
                patientNames.put(labTest.getPatient_id(), patient);

                labTests.add(labTest);

                LabResult result = labResultRepository.findByTestRequest_id(labTest.getTest_id());

                labResults.put(labTest.getTest_id(), result);

            }
        }

        model.addAttribute("doctor", doctor);
        model.addAttribute("labTests", labTests);
        model.addAttribute("labResults", labResults);
        model.addAttribute("patientNames", patientNames);

        return "Doctor/sentrequests";
    }


    @GetMapping("/sendlabr/{id}")
    public String sendLabR(@PathVariable int id, Model model) {

        Patient patient = patientRepository.findById(id);
        Iterable<Test> tests = testRepository.findAll();

        model.addAttribute("tests", tests);
        model.addAttribute("patient", patient);
        model.addAttribute("labTest", new LabTest());

        return "Doctor/sendlabr";
    }

    @GetMapping("/sentrequests/edit/{id}/{test_id}")
    public String editLabRequest(@PathVariable int id, @PathVariable int test_id, Model model) {

        Patient patient = patientRepository.findById(id);
        Iterable<Test> tests = testRepository.findAll();
        List<Test> selectedTests = labTestRepository.findByTest_id(test_id).getLab_request();

        model.addAttribute("tests", tests);
        model.addAttribute("patient", patient);
        model.addAttribute("selectedTests",selectedTests);
        model.addAttribute("labTest", new LabTest());
        model.addAttribute("test_id", test_id);

        return "Doctor/editsentrequests";
    }

    @PutMapping("/sentrequests/edit/{test_id}")
    public String processEditedLapReqest(@Valid LabTest labTest, @PathVariable int test_id, Principal principal, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "Doctor/editsentrequests";
        }

//        patientRepository.deleteById(id);
//        int testId = labTest.getTest_id();
        List<Test> lab_requests = labTest.getLab_request();

        LabTest labTestToBeSaved = labTestRepository.findByTest_id(test_id);

        labTestToBeSaved.setLab_request(lab_requests);
        labTestToBeSaved.setPatient_id(labTestToBeSaved.getPatient_id());
        labTestToBeSaved.setDoctor_email(principal.getName());

        labTestRepository.save(labTestToBeSaved);

        return "redirect:/doctor/sentrequests";
    }

    @PostMapping("/sendlabr/{id}")
    public String processLabRequest(@Valid LabTest labTest, Errors errors, @PathVariable int id, Principal principal) {

        if (errors.hasErrors()) {
            return "Doctor/sendlabr";
        }

//        patientRepository.deleteById(id);
//        int testId = labTest.getTest_id();
        List<Test> lab_requests = labTest.getLab_request();

        LabTest labTestToBeSaved = new LabTest();

        labTestToBeSaved.setLab_request(lab_requests);
        labTestToBeSaved.setPatient_id(id);
        labTestToBeSaved.setDoctor_email(principal.getName());

        labTestRepository.save(labTestToBeSaved);

        return "redirect:/doctor/home";
    }

    @GetMapping("/labresults")
    public String labResults(Model model, Principal principal) {

        Iterable<LabTest> allLabTests = labTestRepository.findAll();
        List<LabTest> labTests = new ArrayList<>();
//        List<LabResult> labResults = new ArrayList<>();
        HashMap<Integer, Patient> patientNames = new HashMap<>();
        HashMap<Integer, LabResult> labResults = new HashMap<>();
        User doctor = userService.findUserByEmail(principal.getName());
        for (LabTest labTest :
                allLabTests) {

            if (labTest.getDoctor_email().equals(doctor.getEmail())) {
                Patient patient = patientRepository.findById(labTest.getPatient_id());
                patientNames.put(labTest.getPatient_id(), patient);

                labTests.add(labTest);

                LabResult result = labResultRepository.findByTestRequest_id(labTest.getTest_id());

                labResults.put(labTest.getTest_id(), result);
            }
        }

        model.addAttribute("doctor", doctor);
        model.addAttribute("labTests", labTests);
        model.addAttribute("labResults", labResults);
        model.addAttribute("patientNames", patientNames);

        return "Doctor/labresults";
    }

    @GetMapping("/patienthistory")
    public String patientHistory(Model model, Principal principal) {

        Iterable<LabTest> allLabTests = labTestRepository.findAll();
        List<LabTest> labTests = new ArrayList<>();
//        List<LabResult> labResults = new ArrayList<>();
        HashMap<Integer, Patient> patientNames = new HashMap<>();
        HashMap<Integer, LabResult> labResults = new HashMap<>();
        HashMap<Integer, String> laboratoryTechName = new HashMap<>();
        User doctor = userService.findUserByEmail(principal.getName());
        for (LabTest labTest :
                allLabTests) {

            if (labTest.getDoctor_email().equals(doctor.getEmail())) {
                Patient patient = patientRepository.findById(labTest.getPatient_id());
                if(patient.getPatientHistory() != null) {

                    patientNames.put(labTest.getPatient_id(), patient);

                    labTests.add(labTest);

                    LabResult result = labResultRepository.findByTestRequest_id(labTest.getTest_id());

                    labResults.put(labTest.getTest_id(), result);

                    User labTech = userService.findUserByEmail(result.getLabTechnician_name());
                    laboratoryTechName.put(labTest.getTest_id(), labTech.getName());
                }

            }
        }

        model.addAttribute("doctor", doctor);
        model.addAttribute("labTests", labTests);
        model.addAttribute("labResults", labResults);
        model.addAttribute("patientNames", patientNames);
        model.addAttribute("labTechNames", laboratoryTechName);

        return "Doctor/patienthistory";
    }

    @PostMapping("/patienthistory/{patient_id}")
    public String savePatienthistory(@PathVariable int patient_id) {
        Patient patient = patientRepository.findById(patient_id);
        PatientHistory patientHistory = new PatientHistory();

        patient.setPatientHistory(patientHistory);
        patientHistory.setPatient(patient);

        patientRepository.save(patient);
        patientHistoryRepository.save(patientHistory);

        return "redirect:/doctor/patienthistory";

    }

}
