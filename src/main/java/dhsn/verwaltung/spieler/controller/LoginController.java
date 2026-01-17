package dhsn.verwaltung.spieler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import dhsn.verwaltung.spieler.service.BenutzerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/login")
public class LoginController {
    
    private BenutzerService benutzerService;

    public LoginController (BenutzerService benutzerService) {
        this.benutzerService = benutzerService;
    }

    @GetMapping()
    public String login(
        Model model, 
        @RequestParam(required = false, value = "bye") String logout,
        @RequestParam(required = false, value = "fehler") String fehler
    ) {
        if (logout!=null) {
            model.addAttribute("bye", "Erfolgreich abgemeldet");
        }
        if (fehler!=null) {
            model.addAttribute("fehler", "Falscher Benutzername oder Passwort");
        }
        return "login";
    }
}
