package web.clinicApp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import web.clinicApp.entity.Doctor;
import web.clinicApp.entity.Patient;
import web.clinicApp.entity.Specialty;
import web.clinicApp.entity.User;
import web.clinicApp.repository.DoctorRepository;
import web.clinicApp.repository.PatientRepository;
import web.clinicApp.repository.UserRepository;
import web.clinicApp.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, UserService userService) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.userService = userService;
    }

    @RequestMapping("/patients")
    public String patientList(Model model) {
        List<Patient> patients = this.patientRepository.findAll();
        model.addAttribute("patients", patients);
        return "users/list";
    }

    @GetMapping("/patients/registration")
    public String registrationForm(){
        return "users/add";
    }

    @PostMapping("/patients/registration")
    public String addUser(Model model,
                          @RequestParam("first_name") String first_name,
                          @RequestParam("last_name") String last_name,
                          @RequestParam("dob") String date,
                          @RequestParam("email") String email,
                          @RequestParam("password") String password){
        LocalDate dob = LocalDate.parse(date);
        User user = userService.saveUserPatient(first_name, last_name, dob, email, password, "ROLE_USER");
        return "redirect:/login";
    }

    @RequestMapping("/doctors")
    public String doctorList(Model model) {
        List<Doctor> doctors = this.doctorRepository.findAll();
        model.addAttribute("doctors", doctors);
        return "doctors/list";
    }

    @GetMapping("/doctors/registration")
    public String doctorRegistrationForm(){
        return "doctors/add";
    }

    @PostMapping("/doctors/registration")
    public String addDoctor(Model model,
                          @RequestParam("first_name") String first_name,
                          @RequestParam("last_name") String last_name,
                          @RequestParam("specialty") String specialtyInfo,
                          @RequestParam("email") String email,
                          @RequestParam("password") String password){
        Specialty specialty = Specialty.valueOf(specialtyInfo);
        User user = userService.saveUserDoctor(first_name, last_name, specialty, email, password, "ROLE_USER");
        return "redirect:/doctors";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteById(@PathVariable("id") UUID id){
        patientRepository.deleteByUserUserId(id);
        userRepository.deleteById(id);
        return "redirect:/patients/";
    }
}
