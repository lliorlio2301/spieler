package dhsn.verwaltung.spieler.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dhsn.verwaltung.spieler.model.domain.Verein;
import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.Role;
import dhsn.verwaltung.spieler.model.identity.DTO.BenutzerDTO;
import dhsn.verwaltung.spieler.model.identity.DTO.BenutzerIdDTO;
import dhsn.verwaltung.spieler.repository.BenutzerRepository;
import dhsn.verwaltung.spieler.repository.SpielerRepository;
import jakarta.transaction.Transactional;

@Service
public class BenutzerService {

  private BenutzerRepository benutzerRepository;
  private SpielerRepository spielerRepository;
  private PasswordEncoder passwordEncoder;


  public BenutzerService (BenutzerRepository benutzerRepository, PasswordEncoder pe, SpielerRepository sp) {
    this.benutzerRepository = benutzerRepository;
    this.passwordEncoder = pe;
    this.spielerRepository = sp;
  }

  @Transactional
  public Benutzer register(BenutzerDTO benutzerDTO) {
    Benutzer benutzer = new Benutzer(
      benutzerDTO.getUsername(),
      passwordEncoder.encode(benutzerDTO.getPasswort()),
      benutzerDTO.getRole());
    // Gibt Objekt zurück, da erst bei save() die Id in der DB erstellt wird
    // Jetzt als Prüfer für Methode im Controller
    return benutzerRepository.save(benutzer);  
  }

  public List<Benutzer> getAllBenutzer() {
    return benutzerRepository.findAll();
  }

  public BenutzerIdDTO getBenutzerIdDTOById(Long id) { 
    Benutzer benutzer = benutzerRepository.findById(id).orElseThrow(()-> 
      new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt"));
    BenutzerIdDTO bDTO = new BenutzerIdDTO(benutzer.getId(), benutzer.getUsername(), benutzer.getPasswort(), benutzer.getRole()); 
      return bDTO;
  }

  @Transactional
  public void speicherEditBenutzer(BenutzerIdDTO formularBenutzer, Long id) {

    Benutzer datenBankBenutzer = benutzerRepository.findById(id).
    orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt"));

    datenBankBenutzer.setUsername(formularBenutzer.getUsername());
    datenBankBenutzer.setRole(formularBenutzer.getRole());

    if (!formularBenutzer.getPasswort().isEmpty() && formularBenutzer.getPasswort() != null) {
      datenBankBenutzer.setPasswort(passwordEncoder.encode(formularBenutzer.getPasswort()));
    }

    benutzerRepository.save(datenBankBenutzer);

    if (spielerRepository.existsById(id)) { //Benutzer darf nicht Spieler und Admin gleichzeitig sein
        spielerRepository.deleteNurBeiSpieler(id);
        return;
    }

    if (datenBankBenutzer.getRole().equals(Role.ROLE_SPIELER) 
      && !spielerRepository.existsById(id)) //Sicherstellung dieser Benutzer schon in spieler Tabelle nicht war  
    {
      List<Integer> benutzteRuecknummer = spielerRepository.getRueckNummer();
      int rueckennummer = 0;
      while (benutzteRuecknummer.contains(rueckennummer)) {
        rueckennummer++;
      }

      spielerRepository.adminZuSpieler(id, rueckennummer);
    }
  }

  @Transactional
  public void deleteBenutzer(Long id) {
    if (!benutzerRepository.existsById(id)) {
     throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt"); 
    }
    benutzerRepository.deleteById(id);
  }

  public Verein getAdminVerein(Long id) {
    Benutzer admin = benutzerRepository.findById(id).
    orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt"));
    return admin.getVerwalteterVerein();
  }

  public Benutzer getAdmin(Long id) {
    return benutzerRepository.findById(id).
    orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt"));
  }

  public List<Benutzer> getFreieAdmins() {
   return benutzerRepository.findFreieAdmins();
  }
  
}
