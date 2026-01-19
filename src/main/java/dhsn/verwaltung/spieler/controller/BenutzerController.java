package dhsn.verwaltung.spieler.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dhsn.verwaltung.spieler.model.domain.Position;
import dhsn.verwaltung.spieler.model.domain.Spieler;
import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.Role;
import dhsn.verwaltung.spieler.service.BenutzerService;
import dhsn.verwaltung.spieler.service.SpielerService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping("/spielerverwaltung/admin/registrierung")

public class BenutzerController {
    private BenutzerService benutzerService;
    private SpielerService spielerService;

    public BenutzerController(BenutzerService bs, SpielerService ss) {
        this.benutzerService = bs;
        this.spielerService = ss;
    }

    @GetMapping
    public String registrierung(Model model) {
        model.addAttribute("listeSpieler", spielerService.getAlleSpieler());    
        return "registrierung";
    }

    @GetMapping("/neuerAdmin")
    public String getNeuenAdmin(Model model) {
        model.addAttribute("admin", new Benutzer());
        return "neuerAdmin";
    }

    @GetMapping("/neuerSpieler")
    public String getneuerSpieler(Model model) {
        model.addAttribute("positionen", Position.values());
        model.addAttribute("spieler", new Spieler());
        return "neuerSpieler";
    }
    
    @PostMapping("/neuerAdmin")
    public String postneuerAdmin(
        //ModelAttribute anstatt BodyRequest weil model.addAttribute
        //RequestBody schickt JSON
        @ModelAttribute("admin") Benutzer benutzer,
        Model model
    ) {
        benutzer.setRole(Role.ROLE_ADMIN);
        var benutzerTest = benutzerService.register(benutzer);
        if(null==benutzerTest) {
            model.addAttribute("regError", "Fehler bei der Registrierung");
            return"redirect:/spielerverwaltung/admin/registrierung";
        }
        return "redirect:/spielerverwaltung/admin/registrierung/regErfolg";
    }

    @PostMapping("/neuerSpieler")
    public String postneuerSpieler(
        @ModelAttribute("spieler") Spieler spieler,
        Model model
    ) {
        spieler.setRole(Role.ROLE_SPIELER);
        var spielerTest = benutzerService.register(spieler);
        if(null==spielerTest) {
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
