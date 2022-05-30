package web.clinicApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.clinicApp.entity.*;
import web.clinicApp.repository.AppointmentRepository;
import web.clinicApp.repository.DoctorRepository;
import web.clinicApp.repository.PatientRepository;
import web.clinicApp.repository.UserRepository;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment saveAppointment(Patient patient, Doctor doctor, LocalDate localDate, Hour time) {
        Collection<Appointment> existingAppointments = appointmentRepository.findByDoctorEmailAndDateAndTime(doctor.getUser().getEmail(), localDate, time);
        if(!existingAppointments.isEmpty()) {
            throw new RuntimeException("occupied");
        }

        Appointment entity = new Appointment(patient, doctor, localDate, time);
        return appointmentRepository.save(entity);
    }

}
