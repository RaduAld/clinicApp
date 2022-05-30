package web.clinicApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import web.clinicApp.repository.DoctorRepository;
import web.clinicApp.repository.UserRepository;
import web.clinicApp.service.model.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import web.clinicApp.entity.User;


@Controller
public class IndexController {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public IndexController(UserRepository userRepository, DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    private boolean isUserDoctor() {
        User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            String email = ((CustomUserDetails)principal).getUsername();
            user = userRepository.findByEmail(email);
        }

        if (doctorRepository.findByUser(user) != null) {
            return true;
        }
        return false;
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("isUserDoctor", isUserDoctor());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            String email = ((CustomUserDetails)principal).getUsername();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        }
        return "index";
    }

    @GetMapping("/notFound")
    public String NotFound(Model model) {
        return "notfound";
    }
}
