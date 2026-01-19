package dhsn.verwaltung.spieler.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.Role;
import dhsn.verwaltung.spieler.service.BenutzerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;



@Controller
@RequestMapping("/spielerverwaltung/admin/registrierung")

public class BenutzerController {
    private BenutzerService benutzerService;

    public BenutzerController(BenutzerService benutzerService) {
        this.benutzerService = benutzerService;
    }

    @GetMapping
    public String registrierung(Model model) {
        model.addAttribute("benutzer", new Benutzer());
        
        //Ohne SuperUser_Role schicken
        List<Role> roles = List.of(Role.ROLE_ADMIN, Role.ROLE_SPIELER);
        //Role Values an HTML übergeben
        model.addAttribute("role", roles);
        return "registrierung";
    }

    @PostMapping("/newUser")
    public String postnewUser(
        //ModelAttribute anstatt BodyRequest weil model.addAttribute
        //RequestBody schickt JSON
        @ModelAttribute("benutzer") Benutzer benutzer,
        Model model
    ) {
        var benutzerTest = benutzerService.register(benutzer);
        if(null==benutzerTest) {
            model.addAttribute("regError", "Fehler bei der Registrierung");
            return"redirect:/spielerverwaltung/admin/registrierung";
        }
        return "redirect:/spielerverwaltung/admin/registrierung/regErfolg";
    }

    @GetMapping("/regErfolg")
    public String getMethodName() {
        return "regErfolg";
    }
}
