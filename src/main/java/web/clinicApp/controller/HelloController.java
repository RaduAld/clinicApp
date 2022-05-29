package web.clinicApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String sayHello(Model model, @RequestParam("name") String nameX) {

        // select
        model.addAttribute("zah_name", nameX.toUpperCase(Locale.ROOT));
        return "hello";
    }
}
