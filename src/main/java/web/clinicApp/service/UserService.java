package web.clinicApp.service;

import web.clinicApp.entity.Doctor;
import web.clinicApp.repository.PatientRepository;
import web.clinicApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.clinicApp.entity.Patient;
import web.clinicApp.entity.Specialty;
import web.clinicApp.entity.User;
import web.clinicApp.repository.DoctorRepository;

import java.time.LocalDate;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    public User saveUser(String first_name, String last_name, String email, String password, String role) {
        User existingUser = userRepository.findByEmail(email);
        if(existingUser != null) {
            throw new RuntimeException("duplicate email");
        }

        User entity = new User(first_name, last_name, email, passwordEncoder.encode(password), role);
        return userRepository.save(entity);
    }

    public User saveUserPatient(String first_name, String last_name, LocalDate dob, String email, String password, String role) {
        User existingUser = userRepository.findByEmail(email);
        if(existingUser != null) {
            throw new RuntimeException("duplicate email");
        }

        User entity = new User(first_name, last_name, email, passwordEncoder.encode(password), role);
        User user = userRepository.save(entity);
        Patient patient = new Patient(user, dob);
        patientRepository.save(patient);

        return user;
    }

    public User saveUserDoctor(String first_name, String last_name, Specialty specialty, String email, String password, String role) {
        User existingUser = userRepository.findByEmail(email);
        if(existingUser != null) {
            throw new RuntimeException("duplicate email");
        }

        User entity = new User(first_name, last_name, email, passwordEncoder.encode(password), role);
        User user = userRepository.save(entity);
        Doctor doctor = new Doctor(user, specialty);
        doctorRepository.save(doctor);

        return user;
    }
}
