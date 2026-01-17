package dhsn.verwaltung.spieler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.service.BenutzerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/registrierung")
public class BenutzerController {
    private BenutzerService benutzerService;

    public BenutzerController(BenutzerService benutzerService) {
        this.benutzerService = benutzerService;
    }

    @GetMapping
    public String registrierung() {
        return "sign_up";
    }

    @PostMapping("/newUser")
    public String postMethodName(
        @RequestBody Benutzer benutzer,
        Model model
    ) {
        if(null==benutzerService.register(benutzer)) {
            model.addAttribute("regError", "Fehler bei der Registrierung");
            return"redirect:home/registrierung";
        }
        return "redirect:/Registrierung/regErfolg";
    }

    @GetMapping("/regErfolg")
    public String getMethodName(@RequestParam String param) {
        return "regErfolg";
    }
}
