package dhsn.verwaltung.spieler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dhsn.verwaltung.spieler.model.domain.Position;
import dhsn.verwaltung.spieler.model.domain.Spieler;
import dhsn.verwaltung.spieler.model.identity.Role;
import dhsn.verwaltung.spieler.service.BenutzerService;
import dhsn.verwaltung.spieler.service.SpielerService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/spielerverwaltung/admin")

public class BenutzerController {
  private BenutzerService benutzerService;
  private SpielerService spielerService;

  public BenutzerController(BenutzerService bs, SpielerService ss) {
    this.benutzerService = bs;
    this.spielerService = ss;
  }

  @GetMapping("/registrierung")
  public String registrierung(Model model) {
    model.addAttribute("listeSpieler", spielerService.getAlleSpieler());
    return "homepage/adminHomepage";
  }

  @GetMapping("/registrierung/neuerSpieler")
  public String getneuerSpieler(Model model) {
    model.addAttribute("positionen", Position.values());
    model.addAttribute("spieler", new Spieler());
    return "spieler/neuerSpieler";
  }

  @PostMapping("/registrierung/neuerSpieler")
  public String postneuerSpieler(
      @ModelAttribute("spieler") Spieler spieler,
      Model model
  ) {
    
    spieler.setRole(Role.ROLE_SPIELER);
    
    var spielerTest = benutzerService.register(spieler);
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
    model.addAttribute("spieler",spielerService.getSpieler(id));
    model.addAttribute("positionen", Position.values());
    return "spieler/editSpieler";
  }

  @PostMapping("/spieler/{id}")
  public String editSpieler(
      @ModelAttribute("spieler") Spieler spieler,
      @PathVariable("id") Long id) {

    spielerService.speichernEditSpieler(spieler, id);

    return "redirect:/spielerverwaltung/erfolg";
  }

  @PostMapping("/spieler/delete/{id}")
  public String deleteSpieler(
      @PathVariable("id") Long id) {
    spielerService.deleteSpieler(id);
    System.out.println("PASSIERT");
    return "redirect:/spielerverwaltung/erfolg";
  }
}
