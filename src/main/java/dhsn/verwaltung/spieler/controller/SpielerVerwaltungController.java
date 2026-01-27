package dhsn.verwaltung.spieler.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dhsn.verwaltung.spieler.model.domain.Position;
import dhsn.verwaltung.spieler.model.domain.SpielerDTO.SpielerUpdateDTO;
import dhsn.verwaltung.spieler.model.identity.BenutzerAuthDetails;
import dhsn.verwaltung.spieler.model.identity.Role;
import dhsn.verwaltung.spieler.model.identity.DTO.BenutzerIdDTO;
import dhsn.verwaltung.spieler.service.BenutzerService;
import dhsn.verwaltung.spieler.service.SpielerService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


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
    public String getHomepage(Model model, @AuthenticationPrincipal BenutzerAuthDetails bad ) {
        
        BenutzerIdDTO bDTO = benutzerService.getBenutzerIdDTOById(bad.getId());
        
        if (bDTO.getRole() == Role.ROLE_SPIELER) model.addAttribute("fussballID", bad.getId());
        if (bDTO.getRole() == Role.ROLE_ADMIN) model.addAttribute("willkommenadmin", "Willkommen Admin");
        if (bDTO.getRole() == Role.ROLE_SUPER) model.addAttribute("willkommensuper", "Willkommen SuperAdmin");

        return "homepage/homepage";
    } 
    
    @GetMapping("/erfolg")
    public String getErfolg() {
        return "erfolg";
    }

    @GetMapping("/spieler/{id}")
    public String getSpielerDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("spielerDTO", spielerService.getBasicSpielerDTO(id));
        model.addAttribute("positionen", Position.values());
        return "spieler/spielerSelbstBearbeitung";
    }


    @PostMapping("/spieler/{id}")
    public String editSpieler(
      Model model, 
      @ModelAttribute("spielerDTO") @Valid SpielerUpdateDTO spielerDTO, BindingResult br ,
      @PathVariable("id") Long id
    ) {
    
      if(br.hasErrors()){

        System.out.println("--- VALIDIERUNGSFEHLER GEFUNDEN ---");
        
        br.getAllErrors().forEach(error -> {
            System.out.println(error.toString());
        });
        
        System.out.println("-----------------------------------");
        
        //nochmal Enums schicken  
        model.addAttribute("positionen", Position.values());
          return "spieler/spielerSelbstBearbeitung"; 
        }
    spielerService.speichernEditSpieler(spielerDTO, id);

    return "redirect:/spielerverwaltung/erfolg";
  }

  @PostMapping("/spieler/erhoeung/{id}")
  public String gehaltsErhöhungSpieler(
      Model model, @PathVariable("id") Long id, @RequestParam("mehrGehalt") boolean willErhoung
    ) {
        if(willErhoung)System.out.println(id);
        spielerService.setGehaltErhoung(id, willErhoung);
    return "redirect:/spielerverwaltung/erfolg";
  }
    
    
}
