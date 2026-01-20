package dhsn.verwaltung.spieler.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import dhsn.verwaltung.spieler.model.domain.Position;
import dhsn.verwaltung.spieler.model.domain.SpielerBasicDTO;
import dhsn.verwaltung.spieler.model.domain.SpielerRegisterDTO;
import dhsn.verwaltung.spieler.service.SpielerService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/spielerverwaltung/admin")

public class SpielerController {
  private SpielerService spielerService;

  public SpielerController(SpielerService ss) {
    this.spielerService = ss;
  }

  @GetMapping("/registrierung")
  public String registrierung(Model model) {
    model.addAttribute("listeSpieler", spielerService.getAlleSpielerAdmin());
    return "homepage/adminHomepage";
  }

  @GetMapping("/registrierung/neuerSpieler")
  public String getneuerSpieler(Model model) {
    model.addAttribute("positionen", Position.values());
    model.addAttribute("spielerDTO", new SpielerRegisterDTO());
    return "spieler/neuerSpieler";
  }

  @PostMapping("/registrierung/neuerSpieler")
  public String postneuerSpieler(
      @ModelAttribute("spielerDTO") @Valid SpielerRegisterDTO spielerDTO, BindingResult br,
      Model model) {
    
    if(br.hasErrors()){
      return "spieler/neuerSpieler"; //HTML nochmal schicken 
    }
    
    var spielerTest = spielerService.register(spielerDTO);
    if (null == spielerTest) {
      model.addAttribute("regError", "Fehler bei der Registrierung");
      return "redirect:/spielerverwaltung/admin/registrierung";
    }
    return "redirect:/spielerverwaltung/erfolg";
  }

  @GetMapping("/spieler/{id}")
  // PathVariable weil {id} eine Varialbe ist
  // @RequestParam ist für z.B. ?name=jorge

  public String editSpieler(Model model, @PathVariable Long id) {

    model.addAttribute("spielerDTO",spielerService.getBasicSpielerDTO(id));
    model.addAttribute("positionen", Position.values());
    return "spieler/editSpieler";
  }

  @PostMapping("/spieler/{id}")
  public String editSpieler(
      Model model, 
      @ModelAttribute("spielerDTO") @Valid SpielerBasicDTO spielerDTO, BindingResult br ,
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
          return "spieler/editSpieler"; 
        }
    spielerService.speichernEditSpieler(spielerDTO, id);

    return "redirect:/spielerverwaltung/erfolg";
  }

  @PostMapping("/spieler/delete/{id}")
  public String deleteSpieler(
      @Value(value = "")@PathVariable("id") Long id) {
    spielerService.deleteSpieler(id);
    return "redirect:/spielerverwaltung/erfolg";
  }
}
