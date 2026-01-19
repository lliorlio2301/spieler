package dhsn.verwaltung.spieler.controller;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dhsn.verwaltung.spieler.model.identity.Role;
import dhsn.verwaltung.spieler.repository.BenutzerRepository;
import dhsn.verwaltung.spieler.service.BenutzerService;





@Controller
@RequestMapping("/spielerverwaltung")
public class SpielerVerwaltungController {

    BenutzerService benutzerService;

    

    public SpielerVerwaltungController(BenutzerService bs) {
    this.benutzerService = bs;
    }

    @GetMapping("/startseite")
    public String getStartseite() {
        return "startseite";
    }
    
    @GetMapping("/homepage")
    public String getHomepage() {
        return "homepage";
    }
    
    @GetMapping("/super")
    public String getSuperUser(Model model) {
       
        model.addAttribute("listeBenutzer", benutzerService
        .getAllBenutzer()
        .stream()
        .filter(b->b.getRole()!=Role.ROLE_SUPER)
        .collect(Collectors.toList())
    );
        return "superUser";
    }    
}
