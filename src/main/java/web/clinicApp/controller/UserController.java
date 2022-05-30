package web.clinicApp.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import web.clinicApp.entity.*;
import web.clinicApp.repository.AppointmentRepository;
import web.clinicApp.repository.PatientRepository;
import web.clinicApp.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import web.clinicApp.repository.DoctorRepository;
import web.clinicApp.service.UserService;
import web.clinicApp.service.model.CustomUserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, UserService userService) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.userService = userService;
    }
    private User getLoggedUserDetails() {
        User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            String email = ((CustomUserDetails)principal).getUsername();
            user = userRepository.findByEmail(email);
        }
        return user;
    }

    private boolean isUserDoctor() {
        User user = getLoggedUserDetails();
        if (doctorRepository.findByUser(user) != null) {
            return true;
        }
        return false;
    }

    @RequestMapping("/patients")
    public String patientList(Model model) {
        User user = getLoggedUserDetails();
        model.addAttribute("user", user);
        model.addAttribute("isUserDoctor", isUserDoctor());

        List<Patient> patients = this.patientRepository.findAll();
        model.addAttribute("patients", patients);
        return "patients/list";
    }

    @GetMapping("/patients/registration")
    public String registrationForm(Model model){
        model.addAttribute("isUserDoctor", isUserDoctor());
        model.addAttribute("user", getLoggedUserDetails());
        return "patients/add";
    }

    @PostMapping("/patients/registration")
    public String addUser(Model model,
                          @RequestParam("first_name") String first_name,
                          @RequestParam("last_name") String last_name,
                          @RequestParam("dob") String date,
                          @RequestParam("email") String email,
                          @RequestParam("password") String password){
        LocalDate dob = LocalDate.parse(date);
        try {
            User user = userService.saveUserPatient(first_name, last_name, dob, email, password, "ROLE_USER");
        } catch (RuntimeException exception) {
            return "redirect:/patients/registration?error";
        }
        return "redirect:/login";

    }


    @GetMapping("/patients/delete/{email}")
    public String deletePatientById(@PathVariable("email") String email){
        User user = userRepository.findByEmail(email);
        Collection<Appointment> appointments = appointmentRepository.findByPatientEmail(email);
        appointments.forEach(a -> appointmentRepository.deleteById(a.getAppointmentId()));
        patientRepository.deleteByUser(user);
        userRepository.deleteById(user.getUserId());
        return "redirect:/patients/";
    }

    @RequestMapping("/doctors")
    public String doctorList(Model model) {
        model.addAttribute("isUserDoctor", isUserDoctor());
        List<Doctor> doctors = this.doctorRepository.findAll();
        model.addAttribute("doctors", doctors);
        return "doctors/list";
    }

    @GetMapping("/doctors/registration")
    public String doctorRegistrationForm(Model model){
        model.addAttribute("isUserDoctor", isUserDoctor());
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

    @GetMapping("/doctors/delete/{email}")
    public String deleteDoctorById(@PathVariable("email") String email){
        User user = userRepository.findByEmail(email);
        Collection<Appointment> appointments = appointmentRepository.findByDoctorEmail(email);
        appointments.forEach(a -> appointmentRepository.deleteById(a.getAppointmentId()));
        doctorRepository.deleteByUser(user);
        userRepository.deleteById(user.getUserId());
        return "redirect:/doctors/";
    }
}
