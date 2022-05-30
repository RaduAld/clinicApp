package web.clinicApp.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import web.clinicApp.entity.*;
import web.clinicApp.repository.AppointmentRepository;
import web.clinicApp.repository.PatientRepository;
import web.clinicApp.repository.UserRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.clinicApp.repository.DoctorRepository;
import web.clinicApp.service.AppointmentService;
import web.clinicApp.service.model.CustomUserDetails;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, UserRepository userRepository, AppointmentService appointmentService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.appointmentService = appointmentService;
    }

    private User userDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String logged = ((CustomUserDetails)principal).getUsername();
        return userRepository.findByEmail(logged);
    }

    private boolean isUserDoctor() {
        User user = userDetails();
        if (doctorRepository.findByUser(user) != null) {
            return true;
        }
        return false;
    }

    @GetMapping("/appointments/add")
    public String appointmentAdd(Model model, Hour time, Doctor selectedDoctor, String selectedDate){
        model.addAttribute("isUserDoctor", isUserDoctor());

        LocalDate date = LocalDate.now();
        model.addAttribute("today", date);

        List<Doctor> docs = this.doctorRepository.findAll();
        model.addAttribute("doctors", docs);

        if (time != null){
            model.addAttribute("notAvailableTime", time);
            model.addAttribute("selectedDoctor", selectedDoctor);
            model.addAttribute("selectedDate", selectedDate);
            return "appointments/add";
        }

        return "appointments/add";
    }

    @PostMapping("/appointments/add")
    public String addAppointment(Model model,
                            @RequestParam("doctor") String doctorEmail,
                            @RequestParam("date") String date,
                            @RequestParam("time") String timeInfo) {
        LocalDate localDate = LocalDate.parse(date);
        Hour time = Hour.valueOf(timeInfo);
        User doctorUser = userRepository.findByEmail(doctorEmail);
        Doctor doctor = doctorRepository.findByUser(doctorUser);

        User user = userDetails();

        Patient patient = patientRepository.findByUser(user);
        try {
            Appointment appointment = appointmentService.saveAppointment(patient, doctor, localDate, time);
        } catch (RuntimeException exception) {
            appointmentAdd(model, time, doctor, date);
            return null;
        }
        return "redirect:/appointments";
    }

    @GetMapping("/appointments")
    public String patientList(Model model) {
        model.addAttribute("isUserDoctor", isUserDoctor());
        User user = userDetails();

        Collection<Appointment> appointments =  appointmentRepository.findByPatientEmail(user.getEmail());
        if (!appointments.isEmpty()){
            model.addAttribute("appointments", appointments);
        }
        return "appointments/list";
    }

    @GetMapping("/appointments/delete/{id}")
    public String deleteAppointmentrById(@PathVariable("id") UUID id){
        appointmentRepository.deleteById(id);
        return "redirect:/appointments/";
    }

    @GetMapping("/doctor/appointments")
    public String doctorList(Model model) {
        model.addAttribute("isUserDoctor", isUserDoctor());
        User user = userDetails();

        Collection<Appointment> appointments =  appointmentRepository.findByDoctorEmail(user.getEmail());
        if (!appointments.isEmpty()){
            model.addAttribute("appointments", appointments);
        }
        return "appointments/doctorList";
    }

}
