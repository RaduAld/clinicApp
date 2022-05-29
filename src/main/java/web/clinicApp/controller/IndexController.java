package web.clinicApp.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import web.clinicApp.entity.User;
import web.clinicApp.repository.UserRepository;
import web.clinicApp.service.model.CustomUserDetails;


@Controller
public class IndexController {

    private final UserRepository userRepository;

    public IndexController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/")
    public String index(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            String email = ((CustomUserDetails)principal).getUsername();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        }
        return "index";
    }
}
