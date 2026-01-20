package dhsn.verwaltung.spieler.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dhsn.verwaltung.spieler.model.domain.Spieler;
import dhsn.verwaltung.spieler.repository.BenutzerRepository;
import dhsn.verwaltung.spieler.repository.SpielerRepository;
import jakarta.transaction.Transactional;

@Service
public class SpielerService {
  private SpielerRepository spielerRepo;
  private PasswordEncoder passwordEncoder;

  public SpielerService(
    SpielerRepository sr, PasswordEncoder passwordEncoder
  ) {
    this.spielerRepo = sr;
    this.passwordEncoder = passwordEncoder;
  }

  public Spieler getSpieler(Long id) {
    return spielerRepo.findById(id).orElseThrow(()->new UsernameNotFoundException("ID ist unbekannt: " +id));
  }

  public List<Spieler> getAlleSpieler() {
    return spielerRepo.findAll();
  }
  

//Gewährleistung der Datenintegrität. Alles wird ausgeführt oder nichts
  @Transactional
  public Spieler speichernEditSpieler(Spieler formularSpieler, Long id) 
  throws UsernameNotFoundException {
    Spieler datenBankSpieler = spielerRepo.findById(id).orElseThrow(() -> 
    new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt"));

    datenBankSpieler.setUsername(formularSpieler.getUsername());
    datenBankSpieler.setVorname(formularSpieler.getVorname());
    datenBankSpieler.setNachname(formularSpieler.getNachname());
    datenBankSpieler.setGeburtsdatum(formularSpieler.getGeburtsdatum());
    datenBankSpieler.setPosition(formularSpieler.getPosition());
    datenBankSpieler.setRueckennummer(formularSpieler.getRueckennummer());

    if (
      !formularSpieler.getPasswort().isEmpty() &&
      formularSpieler.getPasswort() != null 
      && !datenBankSpieler.getPasswort().equals(passwordEncoder.encode(formularSpieler.getPasswort()))
    ) {
        
      datenBankSpieler.setPasswort(passwordEncoder.encode(formularSpieler.getPasswort()));
    }
    return spielerRepo.save(datenBankSpieler);
  }

  @Transactional
  public void deleteSpieler(Long id) {
    //Test ob ID in DB vorhanden ist
    spielerRepo.findById(id).orElseThrow(() -> 
    new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt"));
    //benutzerRepo.deleteById(id);
    System.out.println(spielerRepo.findById(id));
    System.out.println("-DELETE-");
    spielerRepo.deleteById(id);
  }
}
