package dhsn.verwaltung.spieler.controller;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.BenutzerDTO;
import dhsn.verwaltung.spieler.model.identity.BenutzerIdDTO;
import dhsn.verwaltung.spieler.model.identity.Role;
import dhsn.verwaltung.spieler.service.BenutzerService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/spielerverwaltung/super")
public class BenutzerController {

    BenutzerService benutzerService;

    public BenutzerController(BenutzerService benutzerService) {
        this.benutzerService = benutzerService;
    }

    @GetMapping
    public String getSuperUser(Model model) {
       
        model.addAttribute("listeBenutzer", benutzerService
        .getAllBenutzer()
        .stream()
        .filter(b->b.getRole()!=Role.ROLE_SUPER)
        .collect(Collectors.toList())
    );
        return "homepage/superUserHomepage";
    }  

    @GetMapping("/neuerAdmin")
        public String getNeuenAdmin(Model model) {
        model.addAttribute("admin", new BenutzerDTO());
        model.addAttribute("adminrole", Role.ROLE_ADMIN);
        return "benutzer/neuerAdmin";
    }

    @PostMapping("/neuerAdmin")
    public String postneuerAdmin(
      // ModelAttribute anstatt BodyRequest weil model.addAttribute
      // RequestBody schickt JSON oder andere
      // @Valid kennzeichnet das zu überprüfende Objekt
      // BindingResult prüft dieses Objekt
      @ModelAttribute("admin") @Valid BenutzerDTO benutzerDTO, BindingResult bindingResult,
      Model model
    ) {

        if(bindingResult.hasErrors()){
            return "benutzer/neuerAdmin"; //HTML nochmal schicken 
        }

        benutzerDTO.setRole(Role.ROLE_ADMIN);
        var benutzerTest = benutzerService.register(benutzerDTO);
        if (null == benutzerTest) {
          model.addAttribute("regError", "Fehler bei der Registrierung");
          return "redirect:/spielerverwaltung/admin/registrierung";
        }
        return "redirect:/spielerverwaltung/erfolg";
    } 

    @GetMapping("/benutzer/{id}")
    public String getEditBenutzer(Model model, @PathVariable Long id) {
        model.addAttribute("benutzer", benutzerService.getBenutzerById(id));
        model.addAttribute("roles", Role.values());
        return "benutzer/editBenutzer";
    }

    @PostMapping("/benutzer/{id}")
    public String postEditBenutzer( Model model,
        @ModelAttribute("benutzer") @Valid BenutzerIdDTO benutzer, BindingResult bindingResult,
        @PathVariable Long id
    ) { 
        if(bindingResult.hasErrors()){
            
            model.addAttribute("roles", Role.values());
            return "benutzer/editBenutzer"; //HTML nochmal schicken 
        }

        benutzerService.speicherEditBenutzer(benutzer, id);
        return "erfolg";
    }

    @PostMapping("/benutzer/delete/{id}")
    public String deleteBenutzer(@PathVariable Long id) {
        benutzerService.deleteBenutzer(id);
        return "erfolg";
    }
}
