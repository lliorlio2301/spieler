package dhsn.verwaltung.spieler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import dhsn.verwaltung.spieler.service.BenutzerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/login")
public class LoginController {
    
    private BenutzerService benutzerService;

    public LoginController (BenutzerService benutzerService) {
        this.benutzerService = benutzerService;
    }

    @GetMapping()
    public String getMethodName(Model model, @RequestParam(required = false, value = "bye") String param) {
        if (param!=null) {
            
            model.addAttribute("bye", "Erfolgreich abgemeldet");
        }       
        return "login";
    }
    
}
