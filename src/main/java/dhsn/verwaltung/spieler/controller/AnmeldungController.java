package dhsn.verwaltung.spieler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/anmelden")
public class AnmeldungController {

    @GetMapping
    public String anmelden(
        Model model, 
        @RequestParam(required = false, value = "bye") String logout,
        @RequestParam(required = false, value = "error") String fehler
    ) {
        if (logout!=null) {
            model.addAttribute("bye", logout);
        }
        if (fehler!=null) {
            model.addAttribute("error", fehler);
        }
        return "anmelden";
    }
}
