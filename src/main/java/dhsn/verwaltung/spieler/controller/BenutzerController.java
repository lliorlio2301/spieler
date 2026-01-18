package dhsn.verwaltung.spieler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.service.BenutzerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;



@Controller
@RequestMapping("/registrierung")
public class BenutzerController {
    private BenutzerService benutzerService;

    public BenutzerController(BenutzerService benutzerService) {
        this.benutzerService = benutzerService;
    }

    @GetMapping
    public String registrierung(Model model) {
        model.addAttribute("benutzer", new Benutzer());
        return "registrierung";
    }

    @PostMapping("/newUser")
    public String postnewUser(
        //ModelAttribute anstatt BodyRequest weil model.addAttribute
        //RequestBody schickt JSON
        @ModelAttribute("benutzer") Benutzer benutzer,
        Model model
    ) {
        var benutzer1 = benutzerService.register(benutzer);
        if(null==benutzer1) {
            model.addAttribute("regError", "Fehler bei der Registrierung");
            return"redirect:home/registrierung";
        }
        return "redirect:/registrierung/regErfolg";
    }

    @GetMapping("/regErfolg")
    public String getMethodName() {
        return "regErfolg";
    }
}
