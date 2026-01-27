package dhsn.verwaltung.spieler.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import dhsn.verwaltung.spieler.model.domain.Spieler;
import dhsn.verwaltung.spieler.model.domain.Verein;

import dhsn.verwaltung.spieler.model.identity.BenutzerAuthDetails;
import dhsn.verwaltung.spieler.model.identity.Role;

import dhsn.verwaltung.spieler.service.BenutzerService;
import dhsn.verwaltung.spieler.service.SpielerService;
import dhsn.verwaltung.spieler.service.VereinService;
import org.springframework.web.bind.annotation.PostMapping;





@Controller
@RequestMapping("/spielerverwaltung/admin/verein")
public class VereinController {

    
    private VereinService vereinService;
    private BenutzerService benutzerService;
    private SpielerService spielerService;

    public VereinController(VereinService vServ, BenutzerService bServ, SpielerService spielerService) {
        this.vereinService = vServ;
        this.benutzerService = bServ;
        this.spielerService = spielerService;
    }

    @GetMapping
    public String getAdminVerein(Model model, @AuthenticationPrincipal BenutzerAuthDetails benutzerAuthDetails) {
        Verein verein  = benutzerService.getAdminVerein(benutzerAuthDetails.getId()); 
        if (verein==null) return "/spielerverwaltung/keinVerein";
        
        model.addAttribute("verein", verein);
        model.addAttribute("spielerListe", verein.getSpielerListe());
        return "verein/vereinDashboard";
    }

    @PostMapping("/spielerDelete/{id}")
    public String spielerDelete(@PathVariable("id") Long id, 
        @AuthenticationPrincipal BenutzerAuthDetails benutzerAuthDetails) throws IllegalAccessException {
        Verein verein  = benutzerService.getAdminVerein(benutzerAuthDetails.getId());
        Spieler spieler = spielerService.getSpieler(id);

        if (spieler.getVerein()==null||!spieler.getVerein().getId().equals(verein.getId())) {
            throw new IllegalAccessException("Dieser Spieler gehört nicht zu ihrem Verein");          
        }

        spieler.setVerein(null);
        spielerService.save(spieler);
        return "redirect:/spielerverwaltung/erfolg";    
    }

    @GetMapping("/EditVerein/{id}")
    public String getEditVerein(Model model, @PathVariable("id") Long id) {

        Verein verein  = vereinService.getVerein(id);
        if (verein==null) return "/spielerverwaltung/keinVerein";
        
        model.addAttribute("verein", verein);
        return "verein/vereinEdit";
    }

    @PostMapping("/EditVerein/{id}") 
    public String putEditVerein(@ModelAttribute("verein") Verein formVerein,
                                @AuthenticationPrincipal BenutzerAuthDetails userDetails) {

        Verein dbVerein = vereinService.getVerein(formVerein.getId());
        
        if (dbVerein == null) return "redirect:/error";

        dbVerein.setName(formVerein.getName());
        
        vereinService.save(dbVerein);

        return "redirect:/spielerverwaltung/admin/verein";
    }

    @GetMapping("/EditVerein/mehrSpieler/{verein_id}")
    public String mehrSpieler(Model model, @PathVariable("verein_id") Long verein_id) {
        Verein verein  = vereinService.getVerein(verein_id);

        List<Spieler> ohneTeamSpieler = spielerService.getFreieSpieler();

        model.addAttribute("admin", Role.ROLE_ADMIN);
        model.addAttribute("verein", verein);
        model.addAttribute("spielerListe", ohneTeamSpieler);
        return "verein/mehrSpieler";
    }

     @PostMapping("/EditVerein/mehrSpieler/{verein_id}/{spieler_id}")
     public String mehrSpieler(
        @PathVariable("verein_id") Long verein_id, @PathVariable("spieler_id") Long spieler_id) {
        Spieler spieler = spielerService.getSpieler(spieler_id);
        Verein dbVerein = vereinService.getVerein(verein_id);
        spieler.setVerein(dbVerein);
        spielerService.save(spieler);
        return "redirect:/spielerverwaltung/erfolg";
     }     
}
