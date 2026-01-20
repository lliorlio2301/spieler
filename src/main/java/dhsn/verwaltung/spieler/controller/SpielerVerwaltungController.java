package dhsn.verwaltung.spieler.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dhsn.verwaltung.spieler.service.BenutzerService;
import dhsn.verwaltung.spieler.service.SpielerService;





@Controller
@RequestMapping("/spielerverwaltung")
public class SpielerVerwaltungController {

    BenutzerService benutzerService;
    SpielerService spielerService;
    

    public SpielerVerwaltungController(BenutzerService bs, SpielerService ss) {
        this.benutzerService = bs;
        this.spielerService =ss;
    }

    @GetMapping("/startseite")
    public String getStartseite(Model model) {
        model.addAttribute("spielerDTOListe", spielerService.getAlleSpielerAdmin());
        return "startseite";
    }
    
    @GetMapping("/homepage")
    public String getHomepage() {
        return "homepage/homepage";
    } 
    
    @GetMapping("/erfolg")
    public String getErfolg() {
        return "erfolg";
    }
    
}
